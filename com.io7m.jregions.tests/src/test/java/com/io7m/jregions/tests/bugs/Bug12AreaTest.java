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

public final class Bug12AreaTest
{
  @Test
  public void testBug12L()
  {
    final AreaL area0 =
      AreaL.of(0L, 10L, 0L, 10L);
    final AreaL area1 =
      AreaL.of(10L, 20L, 10L, 20L);

    Assertions.assertFalse(AreasL.overlaps(area0, area1));
  }

  @Test
  public void testBug12I()
  {
    final AreaI area0 =
      AreaI.of(0, 10, 0, 10);
    final AreaI area1 =
      AreaI.of(10, 20, 10, 20);

    Assertions.assertFalse(AreasI.overlaps(area0, area1));
  }

  @Test
  public void testBug12D()
  {
    final AreaD area0 =
      AreaD.of(0.0, 10.0, 0.0, 10.0);
    final AreaD area1 =
      AreaD.of(10.0, 20.0, 10.0, 20.0);

    Assertions.assertFalse(AreasD.overlaps(area0, area1));
  }

  @Test
  public void testBug12F()
  {
    final AreaF area0 =
      AreaF.of(0.0F, 10.0F, 0.0F, 10.0F);
    final AreaF area1 =
      AreaF.of(10.0F, 20.0F, 10.0F, 20.0F);

    Assertions.assertFalse(AreasF.overlaps(area0, area1));
  }

  @Test
  public void testBug12BI()
  {
    final AreaBI area0 =
      AreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final AreaBI area1 =
      AreaBI.of(
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L));

    Assertions.assertFalse(AreasBI.overlaps(area0, area1));
  }

  @Test
  public void testBug12BD()
  {
    final AreaBD area0 =
      AreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final AreaBD area1 =
      AreaBD.of(
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L));

    Assertions.assertFalse(AreasBD.overlaps(area0, area1));
  }

  @Test
  public void testBugP12L()
  {
    final PAreaL<Object> area0 =
      PAreaL.of(0L, 10L, 0L, 10L);
    final PAreaL<Object> area1 =
      PAreaL.of(10L, 20L, 10L, 20L);

    Assertions.assertFalse(PAreasL.overlaps(area0, area1));
  }

  @Test
  public void testBugP12I()
  {
    final PAreaI<Object> area0 =
      PAreaI.of(0, 10, 0, 10);
    final PAreaI<Object> area1 =
      PAreaI.of(10, 20, 10, 20);

    Assertions.assertFalse(PAreasI.overlaps(area0, area1));
  }

  @Test
  public void testBugP12D()
  {
    final PAreaD<Object> area0 =
      PAreaD.of(0.0, 10.0, 0.0, 10.0);
    final PAreaD<Object> area1 =
      PAreaD.of(10.0, 20.0, 10.0, 20.0);

    Assertions.assertFalse(PAreasD.overlaps(area0, area1));
  }

  @Test
  public void testBugP12F()
  {
    final PAreaF<Object> area0 =
      PAreaF.of(0.0F, 10.0F, 0.0F, 10.0F);
    final PAreaF<Object> area1 =
      PAreaF.of(10.0F, 20.0F, 10.0F, 20.0F);

    Assertions.assertFalse(PAreasF.overlaps(area0, area1));
  }

  @Test
  public void testBugP12BI()
  {
    final PAreaBI<Object> area0 =
      PAreaBI.of(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(0L),
        BigInteger.valueOf(10L));
    final PAreaBI<Object> area1 =
      PAreaBI.of(
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L),
        BigInteger.valueOf(10L),
        BigInteger.valueOf(20L));

    Assertions.assertFalse(PAreasBI.overlaps(area0, area1));
  }

  @Test
  public void testBugP12BD()
  {
    final PAreaBD<Object> area0 =
      PAreaBD.of(
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(0L),
        BigDecimal.valueOf(10L));
    final PAreaBD<Object> area1 =
      PAreaBD.of(
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L),
        BigDecimal.valueOf(10L),
        BigDecimal.valueOf(20L));

    Assertions.assertFalse(PAreasBD.overlaps(area0, area1));
  }
}
