/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jregions.tests.core;

import com.io7m.jregions.core.conversions.AreaConversions;
import com.io7m.jregions.core.conversions.AreaSizeConversions;
import com.io7m.jregions.core.parameterized.areas.PAreasBD;
import com.io7m.jregions.core.parameterized.areas.PAreasBI;
import com.io7m.jregions.core.parameterized.areas.PAreasD;
import com.io7m.jregions.core.parameterized.areas.PAreasF;
import com.io7m.jregions.core.parameterized.areas.PAreasI;
import com.io7m.jregions.core.parameterized.areas.PAreasL;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesBD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesBI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesF;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesL;
import com.io7m.jregions.core.parameterized.volumes.PVolumesBD;
import com.io7m.jregions.core.parameterized.volumes.PVolumesBI;
import com.io7m.jregions.core.parameterized.volumes.PVolumesD;
import com.io7m.jregions.core.parameterized.volumes.PVolumesF;
import com.io7m.jregions.core.parameterized.volumes.PVolumesI;
import com.io7m.jregions.core.parameterized.volumes.PVolumesL;
import com.io7m.jregions.core.unparameterized.areas.AreasBD;
import com.io7m.jregions.core.unparameterized.areas.AreasBI;
import com.io7m.jregions.core.unparameterized.areas.AreasD;
import com.io7m.jregions.core.unparameterized.areas.AreasF;
import com.io7m.jregions.core.unparameterized.areas.AreasI;
import com.io7m.jregions.core.unparameterized.areas.AreasL;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesL;
import com.io7m.jregions.core.unparameterized.volumes.VolumesBD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesBI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesF;
import com.io7m.jregions.core.unparameterized.volumes.VolumesI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesL;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public final class UnreachableTest
{
  private static void callPrivate(
    final Class<?> c)
    throws Throwable
  {
    try {
      final var constructor = c.getDeclaredConstructors()[0];
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (final InvocationTargetException e) {
      throw e.getCause();
    }
  }

  @TestFactory
  public Stream<DynamicTest> testUnreachables()
  {
    return Stream.of(
      AreaConversions.class,
      AreaSizeConversions.class,
      PAreasBD.class,
      PAreasBI.class,
      PAreasD.class,
      PAreasF.class,
      PAreasI.class,
      PAreasL.class,
      PAreaSizesBD.class,
      PAreaSizesBI.class,
      PAreaSizesD.class,
      PAreaSizesF.class,
      PAreaSizesI.class,
      PAreaSizesL.class,
      PVolumesBD.class,
      PVolumesBI.class,
      PVolumesD.class,
      PVolumesF.class,
      PVolumesI.class,
      PVolumesL.class,
      AreasBD.class,
      AreasBI.class,
      AreasD.class,
      AreasF.class,
      AreasI.class,
      AreasL.class,
      AreaSizesBD.class,
      AreaSizesBI.class,
      AreaSizesD.class,
      AreaSizesF.class,
      AreaSizesI.class,
      AreaSizesL.class,
      VolumesBD.class,
      VolumesBI.class,
      VolumesD.class,
      VolumesF.class,
      VolumesI.class,
      VolumesL.class
    ).map(clazz -> {
      return DynamicTest.dynamicTest("testUnreachable" + clazz, () -> {
        this.checkUnreachable(clazz);
      });
    });
  }

  private void checkUnreachable(
    final Class<?> c)
  {
    Assertions.assertThrows(UnreachableCodeException.class, () -> {
      callPrivate(c);
    });
  }
}
