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
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesF;
import com.io7m.jregions.generators.AreaSizeFGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class AreaSizeFTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    Assert.assertEquals(10.0f, AreaSizeF.of(10.0f, 0.0f).width(), 0.0f);
    Assert.assertEquals(10.0f, AreaSizeF.of(0.0f, 10.0f).height(), 0.0f);
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(AreaSizeF.of(10.0f, 0.0f), AreaSizeF.of(10.0f, 0.0f));
    Assert.assertEquals(AreaSizeF.of(0.0f, 10.0f), AreaSizeF.of(0.0f, 10.0f));

    Assert.assertNotEquals(AreaSizeF.of(10.0f, 0.0f), AreaSizeF.of(9.9f, 0.0f));
    Assert.assertNotEquals(AreaSizeF.of(0.0f, 10.0f), AreaSizeF.of(0.0f, 9.9f));
    Assert.assertNotEquals(AreaSizeF.of(0.0f, 10.0f), null);
    Assert.assertNotEquals(AreaSizeF.of(0.0f, 10.0f), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      AreaSizeFGenerator.create(),
      new AbstractCharacteristic<AreaSizeF>()
      {
        @Override
        protected void doSpecify(final AreaSizeF area)
          throws Throwable
        {
          Assert.assertTrue(AreaSizesF.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final AreaSizeFGenerator generator = AreaSizeFGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<AreaSizeF>()
      {
        @Override
        protected void doSpecify(final AreaSizeF a)
          throws Throwable
        {
          final AreaSizeF b = generator.next();
          final AreaSizeF c = generator.next();

          if (AreaSizesF.includes(a, b) && AreaSizesF.includes(b, c)) {
            Assert.assertTrue(AreaSizesF.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Width"));
    AreaSizeF.of(-1.0f, 0.0f);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Height"));
    AreaSizeF.of(0.0f, -1.0f);
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      AreaSizeF.of(10.0f, 0.0f).toString(),
      AreaSizeF.of(10.0f, 0.0f).toString());
    Assert.assertEquals(
      AreaSizeF.of(0.0f, 10.0f).toString(),
      AreaSizeF.of(0.0f, 10.0f).toString());

    Assert.assertNotEquals(
      AreaSizeF.of(10.0f, 0.0f).toString(),
      AreaSizeF.of(9.9f, 0.0f).toString());
    Assert.assertNotEquals(
      AreaSizeF.of(0.0f, 10.0f).toString(),
      AreaSizeF.of(0.0f, 9.9f).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode());
    Assert.assertEquals(
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode());

    Assert.assertNotEquals(
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) AreaSizeF.of(9.9f, 0.0f).hashCode());
    Assert.assertNotEquals(
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) AreaSizeF.of(0.0f, 9.9f).hashCode());
  }
}
