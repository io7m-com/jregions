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


import com.io7m.jregions.core.unparameterized.volumes.VolumeBD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeBI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeF;
import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import com.io7m.jregions.core.unparameterized.volumes.VolumesBD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesBI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesF;
import com.io7m.jregions.core.unparameterized.volumes.VolumesI;
import com.io7m.jregions.core.unparameterized.volumes.VolumesL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Bug14VolumeTest
{
  @Test
  public void testBugOverlapsL()
  {
    final var area0 =
      VolumeL.of(
        0L,
        10L,
        0L,
        10L,
        0L,
        10L);
    final var area1 =
      VolumesL.create(
        0L,
        0L,
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(VolumesL.overlaps(area0, area1));
    Assertions.assertTrue(VolumesL.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsI()
  {
    final var area0 =
      VolumeI.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final var area1 =
      VolumesI.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(VolumesI.overlaps(area0, area1));
    Assertions.assertTrue(VolumesI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsD()
  {
    final var area0 =
      VolumeD.of(
        (double) 0L,
        10.0,
        (double) 0L,
        10.0,
        (double) 0L,
        10.0);
    final var area1 =
      VolumesD.create(
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L,
        (double) 0L);
    Assertions.assertTrue(VolumesD.overlaps(area0, area1));
    Assertions.assertTrue(VolumesD.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsF()
  {
    final var area0 =
      VolumeF.of(
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F,
        (float) 0L,
        10.0F);
    final var area1 =
      VolumesF.create(
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L,
        (float) 0L);
    Assertions.assertTrue(VolumesF.overlaps(area0, area1));
    Assertions.assertTrue(VolumesF.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBI()
  {
    final var area0 =
      VolumeBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final var area1 =
      VolumesBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(VolumesBI.overlaps(area0, area1));
    Assertions.assertTrue(VolumesBI.overlaps(area1, area0));
  }

  @Test
  public void testBugOverlapsBD()
  {
    final var area0 =
      VolumeBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final var area1 =
      VolumesBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(VolumesBD.overlaps(area0, area1));
    Assertions.assertTrue(VolumesBD.overlaps(area1, area0));
  }

  @Test
  public void testBugContainsL()
  {
    final var area0 =
      VolumeL.of(
        0L,
        10L,
        0L,
        10L,
        0L,
        10L);
    final var area1 =
      VolumesL.create(
        0L,
        0L,
        0L,
        0L,
        0L,
        0L);
    Assertions.assertTrue(VolumesL.contains(area0, area1));
  }

  @Test
  public void testBugContainsI()
  {
    final var area0 =
      VolumeI.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final var area1 =
      VolumesI.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(VolumesI.contains(area0, area1));
  }

  @Test
  public void testBugContainsD()
  {
    final var area0 =
      VolumeD.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final var area1 =
      VolumesD.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(VolumesD.contains(area0, area1));
  }

  @Test
  public void testBugContainsF()
  {
    final var area0 =
      VolumeF.of(
        0,
        10,
        0,
        10,
        0,
        10);
    final var area1 =
      VolumesF.create(
        0,
        0,
        0,
        0,
        0,
        0);
    Assertions.assertTrue(VolumesF.contains(area0, area1));
  }

  @Test
  public void testBugContainsBI()
  {
    final var area0 =
      VolumeBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final var area1 =
      VolumesBI.create(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(0L));
    Assertions.assertTrue(VolumesBI.contains(area0, area1));
  }

  @Test
  public void testBugContainsBD()
  {
    final var area0 =
      VolumeBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final var area1 =
      VolumesBD.create(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(0L));
    Assertions.assertTrue(VolumesBD.contains(area0, area1));
  }
}
