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

import com.vanillasource.config.Key;

/**
 * Handles returning a default value in case there is no value stored
 * for this key, or delegate returns 'null'.
 */
public final class DefaultValuedKey<T> implements Key<T> {
   private final T defaultValue;
   private final Key<T> delegate;

   public DefaultValuedKey(Key<T> delegate, T defaultValue) {
      this.delegate = delegate;
      this.defaultValue = defaultValue;
   }

   @Override
   public void storeTo(KeyValueStorage storage, T value) {
      if (value == null) {
         delegate.removeFrom(storage);
      } else {
         delegate.storeTo(storage, value);
      }
   }

   @Override
   public T loadFrom(KeyValueStorage storage) {
      if (!presentIn(storage)) {
         return defaultValue;
      } else {
         T result = delegate.loadFrom(storage);
         if (result == null) {
            return defaultValue;
         } else {
            return result;
         }
      }
   }

   @Override
   public void removeFrom(KeyValueStorage storage) {
      delegate.removeFrom(storage);
   }

   @Override
   public boolean presentIn(KeyValueStorage storage) {
      return delegate.presentIn(storage);
   }

   @Override
   public int hashCode() {
      return delegate.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      return delegate.equals(o);
   }

   @Override
   public String toString() {
      return delegate.toString()+" (default: "+defaultValue+")";
   }
}
