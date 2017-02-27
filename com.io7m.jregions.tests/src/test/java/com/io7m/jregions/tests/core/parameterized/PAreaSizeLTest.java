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

import com.io7m.jregions.core.parameterized.PAreaSizeL;
import com.io7m.jregions.core.parameterized.PAreaSizesL;
import com.io7m.jregions.generators.PAreaSizeLGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

public final class PAreaSizeLTest
{
  @Test
  public void testIdentities()
  {
    Assert.assertEquals(100L, PAreaSizeL.of(100L, 0L).width());
    Assert.assertEquals(100L, PAreaSizeL.of(0L, 100L).height());
  }

  @Test
  public void testEquals()
  {
    Assert.assertEquals(PAreaSizeL.of(100L, 0L), PAreaSizeL.of(100L, 0L));
    Assert.assertEquals(PAreaSizeL.of(0L, 100L), PAreaSizeL.of(0L, 100L));

    Assert.assertNotEquals(PAreaSizeL.of(100L, 0L), PAreaSizeL.of(99L, 0L));
    Assert.assertNotEquals(PAreaSizeL.of(0L, 100L), PAreaSizeL.of(0L, 99L));
    Assert.assertNotEquals(PAreaSizeL.of(0L, 100L), null);
    Assert.assertNotEquals(PAreaSizeL.of(0L, 100L), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      PAreaSizeLGenerator.create(),
      new AbstractCharacteristic<PAreaSizeL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeL<Object> area)
          throws Throwable
        {
          Assert.assertTrue(PAreaSizesL.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeLGenerator<Object> generator = PAreaSizeLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeL<Object> a)
          throws Throwable
        {
          final PAreaSizeL<Object> b = generator.next();
          final PAreaSizeL<Object> c = generator.next();

          if (PAreaSizesL.includes(a, b) && PAreaSizesL.includes(b, c)) {
            Assert.assertTrue(PAreaSizesL.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testToString()
  {
    Assert.assertEquals(
      PAreaSizeL.of(100L, 0L).toString(),
      PAreaSizeL.of(100L, 0L).toString());
    Assert.assertEquals(
      PAreaSizeL.of(0L, 100L).toString(),
      PAreaSizeL.of(0L, 100L).toString());

    Assert.assertNotEquals(
      PAreaSizeL.of(100L, 0L).toString(),
      PAreaSizeL.of(99L, 0L).toString());
    Assert.assertNotEquals(
      PAreaSizeL.of(0L, 100L).toString(),
      PAreaSizeL.of(0L, 99L).toString());
  }

  @Test
  public void testHashCode()
  {
    Assert.assertEquals(
      (long) PAreaSizeL.of(100L, 0L).hashCode(),
      (long) PAreaSizeL.of(100L, 0L).hashCode());
    Assert.assertEquals(
      (long) PAreaSizeL.of(0L, 100L).hashCode(),
      (long) PAreaSizeL.of(0L, 100L).hashCode());

    Assert.assertNotEquals(
      (long) PAreaSizeL.of(100L, 0L).hashCode(),
      (long) PAreaSizeL.of(99L, 0L).hashCode());
    Assert.assertNotEquals(
      (long) PAreaSizeL.of(0L, 100L).hashCode(),
      (long) PAreaSizeL.of(0L, 99L).hashCode());
  }
}
