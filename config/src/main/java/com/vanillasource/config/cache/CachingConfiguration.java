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

import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key;
import java.util.Map;
import java.util.HashMap;

/**
 * Caches all configuration values indefinitely, until the same (equal)
 * key is used in an update method.
 */
public class CachingConfiguration implements Configuration {
   private Configuration delegate;
   private Map<Key<Object>, Object> cache = new HashMap<>();

   public CachingConfiguration(Configuration delegate) {
      this.delegate = delegate;
   }

   @Override
   @SuppressWarnings("unchecked")
   public <T> T get(Key<T> key) {
      if (cache.containsKey(key)) {
         return (T) cache.get(key);
      }
      T value = delegate.get(key);
      cache.put((Key<Object>)key, value);
      return value;
   }

   @Override
   public <T> void set(Key<T> key, T value) {
      cache.remove(key);
      delegate.set(key, value);
   }

   @Override
   public void unset(Key<?> key) {
      cache.remove(key);
      delegate.unset(key);
   }

   @Override
   public boolean isSet(Key<?> key) {
      if (cache.containsKey(key)) {
         return true;
      }
      return delegate.isSet(key);
   }
}


