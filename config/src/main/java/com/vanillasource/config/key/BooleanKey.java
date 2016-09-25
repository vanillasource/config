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

public class BooleanKey implements Key<Boolean> {
   private String name;
   private boolean defaultValue;

   public BooleanKey(String name, boolean defaultValue) {
      this.name = name;
      this.defaultValue = defaultValue;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public Boolean getDefaultValue() {
      return defaultValue;
   }

   @Override
   public String serialize(Boolean value) {
      return value.toString();
   }

   @Override
   public Boolean deserialize(String serializedValue) {
      return Boolean.valueOf(serializedValue);
   }
}


