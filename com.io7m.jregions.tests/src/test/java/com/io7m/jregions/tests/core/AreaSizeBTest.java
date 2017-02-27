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

package com.io7m.jregions.tests.core;

import com.io7m.jaffirm.core.PreconditionViolationException;
import com.io7m.jregions.core.AreaSizeB;
import com.io7m.jregions.core.AreaSizesB;
import com.io7m.jregions.generators.AreaSizeBGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

public final class AreaSizeBTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(
      new BigInteger("100"),
      AreaSizeB.of(
        new BigInteger("100"),
        BigInteger.ZERO).width());
    Assert.assertEquals(
      new BigInteger("100"),
      AreaSizeB.of(
        BigInteger.ZERO,
        new BigInteger("100")).height());
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      AreaSizeBGenerator.create(),
      new AbstractCharacteristic<AreaSizeB>()
      {
        @Override
        protected void doSpecify(final AreaSizeB area)
          throws Throwable
        {
          Assert.assertTrue(AreaSizesB.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final AreaSizeBGenerator generator = AreaSizeBGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeB>()
      {
        @Override
        protected void doSpecify(final AreaSizeB a)
          throws Throwable
        {
          final AreaSizeB b = generator.next();
          final AreaSizeB c = generator.next();

          if (AreaSizesB.includes(a, b) && AreaSizesB.includes(b, c)) {
            Assert.assertTrue(AreaSizesB.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    AreaSizeB.of(new BigInteger("-1"), BigInteger.ZERO);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    AreaSizeB.of(BigInteger.ZERO, new BigInteger("-1"));
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(
      AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO),
      AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO));
    Assert.assertEquals(
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")),
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")));

    Assert.assertNotEquals(
      AreaSizeB.of(
        new BigInteger("100"),
        BigInteger.ZERO),
      AreaSizeB.of(
        new BigInteger("99"),
        BigInteger.ZERO));
    Assert.assertNotEquals(
      AreaSizeB.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      AreaSizeB.of(
        BigInteger.ZERO,
        new BigInteger("99")));
    Assert.assertNotEquals(AreaSizeB.of(
      BigInteger.ZERO,
      new BigInteger("100")), null);
    Assert.assertNotEquals(
      AreaSizeB.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).toString());
    Assert.assertEquals(
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).toString());

    Assert.assertNotEquals(
      AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeB.of(new BigInteger("99"), BigInteger.ZERO).toString());
    Assert.assertNotEquals(
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeB.of(BigInteger.ZERO, new BigInteger("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).hashCode());
    Assert.assertEquals(
      (long) AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).hashCode());

    Assert.assertNotEquals(
      (long) AreaSizeB.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeB.of(new BigInteger("99"), BigInteger.ZERO).hashCode());
    Assert.assertNotEquals(
      (long) AreaSizeB.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeB.of(BigInteger.ZERO, new BigInteger("99")).hashCode());
  }
}
