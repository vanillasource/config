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

package com.vanillasource.config.single;

import com.vanillasource.config.Configuration;
import com.vanillasource.config.Key.KeyValueStorage;
import com.vanillasource.config.Key;

public final class SingleStorageConfiguration implements Configuration {
   private final KeyValueStorage storage;

   public SingleStorageConfiguration(KeyValueStorage storage) {
      this.storage = storage;
   }

   @Override
   public <T> T get(Key<T> key) {
      return key.loadFrom(storage);
   }

   @Override
   public <T> void set(Key<T> key, T value) {
      key.storeTo(storage, value);
   }

   @Override
   public void unset(Key<?> key) {
      key.removeFrom(storage);
   }

   @Override
   public boolean isSet(Key<?> key) {
      return key.presentIn(storage);
   }
}

