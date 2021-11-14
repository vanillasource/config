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

package com.vanillasource.config.configuration;

import com.vanillasource.config.MutableConfiguration;
import com.vanillasource.config.MutableKeyValueStorage;
import com.vanillasource.config.Parameter;
import com.vanillasource.config.MutableParameter;
import java.util.Optional;

public final class MutableStorageConfiguration implements MutableConfiguration {
   private final MutableKeyValueStorage storage;

   public MutableStorageConfiguration(MutableKeyValueStorage storage) {
      this.storage = storage;
   }

   @Override
   public <T> T get(Parameter<T> parameter) {
      return parameter.loadFrom(storage);
   }

   @Override
   public <S> void set(MutableParameter<S, ?> parameter, S value) {
      parameter.storeTo(storage, value);
   }

   @Override
   public void unset(MutableParameter<?, ?> parameter) {
      parameter.removeFrom(storage);
   }
}

