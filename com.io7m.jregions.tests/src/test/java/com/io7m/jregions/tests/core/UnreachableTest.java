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
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public final class UnreachableTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static void callPrivate(
    final Class<?> c)
    throws Throwable
  {
    try {
      final Constructor<?> constructor = c.getDeclaredConstructors()[0];
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (final InvocationTargetException e) {
      throw e.getCause();
    }
  }

  @Test
  public void testAreaSizesBI()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesBI.class);
    Assert.fail();
  }

  @Test
  public void testAreaSizesBD()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesBD.class);
    Assert.fail();
  }

  @Test
  public void testAreaSizesL()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesL.class);
    Assert.fail();
  }

  @Test
  public void testAreaSizesI()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesI.class);
    Assert.fail();
  }

  @Test
  public void testAreaSizesD()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesD.class);
    Assert.fail();
  }

  @Test
  public void testAreaSizesF()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesF.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesBI()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesBI.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesBD()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesBD.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesL()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesL.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesI()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesI.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesD()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesD.class);
    Assert.fail();
  }

  @Test
  public void testPAreaSizesF()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesF.class);
    Assert.fail();
  }

  @Test
  public void testPAreasL()
    throws Throwable
  {
    this.checkUnreachable(PAreasL.class);
    Assert.fail();
  }

  @Test
  public void testPAreasBI()
    throws Throwable
  {
    this.checkUnreachable(PAreasBI.class);
    Assert.fail();
  }

  @Test
  public void testPAreasBD()
    throws Throwable
  {
    this.checkUnreachable(PAreasBD.class);
    Assert.fail();
  }

  @Test
  public void testPAreasI()
    throws Throwable
  {
    this.checkUnreachable(PAreasI.class);
    Assert.fail();
  }

  @Test
  public void testPAreasF()
    throws Throwable
  {
    this.checkUnreachable(PAreasF.class);
    Assert.fail();
  }

  @Test
  public void testPAreasD()
    throws Throwable
  {
    this.checkUnreachable(PAreasD.class);
    Assert.fail();
  }

  @Test
  public void testAreasL()
    throws Throwable
  {
    this.checkUnreachable(AreasL.class);
    Assert.fail();
  }

  @Test
  public void testAreasBI()
    throws Throwable
  {
    this.checkUnreachable(AreasBI.class);
    Assert.fail();
  }

  @Test
  public void testAreasBD()
    throws Throwable
  {
    this.checkUnreachable(AreasBD.class);
    Assert.fail();
  }

  @Test
  public void testAreasI()
    throws Throwable
  {
    this.checkUnreachable(AreasI.class);
    Assert.fail();
  }

  @Test
  public void testAreasF()
    throws Throwable
  {
    this.checkUnreachable(AreasF.class);
    Assert.fail();
  }

  @Test
  public void testAreasD()
    throws Throwable
  {
    this.checkUnreachable(AreasD.class);
    Assert.fail();
  }

  private void checkUnreachable(
    final Class<?> c)
    throws Throwable
  {
    this.expected.expect(UnreachableCodeException.class);
    callPrivate(c);
  }
}
