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
import java.time.Duration;

public class DurationKey implements Key<Duration> {
   private String name;
   private Duration defaultValue;

   public DurationKey(String name, Duration defaultValue) {
      this.name = name;
      this.defaultValue = defaultValue;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public Duration getDefaultValue() {
      return defaultValue;
   }

   @Override
   public String serialize(Duration value) {
      return value.toString();
   }

   @Override
   public Duration deserialize(String serializedValue) {
      return Duration.parse(serializedValue);
   }
}


