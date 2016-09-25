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
import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key;
import com.vanillasource.config.key.StringKey;
import java.io.File;

@Test
public class PropertiesConfigurationTests {
   private static final Key<String> KEY = new StringKey("Test.Setting", "Default");
   private File configFile = new File("target/config.xml");
   private Configuration config;

   public void testSettingIsNotPresentWhenEmpty() {
      assertFalse(config.isSet(KEY));
   }

   public void testDefaultValueIsReturnedIfSettingNotPresent() {
      assertEquals(config.get(KEY), "Default");
   }

   public void testSettingIsReturnedAfterSetting() {
      config.set(KEY, "Value");

      assertEquals(config.get(KEY), "Value");
   }

   public void testSettingsArePersistent() {
      config.set(KEY, "Value");
      config = new PropertiesConfiguration(configFile);

      assertEquals(config.get(KEY), "Value");
   }

   public void testDefaultIsReturnedAfterUnset() {
      config.set(KEY, "Value");

      config.unset(KEY);

      assertEquals(config.get(KEY), "Default");
   }

   @BeforeMethod
   protected void setUp() {
      if (configFile.isFile()) {
         configFile.delete();
      }
      config = new PropertiesConfiguration(configFile);
   }
}
