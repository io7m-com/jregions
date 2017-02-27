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

import com.io7m.jregions.core.AreaSizesB;
import com.io7m.jregions.core.AreaSizesD;
import com.io7m.jregions.core.AreaSizesI;
import com.io7m.jregions.core.AreaSizesL;
import com.io7m.jregions.core.parameterized.PAreaSizesB;
import com.io7m.jregions.core.parameterized.PAreaSizesD;
import com.io7m.jregions.core.parameterized.PAreaSizesI;
import com.io7m.jregions.core.parameterized.PAreaSizesL;
import com.io7m.jregions.core.parameterized.PAreasD;
import com.io7m.jregions.core.parameterized.PAreasI;
import com.io7m.jregions.core.parameterized.PAreasL;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
  public void testAreaSizesB()
    throws Throwable
  {
    this.checkUnreachable(AreaSizesB.class);
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
  public void testPAreaSizesB()
    throws Throwable
  {
    this.checkUnreachable(PAreaSizesB.class);
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
  public void testPAreasL()
    throws Throwable
  {
    this.checkUnreachable(PAreasL.class);
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
  public void testPAreasD()
    throws Throwable
  {
    this.checkUnreachable(PAreasD.class);
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
