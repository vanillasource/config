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

package com.vanillasource.config.parameter;

import com.vanillasource.config.GenericParameter;
import java.util.Optional;

/**
 * A parameter that loads and stores the same type and always
 * returns a value.
 */
public interface SafeParameter<T> extends GenericParameter<T, T> {
   /**
    * Add a default value that is always returned.
    */
   SafeParameter<T> withDefault(T defaultValue);
}

