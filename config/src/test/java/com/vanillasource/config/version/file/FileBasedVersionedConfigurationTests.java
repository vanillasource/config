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

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import java.io.File;
import com.vanillasource.config.properties.PropertiesKeyValueStorage;
import com.vanillasource.config.version.VersionedConfiguration;
import com.vanillasource.config.SafeParameter;
import static com.vanillasource.config.parameter.Parameters.*;
import java.util.List;
import com.vanillasource.config.configuration.StorageConfiguration;

@Test
public class FileBasedVersionedConfigurationTests {
   private static final SafeParameter<String> KEY = stringParameter("Test.Setting").withDefault("Default");
   private FileBasedVersionedConfiguration config;

   public void testGetReturnsDefault() {
      assertEquals(config.get(KEY), "Default");
   }

   public void testSetThenGetWorks() {
      config.set(KEY, "Value");

      assertEquals(config.get(KEY), "Value");
   }

   public void testTaggedVersionsAreReturnedOldestFirst() {
      config.set(KEY, "Value");
      config.tag("Tag1");
      config.tag("Tag2");

      List<VersionedConfiguration.Version> versions = config.getVersions();

      assertEquals(versions.size(), 2);
      assertEquals(versions.get(0).getTag(), "Tag1");
      assertEquals(versions.get(1).getTag(), "Tag2");
   }

   public void testVersioningCanStartAgain() {
      config.set(KEY, "Value");
      config.tag("Tag1");
      config.tag("Tag2");
      setUp();
      config.set(KEY, "Value");
      config.tag("Tag1");

      assertEquals(config.getVersions().size(), 1);
   }

   public void testSameTagsAreAllowed() {
      config.tag("Tag");
      config.tag("Tag");

      assertEquals(config.getVersions().size(), 2);
   }

   public void testVersionCanBeRestored() {
      config.set(KEY, "Original");
      config.tag("OriginalValues");
      config.set(KEY, "New");

      config.getVersions().get(0).restore();

      assertEquals(config.get(KEY), "Original");
   }

   public void testNewTagAfterRestoreContinuesVersions() {
      config.set(KEY, "Original");
      config.tag("OriginalValues");
      config.set(KEY, "New");
      config.getVersions().get(0).restore();
      config.set(KEY, "New");

      config.tag("NewValues");

      assertEquals(config.getVersions().size(), 2);
   }

   @BeforeMethod
   protected void setUp() {
      File configFile = new File("target/config.xml");
      if (configFile.isFile()) {
         configFile.delete();
      }
      config = new FileBasedVersionedConfiguration(configFile, file ->
            new StorageConfiguration(new PropertiesKeyValueStorage(file)));
   }
}

