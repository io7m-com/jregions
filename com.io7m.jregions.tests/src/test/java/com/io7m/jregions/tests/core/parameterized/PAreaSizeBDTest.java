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
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public final class PAreaSizeBDTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(
      new BigDecimal(100),
      PAreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO).width());
    Assert.assertEquals(
      new BigDecimal(100),
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)).height());
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
          Assert.assertTrue(PAreaSizesBD.includes(area, area));
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
            Assert.assertTrue(PAreaSizesBD.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    PAreaSizeBD.of(new BigDecimal("-1"), BigDecimal.ZERO);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("-1"));
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO),
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO));
    Assert.assertEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)));

    Assert.assertNotEquals(
      PAreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO),
      PAreaSizeBD.of(
        new BigDecimal("99"),
        BigDecimal.ZERO));
    Assert.assertNotEquals(
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal("99")));
    Assert.assertNotEquals(PAreaSizeBD.of(
      BigDecimal.ZERO,
      new BigDecimal(100)), null);
    Assert.assertNotEquals(
      PAreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString());
    Assert.assertEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString());

    Assert.assertNotEquals(
      PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      PAreaSizeBD.of(new BigDecimal("99"), BigDecimal.ZERO).toString());
    Assert.assertNotEquals(
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode());
    Assert.assertEquals(
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode());

    Assert.assertNotEquals(
      (long) PAreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) PAreaSizeBD.of(new BigDecimal("99"), BigDecimal.ZERO).hashCode());
    Assert.assertNotEquals(
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) PAreaSizeBD.of(BigDecimal.ZERO, new BigDecimal("99")).hashCode());
  }
}
