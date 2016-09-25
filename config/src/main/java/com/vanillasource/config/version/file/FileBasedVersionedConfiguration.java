/**
 * Copyright (C) 2016 VanillaSource
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.vanillasource.config.version.file;

import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key;
import com.vanillasource.config.key.StringKey;
import com.vanillasource.config.key.IntegerKey;
import com.vanillasource.config.key.DateKey;
import com.vanillasource.config.version.VersionedConfiguration;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.Date;

/**
 * Works with single file-based configuration implementations
 * by copying the tagged files to archives.
 */
public class FileBasedVersionedConfiguration implements VersionedConfiguration {
   private static final Key<String> TAG_KEY = new StringKey("Versioning.Tag", "<initial>");
   private static final Key<Integer> TAG_INDEX_KEY = new IntegerKey("Versioning.Index", 0);
   private static final Key<Date> TAG_TIMESTAMP_KEY = new DateKey("Versioning.Timestamp", "yyyy-MM-dd HH:mm:ss", null);
   private File configFile;
   private Function<File, Configuration> configurationFactory;
   private Configuration currentConfig;

   /**
    * @param configFile The normal/current ("snapshot") configuration file.
    * @param configurationFactory A factory which can produce a new configuration
    * based on the file given.
    */
   public FileBasedVersionedConfiguration(File configFile,
         Function<File, Configuration> configurationFactory) {
      this.configFile = configFile;
      this.configurationFactory = configurationFactory;
      reloadCurrent();
   }

   private void reloadCurrent() {
      currentConfig = configurationFactory.apply(configFile);
   }

   @Override
   public <T> T get(Key<T> key) {
      return currentConfig.get(key);
   }

   @Override
   public <T> void set(Key<T> key, T value) {
      currentConfig.set(key, value);
   }

   @Override
   public void unset(Key<?> key) {
      currentConfig.unset(key);
   }

   @Override
   public boolean isSet(Key<?> key) {
      return currentConfig.isSet(key);
   }

   /**
    * Tag the current configuration with the given tag.
    */
   @Override
   public synchronized void tag(String tag) {
      currentConfig.set(TAG_KEY, tag);
      int index = currentConfig.get(TAG_INDEX_KEY);
      index++;
      currentConfig.set(TAG_INDEX_KEY, index);
      currentConfig.set(TAG_TIMESTAMP_KEY, new Date());
      try {
         File versionedFile = getVersionedFile(index);
         if (versionedFile.isFile()) {
            versionedFile.delete();
         }
         Files.copy(configFile.toPath(), versionedFile.toPath());
      } catch (IOException e) {
         throw new UncheckedIOException("can not create tag "+tag, e);
      }
   }

   private File getVersionedFile(int index) {
      return new File(configFile.getPath()+"."+index);
   }

   /**
    * List the available tags.
    */
   @Override
   public synchronized List<Version> getVersions() {
      List<Version> versions = new ArrayList<Version>();
      int currentIndex = currentConfig.get(TAG_INDEX_KEY);
      for (int i=1; i<currentIndex+1; i++) {
         Configuration config = configurationFactory.apply(getVersionedFile(i));
         int index = i;
         if (config.isSet(TAG_KEY)) {
            versions.add(new Version() {
               @Override
               public int compareTo(Version other) {
                  return getTimestamp().compareTo(other.getTimestamp());
               }

               @Override
               public String getTag() {
                  return config.get(TAG_KEY);
               }

               @Override
               public Date getTimestamp() {
                  return config.get(TAG_TIMESTAMP_KEY);
               }

               @Override
               public void restore() {
                  synchronized (FileBasedVersionedConfiguration.this) {
                     try {
                        configFile.delete();
                        Files.copy(getVersionedFile(index).toPath(), configFile.toPath());
                     } catch (IOException e) {
                        throw new UncheckedIOException("can not restore index "+index, e);
                     }
                     reloadCurrent();
                     config.set(TAG_INDEX_KEY, currentIndex);
                  }
               }
            });
         }
      }
      Collections.sort(versions);
      return versions;
   }
}

