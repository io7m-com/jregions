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

package com.io7m.jregions.tests.core.parameterized;

import com.io7m.jaffirm.core.PreconditionViolationException;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesBD;
import com.io7m.jregions.generators.PAreaSizeBDGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public final class PAreaSizeBDTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(
      new BigDecimal(100),
      PAreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO).sizeX());
    Assertions.assertEquals(
      new BigDecimal(100),
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)).sizeY());
  }

  @Test
  public void testIncludesReflexive()
  {
    final PAreaSizeBDGenerator<Object> gen = PAreaSizeBDGenerator.create();
    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic<PAreaSizeBD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeBD<Object> area)
          throws Throwable
        {
          Assertions.assertTrue(PAreaSizesBD.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeBDGenerator<Object> generator = PAreaSizeBDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeBD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeBD<Object> a)
          throws Throwable
        {
          final PAreaSizeBD<Object> b = generator.next();
          final PAreaSizeBD<Object> c = generator.next();

          if (PAreaSizesBD.includes(a, b) && PAreaSizesBD.includes(b, c)) {
            Assertions.assertTrue(PAreaSizesBD.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeBD.of(new BigDecimal("-1"), BigDecimal.ZERO);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("-1"));
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO),
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO));
    Assertions.assertEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)));

    Assertions.assertNotEquals(
      PAreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO),
      PAreaSizeBD.of(
        new BigDecimal("99"),
        BigDecimal.ZERO));
    Assertions.assertNotEquals(
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal("99")));
    Assertions.assertNotEquals(PAreaSizeBD.of(
      BigDecimal.ZERO,
      new BigDecimal(100)), null);
    Assertions.assertNotEquals(
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString());
    Assertions.assertEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString());

    Assertions.assertNotEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      PAreaSizeBD.of(new BigDecimal("99"), BigDecimal.ZERO).toString());
    Assertions.assertNotEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode());
    Assertions.assertEquals(
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode());

    Assertions.assertNotEquals(
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) PAreaSizeBD.of(new BigDecimal("99"), BigDecimal.ZERO).hashCode());
    Assertions.assertNotEquals(
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("99")).hashCode());
  }
}
