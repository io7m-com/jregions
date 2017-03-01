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

import com.io7m.jregions.core.parameterized.sizes.PAreaSizeI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesI;
import com.io7m.jregions.generators.PAreaSizeIGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

public final class PAreaSizeITest
{
  @Test
  public void testIdentities()
  {
    Assert.assertEquals(100L, (long) PAreaSizeI.of(100, 0).width());
    Assert.assertEquals(100L, (long) PAreaSizeI.of(0, 100).height());
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(PAreaSizeI.of(100, 0), PAreaSizeI.of(100, 0));
    Assert.assertEquals(PAreaSizeI.of(0, 100), PAreaSizeI.of(0, 100));

    Assert.assertNotEquals(PAreaSizeI.of(100, 0), PAreaSizeI.of(99, 0));
    Assert.assertNotEquals(PAreaSizeI.of(0, 100), PAreaSizeI.of(0, 99));
    Assert.assertNotEquals(PAreaSizeI.of(0, 100), null);
    Assert.assertNotEquals(PAreaSizeI.of(0, 100), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      PAreaSizeIGenerator.create(),
      new AbstractCharacteristic<PAreaSizeI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeI<Object> area)
          throws Throwable
        {
          Assert.assertTrue(PAreaSizesI.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeIGenerator<Object> generator = PAreaSizeIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeI<Object> a)
          throws Throwable
        {
          final PAreaSizeI<Object> b = generator.next();
          final PAreaSizeI<Object> c = generator.next();

          if (PAreaSizesI.includes(a, b) && PAreaSizesI.includes(b, c)) {
            Assert.assertTrue(PAreaSizesI.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      PAreaSizeI.of(100, 0).toString(),
      PAreaSizeI.of(100, 0).toString());
    Assert.assertEquals(
      PAreaSizeI.of(0, 100).toString(),
      PAreaSizeI.of(0, 100).toString());

    Assert.assertNotEquals(
      PAreaSizeI.of(100, 0).toString(),
      PAreaSizeI.of(99, 0).toString());
    Assert.assertNotEquals(
      PAreaSizeI.of(0, 100).toString(),
      PAreaSizeI.of(0, 99).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) PAreaSizeI.of(100, 0).hashCode(),
      (long) PAreaSizeI.of(100, 0).hashCode());
    Assert.assertEquals(
      (long) PAreaSizeI.of(0, 100).hashCode(),
      (long) PAreaSizeI.of(0, 100).hashCode());

    Assert.assertNotEquals(
      (long) PAreaSizeI.of(100, 0).hashCode(),
      (long) PAreaSizeI.of(99, 0).hashCode());
    Assert.assertNotEquals(
      (long) PAreaSizeI.of(0, 100).hashCode(),
      (long) PAreaSizeI.of(0, 99).hashCode());
  }
}
