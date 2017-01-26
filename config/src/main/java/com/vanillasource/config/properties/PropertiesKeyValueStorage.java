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

import com.vanillasource.config.Key.KeyValueStorage;
import java.util.Properties;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * A <code>Properties</code> based implementation of a key-value storage.
 */
public class PropertiesKeyValueStorage implements KeyValueStorage {
   private final Properties properties;
   private final File configFile;

   public PropertiesKeyValueStorage(File configFile) {
      this.configFile = configFile;
      this.properties = new Properties();
      loadProperties();
   }

   @Override
   public String get(String key) {
      return properties.getProperty(key);
   }

   @Override
   public void put(String key, String value) {
      properties.setProperty(key, value);
      saveProperties();
   }

   @Override
   public void remove(String key) {
      properties.remove(key);
      saveProperties();
   }

   @Override
   public boolean contains(String key) {
      return properties.containsKey(key);
   }

   private void loadProperties() {
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


