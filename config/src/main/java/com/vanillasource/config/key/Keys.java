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
import java.util.function.Function;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Factory methods for creating default-valued, typed configuration keys.
 */
public final class Keys {
   private Keys() {
   }

   public static <T> Key<T> defaultValued(Key<T> delegate, T defaultValue) {
      return new DefaultValuedKey<>(delegate, defaultValue);
   }

   public static <T> Key<T> singleValued(String name,
         Function<T, String> serializer, Function<String, T> deserializer) {
      return new SingleValuedKey<>(name, serializer, deserializer);
   }

   public static Key<String> stringKey(String name, String defaultValue) {
      return defaultValued(singleValued(name, s->s, s->s), defaultValue);
   }

   public static Key<Boolean> booleanKey(String name, boolean defaultValue) {
      return defaultValued(singleValued(name, b->b.toString(), s->Boolean.valueOf(s)), defaultValue);
   }

   public static Key<BigDecimal> bigDecimalKey(String name, BigDecimal defaultValue) {
      return defaultValued(singleValued(name, b->b.toString(), s->new BigDecimal(s)), defaultValue);
   }

   public static Key<Integer> integerKey(String name, int defaultValue) {
      return defaultValued(singleValued(name, n->n.toString(), s->Integer.valueOf(s)), defaultValue);
   }

   public static Key<Long> longKey(String name, long defaultValue) {
      return defaultValued(singleValued(name, n->n.toString(), s->Long.valueOf(s)), defaultValue);
   }

   public static Key<Double> doubleKey(String name, double defaultValue) {
      return defaultValued(singleValued(name, n->n.toString(), s->Double.valueOf(s)), defaultValue);
   }

   public static Key<Duration> doubleKey(String name, Duration defaultValue) {
      return defaultValued(singleValued(name, d->d.toString(), s->Duration.parse(s)), defaultValue);
   }

   public static Key<Date> dateKey(String name, String format, Date defaultValue) {
      return defaultValued(singleValued(name,
               date -> new SimpleDateFormat(format).format(date),
               str -> {
                  try {
                     return new SimpleDateFormat(format).parse(str);
                  } catch (ParseException e) {
                     throw new IllegalArgumentException("couldn't parse date configuration '"+str+"'", e);
                  }
            }),
            defaultValue);
   }

   public static Key<Date> dateKey(String name, String format, String defaultValue) {
      try {
         return dateKey(name, format,
               new SimpleDateFormat(format).parse(defaultValue));
      } catch (ParseException e) {
         throw new IllegalArgumentException("couldn't parse default date '"+defaultValue+"'", e);
      }
   }
}
