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
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesD;
import com.io7m.jregions.generators.AreaSizeDGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class AreaSizeDTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(10.0, AreaSizeD.of(10.0, 0.0).sizeX(), 0.0);
    Assert.assertEquals(10.0, AreaSizeD.of(0.0, 10.0).sizeY(), 0.0);
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(AreaSizeD.of(10.0, 0.0), AreaSizeD.of(10.0, 0.0));
    Assert.assertEquals(AreaSizeD.of(0.0, 10.0), AreaSizeD.of(0.0, 10.0));

    Assert.assertNotEquals(AreaSizeD.of(10.0, 0.0), AreaSizeD.of(9.9, 0.0));
    Assert.assertNotEquals(AreaSizeD.of(0.0, 10.0), AreaSizeD.of(0.0, 9.9));
    Assert.assertNotEquals(AreaSizeD.of(0.0, 10.0), null);
    Assert.assertNotEquals(AreaSizeD.of(0.0, 10.0), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      AreaSizeDGenerator.create(),
      new AbstractCharacteristic<AreaSizeD>()
      {
        @Override
        protected void doSpecify(final AreaSizeD area)
          throws Throwable
        {
          Assert.assertTrue(AreaSizesD.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final AreaSizeDGenerator generator = AreaSizeDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeD>()
      {
        @Override
        protected void doSpecify(final AreaSizeD a)
          throws Throwable
        {
          final AreaSizeD b = generator.next();
          final AreaSizeD c = generator.next();

          if (AreaSizesD.includes(a, b) && AreaSizesD.includes(b, c)) {
            Assert.assertTrue(AreaSizesD.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testAreaIdentity()
  {
    final AreaSizeDGenerator generator = AreaSizeDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeD>()
      {
        @Override
        protected void doSpecify(final AreaSizeD a)
          throws Throwable
        {
          final AreaD s = AreaSizesD.area(a);
          Assert.assertEquals(a.sizeX(), s.sizeX(), 0.0);
          Assert.assertEquals(a.sizeY(), s.sizeY(), 0.0);
          Assert.assertEquals(0.0, s.minimumX(), 0.0);
          Assert.assertEquals(0.0, s.minimumY(), 0.0);
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    AreaSizeD.of(-1.0, 0.0);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    AreaSizeD.of(0.0, -1.0);
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      AreaSizeD.of(10.0, 0.0).toString(),
      AreaSizeD.of(10.0, 0.0).toString());
    Assert.assertEquals(
      AreaSizeD.of(0.0, 10.0).toString(),
      AreaSizeD.of(0.0, 10.0).toString());

    Assert.assertNotEquals(
      AreaSizeD.of(10.0, 0.0).toString(),
      AreaSizeD.of(9.9, 0.0).toString());
    Assert.assertNotEquals(
      AreaSizeD.of(0.0, 10.0).toString(),
      AreaSizeD.of(0.0, 9.9).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) AreaSizeD.of(10.0, 0.0).hashCode(),
      (long) AreaSizeD.of(10.0, 0.0).hashCode());
    Assert.assertEquals(
      (long) AreaSizeD.of(0.0, 10.0).hashCode(),
      (long) AreaSizeD.of(0.0, 10.0).hashCode());

    Assert.assertNotEquals(
      (long) AreaSizeD.of(10.0, 0.0).hashCode(),
      (long) AreaSizeD.of(9.9, 0.0).hashCode());
    Assert.assertNotEquals(
      (long) AreaSizeD.of(0.0, 10.0).hashCode(),
      (long) AreaSizeD.of(0.0, 9.9).hashCode());
  }
}
