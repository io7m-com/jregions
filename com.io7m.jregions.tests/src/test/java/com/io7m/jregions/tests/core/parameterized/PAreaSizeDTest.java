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
import com.io7m.jregions.core.parameterized.PAreaSizeD;
import com.io7m.jregions.core.parameterized.PAreaSizesD;
import com.io7m.jregions.generators.PAreaSizeDGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PAreaSizeDTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(10.0, PAreaSizeD.of(10.0, 0.0).width(), 0.0);
    Assert.assertEquals(10.0, PAreaSizeD.of(0.0, 10.0).height(), 0.0);
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(PAreaSizeD.of(10.0, 0.0), PAreaSizeD.of(10.0, 0.0));
    Assert.assertEquals(PAreaSizeD.of(0.0, 10.0), PAreaSizeD.of(0.0, 10.0));

    Assert.assertNotEquals(PAreaSizeD.of(10.0, 0.0), PAreaSizeD.of(9.9, 0.0));
    Assert.assertNotEquals(PAreaSizeD.of(0.0, 10.0), PAreaSizeD.of(0.0, 9.9));
    Assert.assertNotEquals(PAreaSizeD.of(0.0, 10.0), null);
    Assert.assertNotEquals(PAreaSizeD.of(0.0, 10.0), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      PAreaSizeDGenerator.create(),
      new AbstractCharacteristic<PAreaSizeD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeD<Object> area)
          throws Throwable
        {
          Assert.assertTrue(PAreaSizesD.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeDGenerator<Object> generator = PAreaSizeDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeD<Object> a)
          throws Throwable
        {
          final PAreaSizeD<Object> b = generator.next();
          final PAreaSizeD<Object> c = generator.next();

          if (PAreaSizesD.includes(a, b) && PAreaSizesD.includes(b, c)) {
            Assert.assertTrue(PAreaSizesD.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    PAreaSizeD.of(-1.0, 0.0);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    PAreaSizeD.of(0.0, -1.0);
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      PAreaSizeD.of(10.0, 0.0).toString(),
      PAreaSizeD.of(10.0, 0.0).toString());
    Assert.assertEquals(
      PAreaSizeD.of(0.0, 10.0).toString(),
      PAreaSizeD.of(0.0, 10.0).toString());

    Assert.assertNotEquals(
      PAreaSizeD.of(10.0, 0.0).toString(),
      PAreaSizeD.of(9.9, 0.0).toString());
    Assert.assertNotEquals(
      PAreaSizeD.of(0.0, 10.0).toString(),
      PAreaSizeD.of(0.0, 9.9).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) PAreaSizeD.of(10.0, 0.0).hashCode(),
      (long) PAreaSizeD.of(10.0, 0.0).hashCode());
    Assert.assertEquals(
      (long) PAreaSizeD.of(0.0, 10.0).hashCode(),
      (long) PAreaSizeD.of(0.0, 10.0).hashCode());

    Assert.assertNotEquals(
      (long) PAreaSizeD.of(10.0, 0.0).hashCode(),
      (long) PAreaSizeD.of(9.9, 0.0).hashCode());
    Assert.assertNotEquals(
      (long) PAreaSizeD.of(0.0, 10.0).hashCode(),
      (long) PAreaSizeD.of(0.0, 9.9).hashCode());
  }
}
