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

public final class Bug12VolumeTest
{
  @Test
  public void testBug12L()
  {
    final VolumeL area0 =
      VolumeL.of(0L, 10L, 0L, 10L, 0L, 10L);
    final VolumeL area1 =
      VolumeL.of(10L, 20L, 10L, 20L, 10L, 20L);

    Assertions.assertFalse(VolumesL.overlaps(area0, area1));
  }

  @Test
  public void testBug12I()
  {
    final VolumeI area0 =
      VolumeI.of(0, 10, 0, 10, 0, 10);
    final VolumeI area1 =
      VolumeI.of(10, 20, 10, 20, 10, 20);

    Assertions.assertFalse(VolumesI.overlaps(area0, area1));
  }

  @Test
  public void testBug12D()
  {
    final VolumeD area0 =
      VolumeD.of(0.0, 10.0, 0.0, 10.0, 0.0, 10.0);
    final VolumeD area1 =
      VolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0);

    Assertions.assertFalse(VolumesD.overlaps(area0, area1));
  }

  @Test
  public void testBug12F()
  {
    final VolumeF area0 =
      VolumeF.of(0.0F, 10.0F, 0.0F, 10.0F, 0.0F, 10.0F);
    final VolumeF area1 =
      VolumeF.of(10.0F, 20.0F, 10.0F, 20.0F, 10.0F, 20.0F);

    Assertions.assertFalse(VolumesF.overlaps(area0, area1));
  }

  @Test
  public void testBug12BI()
  {
    final VolumeBI area0 =
      VolumeBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final VolumeBI area1 =
      VolumeBI.of(
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L));

    Assertions.assertFalse(VolumesBI.overlaps(area0, area1));
  }

  @Test
  public void testBug12BD()
  {
    final VolumeBD area0 =
      VolumeBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final VolumeBD area1 =
      VolumeBD.of(
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L));

    Assertions.assertFalse(VolumesBD.overlaps(area0, area1));
  }

  @Test
  public void testBugP12L()
  {
    final PVolumeL<Object> area0 =
      PVolumeL.of(0L, 10L, 0L, 10L, 0L, 10L);
    final PVolumeL<Object> area1 =
      PVolumeL.of(10L, 20L, 10L, 20L, 10L, 20L);

    Assertions.assertFalse(PVolumesL.overlaps(area0, area1));
  }

  @Test
  public void testBugP12I()
  {
    final PVolumeI<Object> area0 =
      PVolumeI.of(0, 10, 0, 10, 0, 10);
    final PVolumeI<Object> area1 =
      PVolumeI.of(10, 20, 10, 20, 10, 20);

    Assertions.assertFalse(PVolumesI.overlaps(area0, area1));
  }

  @Test
  public void testBugP12D()
  {
    final PVolumeD<Object> area0 =
      PVolumeD.of(0.0, 10.0, 0.0, 10.0, 0.0, 10.0);
    final PVolumeD<Object> area1 =
      PVolumeD.of(10.0, 20.0, 10.0, 20.0, 10.0, 20.0);

    Assertions.assertFalse(PVolumesD.overlaps(area0, area1));
  }

  @Test
  public void testBugP12F()
  {
    final PVolumeF<Object> area0 =
      PVolumeF.of(0.0F, 10.0F, 0.0F, 10.0F, 0.0F, 10.0F);
    final PVolumeF<Object> area1 =
      PVolumeF.of(10.0F, 20.0F, 10.0F, 20.0F, 10.0F, 20.0F);

    Assertions.assertFalse(PVolumesF.overlaps(area0, area1));
  }

  @Test
  public void testBugP12BI()
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
      PVolumeBI.of(
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L));

    Assertions.assertFalse(PVolumesBI.overlaps(area0, area1));
  }

  @Test
  public void testBugP12BD()
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
      PVolumeBD.of(
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L));

    Assertions.assertFalse(PVolumesBD.overlaps(area0, area1));
  }
}
