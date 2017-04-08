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
import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBD;
import com.io7m.jregions.generators.AreaSizeBDGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public final class AreaSizeBDTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(
      new BigDecimal(100),
      AreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO).width());
    Assert.assertEquals(
      new BigDecimal(100),
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)).height());
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      AreaSizeBDGenerator.create(),
      new AbstractCharacteristic<AreaSizeBD>()
      {
        @Override
        protected void doSpecify(final AreaSizeBD area)
          throws Throwable
        {
          Assert.assertTrue(AreaSizesBD.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final AreaSizeBDGenerator generator = AreaSizeBDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeBD>()
      {
        @Override
        protected void doSpecify(final AreaSizeBD a)
          throws Throwable
        {
          final AreaSizeBD b = generator.next();
          final AreaSizeBD c = generator.next();

          if (AreaSizesBD.includes(a, b) && AreaSizesBD.includes(b, c)) {
            Assert.assertTrue(AreaSizesBD.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testAreaIdentity()
  {
    final AreaSizeBDGenerator generator = AreaSizeBDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeBD>()
      {
        @Override
        protected void doSpecify(final AreaSizeBD a)
          throws Throwable
        {
          final AreaBD s = AreaSizesBD.area(a);
          Assert.assertEquals(a.width(), s.width());
          Assert.assertEquals(a.height(), s.height());
          Assert.assertEquals(BigDecimal.ZERO, s.minimumX());
          Assert.assertEquals(BigDecimal.ZERO, s.minimumY());
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    AreaSizeBD.of(new BigDecimal(-1), BigDecimal.ZERO);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(-1));
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO),
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO));
    Assert.assertEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)));

    Assert.assertNotEquals(
      AreaSizeBD.of(
        new BigDecimal(100),
        BigDecimal.ZERO),
      AreaSizeBD.of(
        new BigDecimal(99),
        BigDecimal.ZERO));
    Assert.assertNotEquals(
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(99)));
    Assert.assertNotEquals(AreaSizeBD.of(
      BigDecimal.ZERO,
      new BigDecimal(100)), null);
    Assert.assertNotEquals(
      AreaSizeBD.of(
        BigDecimal.ZERO,
        new BigDecimal(100)),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString());
    Assert.assertEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString());

    Assert.assertNotEquals(
      AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).toString(),
      AreaSizeBD.of(new BigDecimal(99), BigDecimal.ZERO).toString());
    Assert.assertNotEquals(
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).toString(),
      AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(99)).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode());
    Assert.assertEquals(
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode());

    Assert.assertNotEquals(
      (long) AreaSizeBD.of(new BigDecimal(100), BigDecimal.ZERO).hashCode(),
      (long) AreaSizeBD.of(new BigDecimal(99), BigDecimal.ZERO).hashCode());
    Assert.assertNotEquals(
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(100)).hashCode(),
      (long) AreaSizeBD.of(BigDecimal.ZERO, new BigDecimal(99)).hashCode());
  }
}
