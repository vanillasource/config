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

package com.vanillasource.config.parameter;

import com.vanillasource.config.KeyValueStorage;
import java.util.function.Function;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Optional;

/**
 * Factory methods for creating typed configuration parameters.
 */
public final class Parameters {
   private Parameters() {
   }

   public static Parameter<Boolean> booleanParameter(String name) {
      return stringParameter(name).map(Object::toString, Boolean::valueOf);
   }

   public static Parameter<BigDecimal> bigDecimalParameter(String name) {
      return stringParameter(name).map(Object::toString, BigDecimal::new);
   }

   public static Parameter<Integer> integerParameter(String name) {
      return stringParameter(name).map(Object::toString, Integer::valueOf);
   }

   public static Parameter<Long> longParameter(String name) {
      return stringParameter(name).map(Object::toString, Long::valueOf);
   }

   public static Parameter<Double> doubleParameter(String name, double defaultValue) {
      return stringParameter(name).map(Object::toString, Double::valueOf);
   }

   public static Parameter<Duration> isoDurationParameter(String name, Duration defaultValue) {
      return stringParameter(name).map(Object::toString, Duration::parse);
   }

   public static Parameter<Date> dateParameter(String name, String format) {
      return stringParameter(name).map(
            date -> new SimpleDateFormat(format).format(date),
            str -> {
               try {
                  return new SimpleDateFormat(format).parse(str);
               } catch (ParseException e) {
                  throw new IllegalArgumentException("couldn't parse date configuration '"+str+"'", e);
               }
            });
   }

   public static Parameter<String> stringParameter(String name) {
      return new Parameter<String>() {
         @Override
         public void storeTo(KeyValueStorage storage, String value) {
            storage.store(name, value);
         }

         @Override
         public Optional<String> loadFrom(KeyValueStorage storage) {
            return storage.load(name);
         }

         @Override
         public void removeFrom(KeyValueStorage storage) {
            storage.remove(name);
         }
      };
   }

}
