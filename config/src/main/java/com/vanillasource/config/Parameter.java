/**
 * Copyright (C) 2021 VanillaSource
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

package com.vanillasource.config;

import java.util.Optional;
import java.util.function.Function;

/**
 * A single parameter that saves and loads the same type of value in a configuration,
 * where the loaded value is not always present.
 */
public interface Parameter<T> extends GenericParameter<T, Optional<T>> {
   /**
    * Map this parameter to a different type.
    */
   default <R> Parameter<R> map(Function<R, T> toParent, Function<T, R> fromParent) {
      Parameter<T> parent = this;
      return new Parameter<R>() {
         @Override
         public void storeTo(KeyValueStorage storage, R value) {
            parent.storeTo(storage, toParent.apply(value));
         }

         @Override
         public Optional<R> loadFrom(KeyValueStorage storage) {
            return parent.loadFrom(storage).map(fromParent);
         }

         @Override
         public void removeFrom(KeyValueStorage storage) {
            parent.removeFrom(storage);
         }
      };
   }

   /**
    * Add a default value that is always returned.
    */
   default SafeParameter<T> withDefault(T defaultValue) {
      Parameter<T> parent = this;
      return new SafeParameter<T>() {
         @Override
         public void storeTo(KeyValueStorage storage, T value) {
            parent.storeTo(storage, value);
         }

         @Override
         public T loadFrom(KeyValueStorage storage) {
            return parent.loadFrom(storage).orElse(defaultValue);
         }

         @Override
         public void removeFrom(KeyValueStorage storage) {
            parent.removeFrom(storage);
         }

         @Override
         public SafeParameter<T> withDefault(T defaultValue) {
            return parent.withDefault(defaultValue);
         }
      };
   }
}

