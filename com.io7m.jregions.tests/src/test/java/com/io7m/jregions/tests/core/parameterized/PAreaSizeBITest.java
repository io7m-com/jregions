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
import com.io7m.jregions.core.parameterized.PAreaSizeBI;
import com.io7m.jregions.core.parameterized.PAreaSizesBI;
import com.io7m.jregions.generators.PAreaSizeBIGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

public final class PAreaSizeBITest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(
      new BigInteger("100"),
      PAreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO).width());
    Assert.assertEquals(
      new BigInteger("100"),
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")).height());
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
          Assert.assertTrue(PAreaSizesBI.includes(area, area));
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
            Assert.assertTrue(PAreaSizesBI.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    PAreaSizeBI.of(new BigInteger("-1"), BigInteger.ZERO);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("-1"));
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO),
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO));
    Assert.assertEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")));

    Assert.assertNotEquals(
      PAreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO),
      PAreaSizeBI.of(
        new BigInteger("99"),
        BigInteger.ZERO));
    Assert.assertNotEquals(
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("99")));
    Assert.assertNotEquals(PAreaSizeBI.of(
      BigInteger.ZERO,
      new BigInteger("100")), null);
    Assert.assertNotEquals(
      PAreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString());
    Assert.assertEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString());

    Assert.assertNotEquals(
      PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      PAreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).toString());
    Assert.assertNotEquals(
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode());
    Assert.assertEquals(
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode());

    Assert.assertNotEquals(
      (long) PAreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) PAreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).hashCode());
    Assert.assertNotEquals(
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) PAreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).hashCode());
  }
}
