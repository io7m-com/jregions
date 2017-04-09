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
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBI;
import com.io7m.jregions.generators.AreaSizeBIGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

public final class AreaSizeBITest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(
      new BigInteger("100"),
      AreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO).width());
    Assert.assertEquals(
      new BigInteger("100"),
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")).height());
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      AreaSizeBIGenerator.create(),
      new AbstractCharacteristic<AreaSizeBI>()
      {
        @Override
        protected void doSpecify(final AreaSizeBI area)
          throws Throwable
        {
          Assert.assertTrue(AreaSizesBI.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final AreaSizeBIGenerator generator = AreaSizeBIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeBI>()
      {
        @Override
        protected void doSpecify(final AreaSizeBI a)
          throws Throwable
        {
          final AreaSizeBI b = generator.next();
          final AreaSizeBI c = generator.next();

          if (AreaSizesBI.includes(a, b) && AreaSizesBI.includes(b, c)) {
            Assert.assertTrue(AreaSizesBI.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testAreaIdentity()
  {
    final AreaSizeBIGenerator generator = AreaSizeBIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeBI>()
      {
        @Override
        protected void doSpecify(final AreaSizeBI a)
          throws Throwable
        {
          final AreaBI s = AreaSizesBI.area(a);
          Assert.assertEquals(a.width(), s.width());
          Assert.assertEquals(a.height(), s.height());
          Assert.assertEquals(BigInteger.ZERO, s.minimumX());
          Assert.assertEquals(BigInteger.ZERO, s.minimumY());
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    AreaSizeBI.of(new BigInteger("-1"), BigInteger.ZERO);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    AreaSizeBI.of(BigInteger.ZERO, new BigInteger("-1"));
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO),
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO));
    Assert.assertEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")));

    Assert.assertNotEquals(
      AreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO),
      AreaSizeBI.of(
        new BigInteger("99"),
        BigInteger.ZERO));
    Assert.assertNotEquals(
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("99")));
    Assert.assertNotEquals(AreaSizeBI.of(
      BigInteger.ZERO,
      new BigInteger("100")), null);
    Assert.assertNotEquals(
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString());
    Assert.assertEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString());

    Assert.assertNotEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).toString());
    Assert.assertNotEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode());
    Assert.assertEquals(
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode());

    Assert.assertNotEquals(
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).hashCode());
    Assert.assertNotEquals(
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).hashCode());
  }
}
