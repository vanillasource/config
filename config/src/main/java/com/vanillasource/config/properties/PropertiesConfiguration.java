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

package com.vanillasource.config.properties;

import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key;
import java.util.Properties;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * A <code>Properties</code> based implementation of a configuration.
 */
public class PropertiesConfiguration implements Configuration {
   private Properties properties;
   private File configFile;

   public PropertiesConfiguration(File configFile) {
      this.configFile = configFile;
   }

   @Override
   public <T> T get(Key<T> key) {
      if (isSet(key)) {
         return key.deserialize(properties.getProperty(key.getName()));
      }
      return key.getDefaultValue();
   }

   @Override
   public <T> void set(Key<T> key, T value) {
      getProperties().setProperty(key.getName(), key.serialize(value));
      saveProperties();
   }

   @Override
   public void unset(Key<?> key) {
      getProperties().remove(key.getName());
      saveProperties();
   }

   @Override
   public boolean isSet(Key<?> key) {
      return getProperties().containsKey(key.getName());
   }

   private Properties getProperties() {
      if (properties == null) {
         loadProperties();
      }
      return properties;
   }

   private void loadProperties() {
      properties = new Properties();
      if (configFile.isFile()) {
         try {
            try (InputStream in = new FileInputStream(configFile)) {
               properties.loadFromXML(in);
            }
         } catch (IOException e) {
            throw new UncheckedIOException(e);
         }
      }
   }

   private void saveProperties() {
      try {
         try (FileOutputStream out = new FileOutputStream(configFile)) {
            properties.storeToXML(out, "PropertiesConfiguration", "UTF-8");
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }
}


