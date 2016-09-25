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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateKey implements Key<Date> {
   private String name;
   private String format;
   private Date defaultValue;

   public DateKey(String name, String format, String defaultValue) {
      this.name = name;
      this.format = format;
      try {
         this.defaultValue = new SimpleDateFormat(format).parse(defaultValue);
      } catch (ParseException e) {
         throw new IllegalArgumentException("can not parse default value '"+defaultValue+"' with format: "+format, e);
      }
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public Date getDefaultValue() {
      return defaultValue;
   }

   @Override
   public String serialize(Date value) {
      return new SimpleDateFormat(format).format(value);
   }

   @Override
   public Date deserialize(String serializedValue) {
      try {
         return new SimpleDateFormat(format).parse(serializedValue);
      } catch (ParseException e) {
         throw new IllegalStateException("can not deserialize '"+serializedValue+"' with format: "+format, e);
      }
   }
}


