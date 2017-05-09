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

package com.io7m.jregions.tests.bugs;

import com.io7m.jregions.core.parameterized.areas.PAreaBD;
import com.io7m.jregions.core.parameterized.areas.PAreaBI;
import com.io7m.jregions.core.parameterized.areas.PAreaD;
import com.io7m.jregions.core.parameterized.areas.PAreaF;
import com.io7m.jregions.core.parameterized.areas.PAreaI;
import com.io7m.jregions.core.parameterized.areas.PAreaL;
import com.io7m.jregions.core.parameterized.areas.PAreasBD;
import com.io7m.jregions.core.parameterized.areas.PAreasBI;
import com.io7m.jregions.core.parameterized.areas.PAreasD;
import com.io7m.jregions.core.parameterized.areas.PAreasF;
import com.io7m.jregions.core.parameterized.areas.PAreasI;
import com.io7m.jregions.core.parameterized.areas.PAreasL;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Bug14PAreaTest
{
  @Test
  public void testBugOverlapsL()
  {
    final PAreaL<Object> area0 =
      PAreaL.of(
        0L,
        10L,
        0L,
        10L);
    final PAreaL<Object> area1 =
      PAreasL.create(
        0L,
        0L,
        0L,
        0L);
    Assert.assertTrue(PAreasL.overlaps(area0, area1));
    Assert.assertTrue(PAreasL.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsI()
  {
    final PAreaI<Object> area0 =
      PAreaI.of(
        0,
        10,
        0,
        10);
    final PAreaI<Object> area1 =
      PAreasI.create(
        0,
        0,
        0,
        0);
    Assert.assertTrue(PAreasI.overlaps(area0, area1));
    Assert.assertTrue(PAreasI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsD()
  {
    final PAreaD<Object> area0 =
      PAreaD.of(
        (double) 0L,
        10.0,
        (double) 0L,
        10.0);
    final PAreaD<Object> area1 =
      PAreasD.create(
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L);
    Assert.assertTrue(PAreasD.overlaps(area0, area1));
    Assert.assertTrue(PAreasD.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsF()
  {
    final PAreaF<Object> area0 =
      PAreaF.of(
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F);
    final PAreaF<Object> area1 =
      PAreasF.create(
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L);
    Assert.assertTrue(PAreasF.overlaps(area0, area1));
    Assert.assertTrue(PAreasF.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBI()
  {
    final PAreaBI<Object> area0 =
      PAreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final PAreaBI<Object> area1 =
      PAreasBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assert.assertTrue(PAreasBI.overlaps(area0, area1));
    Assert.assertTrue(PAreasBI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBD()
  {
    final PAreaBD<Object> area0 =
      PAreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final PAreaBD<Object> area1 =
      PAreasBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assert.assertTrue(PAreasBD.overlaps(area0, area1));
    Assert.assertTrue(PAreasBD.overlaps(area1, area0));
  }

  @Test
  public void testBugContainsL()
  {
    final PAreaL<Object> area0 =
      PAreaL.of(
        0L,
        10L,
        0L,
        10L);
    final PAreaL<Object> area1 =
      PAreasL.create(
        0L,
        0L,
        0L,
        0L);
    Assert.assertTrue(PAreasL.contains(area0, area1));
  }

  @Test
  public void testBugContainsI()
  {
    final PAreaI<Object> area0 =
      PAreaI.of(
        0,
        10,
        0,
        10);
    final PAreaI<Object> area1 =
      PAreasI.create(
        0,
        0,
        0,
        0);
    Assert.assertTrue(PAreasI.contains(area0, area1));
  }

  @Test
  public void testBugContainsD()
  {
    final PAreaD<Object> area0 =
      PAreaD.of(
        0,
        10,
        0,
        10);
    final PAreaD<Object> area1 =
      PAreasD.create(
        0,
        0,
        0,
        0);
    Assert.assertTrue(PAreasD.contains(area0, area1));
  }

  @Test
  public void testBugContainsF()
  {
    final PAreaF<Object> area0 =
      PAreaF.of(
        0,
        10,
        0,
        10);
    final PAreaF<Object> area1 =
      PAreasF.create(
        0,
        0,
        0,
        0);
    Assert.assertTrue(PAreasF.contains(area0, area1));
  }

  @Test
  public void testBugContainsBI()
  {
    final PAreaBI<Object> area0 =
      PAreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final PAreaBI<Object> area1 =
      PAreasBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assert.assertTrue(PAreasBI.contains(area0, area1));
  }

  @Test
  public void testBugContainsBD()
  {
    final PAreaBD<Object> area0 =
      PAreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final PAreaBD<Object> area1 =
      PAreasBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assert.assertTrue(PAreasBD.contains(area0, area1));
  }
}
