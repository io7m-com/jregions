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

import com.io7m.jregions.core.parameterized.volumes.PVolumeBD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeBI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeF;
import com.io7m.jregions.core.parameterized.volumes.PVolumeI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeL;
import com.io7m.jregions.core.parameterized.volumes.PVolumesBD;
import com.io7m.jregions.core.parameterized.volumes.PVolumesBI;
import com.io7m.jregions.core.parameterized.volumes.PVolumesD;
import com.io7m.jregions.core.parameterized.volumes.PVolumesF;
import com.io7m.jregions.core.parameterized.volumes.PVolumesI;
import com.io7m.jregions.core.parameterized.volumes.PVolumesL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Bug14PVolumeTest
{
  @Test
  public void testBugOverlapsL()
  {
    final PVolumeL<Object> area0 =
      PVolumeL.of(
        0L,
        10L,
        0L,
        10L,
        0L,
        10L);
    final PVolumeL<Object> area1 =
      PVolumesL.create(
        0L,
        0L,
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(PVolumesL.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesL.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsI()
  {
    final PVolumeI<Object> area0 =
      PVolumeI.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final PVolumeI<Object> area1 =
      PVolumesI.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(PVolumesI.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsD()
  {
    final PVolumeD<Object> area0 =
      PVolumeD.of(
        (double) 0L,
        10.0,
        (double) 0L,
        10.0,
        (double) 0L,
        10.0);
    final PVolumeD<Object> area1 =
      PVolumesD.create(
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L);
    Assertions.assertTrue(PVolumesD.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesD.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsF()
  {
    final PVolumeF<Object> area0 =
      PVolumeF.of(
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F);
    final PVolumeF<Object> area1 =
      PVolumesF.create(
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L);
    Assertions.assertTrue(PVolumesF.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesF.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBI()
  {
    final PVolumeBI<Object> area0 =
      PVolumeBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final PVolumeBI<Object> area1 =
      PVolumesBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(PVolumesBI.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesBI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBD()
  {
    final PVolumeBD<Object> area0 =
      PVolumeBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final PVolumeBD<Object> area1 =
      PVolumesBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(PVolumesBD.overlaps(area0, area1));
    Assertions.assertTrue(PVolumesBD.overlaps(area1, area0));
  }

  @Test
  public void testBugContainsL()
  {
    final PVolumeL<Object> area0 =
      PVolumeL.of(
        0L,
        10L,
        0L,
        10L,
        0L,
        10L);
    final PVolumeL<Object> area1 =
      PVolumesL.create(
        0L,
        0L,
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(PVolumesL.contains(area0, area1));
  }

  @Test
  public void testBugContainsI()
  {
    final PVolumeI<Object> area0 =
      PVolumeI.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final PVolumeI<Object> area1 =
      PVolumesI.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(PVolumesI.contains(area0, area1));
  }

  @Test
  public void testBugContainsD()
  {
    final PVolumeD<Object> area0 =
      PVolumeD.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final PVolumeD<Object> area1 =
      PVolumesD.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(PVolumesD.contains(area0, area1));
  }

  @Test
  public void testBugContainsF()
  {
    final PVolumeF<Object> area0 =
      PVolumeF.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final PVolumeF<Object> area1 =
      PVolumesF.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(PVolumesF.contains(area0, area1));
  }

  @Test
  public void testBugContainsBI()
  {
    final PVolumeBI<Object> area0 =
      PVolumeBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final PVolumeBI<Object> area1 =
      PVolumesBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(PVolumesBI.contains(area0, area1));
  }

  @Test
  public void testBugContainsBD()
  {
    final PVolumeBD<Object> area0 =
      PVolumeBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final PVolumeBD<Object> area1 =
      PVolumesBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(PVolumesBD.contains(area0, area1));
  }
}
