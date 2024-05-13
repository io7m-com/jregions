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

package com.io7m.jregions.tests.core.unparameterized;

import com.io7m.jaffirm.core.PreconditionViolationException;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBD;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public final class AreaSizeBDTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(
      new BigDecimal(100),
      AreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO).sizeX());
    Assertions.assertEquals(
      new BigDecimal(100),
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)).sizeY());
  }

  @Property
  public void testIncludesReflexive(
    final @ForAll AreaSizeBD area)
  {
    Assertions.assertTrue(AreaSizesBD.includes(area, area));
  }

  @Property
  public void testIncludesTransitive(
    final @ForAll AreaSizeBD a,
    final @ForAll AreaSizeBD b,
    final @ForAll AreaSizeBD c)
  {
    if (AreaSizesBD.includes(a, b) && AreaSizesBD.includes(b, c)) {
      Assertions.assertTrue(AreaSizesBD.includes(a, c));
    }
  }

  @Property
  public void testAreaIdentity(
    final @ForAll AreaSizeBD a)
  {
    final var s = AreaSizesBD.area(a);
    Assertions.assertEquals(a.sizeX(), s.sizeX());
    Assertions.assertEquals(a.sizeY(), s.sizeY());
    Assertions.assertEquals(BigDecimal.ZERO, s.minimumX());
    Assertions.assertEquals(BigDecimal.ZERO, s.minimumY());
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeBD.of(new BigDecimal(-1), BigDecimal.ZERO);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(-1));
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO),
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO));
    Assertions.assertEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)));

    Assertions.assertNotEquals(
      AreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO),
      AreaSizeBD.of(
        new BigDecimal(99),
        BigDecimal.ZERO));
    Assertions.assertNotEquals(
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(99)));
    Assertions.assertNotEquals(AreaSizeBD.of(
      BigDecimal.ZERO,
      new BigDecimal(100)), null);
    Assertions.assertNotEquals(
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString());
    Assertions.assertEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString());

    Assertions.assertNotEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      AreaSizeBD.of(new BigDecimal(99), BigDecimal.ZERO).toString());
    Assertions.assertNotEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(99)).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode());
    Assertions.assertEquals(
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode());

    Assertions.assertNotEquals(
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) AreaSizeBD.of(new BigDecimal(99), BigDecimal.ZERO).hashCode());
    Assertions.assertNotEquals(
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(99)).hashCode());
  }
}
