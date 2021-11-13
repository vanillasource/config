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

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import java.io.File;

@Test
public class PropertiesKeyValueStorageTests {
   private File configFile = new File("target/config.xml");
   private PropertiesKeyValueStorage storage;

   public void testSettingIsNotPresentWhenEmpty() {
      assertFalse(storage.load("key").isPresent());
   }

   public void testSettingIsReturnedAfterSetting() {
      storage.store("key", "value");

      assertEquals(storage.load("key").get(), "value");
   }

   public void testSettingsArePersistent() {
      storage.store("key", "value");
      storage = new PropertiesKeyValueStorage(configFile);

      assertEquals(storage.load("key").get(), "value");
   }

   public void testKeyNotPresentAfterRemove() {
      storage.store("key", "value");

      storage.remove("key");

      assertFalse(storage.load("key").isPresent());
   }

   @BeforeMethod
   protected void setUp() {
      if (configFile.isFile()) {
         configFile.delete();
      }
      storage = new PropertiesKeyValueStorage(configFile);
   }
}
