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

package com.vanillasource.config.cache;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.mockito.Mockito.*;
import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key;

@Test
public class CachingConfigurationTests {
   @SuppressWarnings("unchecked")
   private static final Key<String> KEY = mock(Key.class);
   private CachingConfiguration config;
   private Configuration delegate;

   public void testFirstGetDelegates() {
      config.get(KEY);

      verify(delegate).get(KEY);
   }

   public void testSecondGetDoesNotDelegate() {
      config.get(KEY);
      config.get(KEY);

      verify(delegate).get(KEY);
   }

   public void testGetAfterSetDelegates() {
      config.get(KEY);
      config.set(KEY, "ni");
      config.get(KEY);

      verify(delegate, times(2)).get(KEY);
   }

   public void testGetAfterUnsetDelegates() {
      config.get(KEY);
      config.set(KEY, "ni");
      config.get(KEY);

      verify(delegate, times(2)).get(KEY);
   }

   public void testIsSetDelegates() {
      config.isSet(KEY);

      verify(delegate).isSet(KEY);
   }

   public void testIsSetDoesNotDelegateAfterGet() {
      config.get(KEY);
      config.isSet(KEY);

      verify(delegate, never()).isSet(KEY);
   }

   @BeforeMethod
   protected void setUp() {
      delegate = mock(Configuration.class);
      config = new CachingConfiguration(delegate);
   }
}
