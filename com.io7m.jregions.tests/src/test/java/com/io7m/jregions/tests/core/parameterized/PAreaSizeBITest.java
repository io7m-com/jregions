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
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesBI;
import com.io7m.jregions.generators.PAreaSizeBIGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public final class PAreaSizeBITest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(
      new BigInteger("100"),
      PAreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO).sizeX());
    Assertions.assertEquals(
      new BigInteger("100"),
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")).sizeY());
  }

  @Test
  public void testIncludesReflexive()
  {
    final PAreaSizeBIGenerator<Object> gen = PAreaSizeBIGenerator.create();
    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic<PAreaSizeBI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeBI<Object> area)
          throws Throwable
        {
          Assertions.assertTrue(PAreaSizesBI.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeBIGenerator<Object> generator = PAreaSizeBIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeBI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeBI<Object> a)
          throws Throwable
        {
          final PAreaSizeBI<Object> b = generator.next();
          final PAreaSizeBI<Object> c = generator.next();

          if (PAreaSizesBI.includes(a, b) && PAreaSizesBI.includes(b, c)) {
            Assertions.assertTrue(PAreaSizesBI.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeBI.of(new BigInteger("-1"), BigInteger.ZERO);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("-1"));
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO),
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO));
    Assertions.assertEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")));

    Assertions.assertNotEquals(
      PAreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO),
      PAreaSizeBI.of(
        new BigInteger("99"),
        BigInteger.ZERO));
    Assertions.assertNotEquals(
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("99")));
    Assertions.assertNotEquals(PAreaSizeBI.of(
      BigInteger.ZERO,
      new BigInteger("100")), null);
    Assertions.assertNotEquals(
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString());
    Assertions.assertEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString());

    Assertions.assertNotEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      PAreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).toString());
    Assertions.assertNotEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode());
    Assertions.assertEquals(
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode());

    Assertions.assertNotEquals(
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) PAreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).hashCode());
    Assertions.assertNotEquals(
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).hashCode());
  }
}
