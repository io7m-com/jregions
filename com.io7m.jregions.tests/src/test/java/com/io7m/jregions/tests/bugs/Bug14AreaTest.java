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

import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaF;
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.core.unparameterized.areas.AreasBD;
import com.io7m.jregions.core.unparameterized.areas.AreasBI;
import com.io7m.jregions.core.unparameterized.areas.AreasD;
import com.io7m.jregions.core.unparameterized.areas.AreasF;
import com.io7m.jregions.core.unparameterized.areas.AreasI;
import com.io7m.jregions.core.unparameterized.areas.AreasL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Bug14AreaTest
{
  @Test
  public void testBugOverlapsL()
  {
    final var area0 =
      AreaL.of(
        0L,
        10L,
        0L,
        10L);
    final var area1 =
      AreasL.create(
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(AreasL.overlaps(area0, area1));
    Assertions.assertTrue(AreasL.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsI()
  {
    final var area0 =
      AreaI.of(
        0,
        10,
        0,
        10);
    final var area1 =
      AreasI.create(
        0,
        0,
        0,
        0);
    Assertions.assertTrue(AreasI.overlaps(area0, area1));
    Assertions.assertTrue(AreasI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsD()
  {
    final var area0 =
      AreaD.of(
        (double) 0L,
        10.0,
        (double) 0L,
        10.0);
    final var area1 =
      AreasD.create(
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L);
    Assertions.assertTrue(AreasD.overlaps(area0, area1));
    Assertions.assertTrue(AreasD.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsF()
  {
    final var area0 =
      AreaF.of(
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F);
    final var area1 =
      AreasF.create(
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L);
    Assertions.assertTrue(AreasF.overlaps(area0, area1));
    Assertions.assertTrue(AreasF.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBI()
  {
    final var area0 =
      AreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final var area1 =
      AreasBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(AreasBI.overlaps(area0, area1));
    Assertions.assertTrue(AreasBI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBD()
  {
    final var area0 =
      AreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final var area1 =
      AreasBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(AreasBD.overlaps(area0, area1));
    Assertions.assertTrue(AreasBD.overlaps(area1, area0));
  }

  @Test
  public void testBugContainsL()
  {
    final var area0 =
      AreaL.of(
        0L,
        10L,
        0L,
        10L);
    final var area1 =
      AreasL.create(
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(AreasL.contains(area0, area1));
  }

  @Test
  public void testBugContainsI()
  {
    final var area0 =
      AreaI.of(
        0,
        10,
        0,
        10);
    final var area1 =
      AreasI.create(
        0,
        0,
        0,
        0);
    Assertions.assertTrue(AreasI.contains(area0, area1));
  }

  @Test
  public void testBugContainsD()
  {
    final var area0 =
      AreaD.of(
        0,
        10,
        0,
        10);
    final var area1 =
      AreasD.create(
        0,
        0,
        0,
        0);
    Assertions.assertTrue(AreasD.contains(area0, area1));
  }

  @Test
  public void testBugContainsF()
  {
    final var area0 =
      AreaF.of(
        0,
        10,
        0,
        10);
    final var area1 =
      AreasF.create(
        0,
        0,
        0,
        0);
    Assertions.assertTrue(AreasF.contains(area0, area1));
  }

  @Test
  public void testBugContainsBI()
  {
    final var area0 =
      AreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final var area1 =
      AreasBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(AreasBI.contains(area0, area1));
  }

  @Test
  public void testBugContainsBD()
  {
    final var area0 =
      AreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final var area1 =
      AreasBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(AreasBD.contains(area0, area1));
  }
}
