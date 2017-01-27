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

package com.vanillasource.config.key;

import com.vanillasource.config.Key.KeyValueStorage;
import com.vanillasource.config.Key;
import java.util.function.Function;

public final class SingleValuedKey<T> implements Key<T> {
   private final String key;
   private final Function<T, String> serializer;
   private final Function<String, T> deserializer;

   public SingleValuedKey(String key, Function<T, String> serializer, Function<String, T> deserializer) {
      this.key = key;
      this.serializer = serializer;
      this.deserializer = deserializer;
   }

   @Override
   public void storeTo(KeyValueStorage storage, T value) {
      storage.put(key, serializer.apply(value));
   }

   @Override
   public T loadFrom(KeyValueStorage storage) {
      return deserializer.apply(storage.get(key));
   }

   @Override
   public void removeFrom(KeyValueStorage storage) {
      storage.remove(key);
   }

   @Override
   public boolean presentIn(KeyValueStorage storage) {
      return storage.contains(key);
   }

   @Override
   public int hashCode() {
      return key.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      return key.equals(o);
   }

   @Override
   public String toString() {
      return "Key '"+key+"'";
   }
}
