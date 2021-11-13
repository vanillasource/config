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
 * A generic parameter that takes some value to store and gives a
 * potentially different value when loaded.
 */
public interface GenericParameter<S, L> {
   /**
    * Store the given value into the given storage.
    */
   void storeTo(KeyValueStorage storage, S value);

   /**
    * Load the value for this key from the given storage.
    */
   L loadFrom(KeyValueStorage storage);

   /**
    * Remove this key from the storage.
    */
   void removeFrom(KeyValueStorage storage);
}

