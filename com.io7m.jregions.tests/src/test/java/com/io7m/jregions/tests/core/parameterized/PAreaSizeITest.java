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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PAreaSizeITest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(100L, (long) PAreaSizeI.of(100, 0).sizeX());
    Assertions.assertEquals(100L, (long) PAreaSizeI.of(0, 100).sizeY());
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(PAreaSizeI.of(100, 0), PAreaSizeI.of(100, 0));
    Assertions.assertEquals(PAreaSizeI.of(0, 100), PAreaSizeI.of(0, 100));

    Assertions.assertNotEquals(PAreaSizeI.of(100, 0), PAreaSizeI.of(99, 0));
    Assertions.assertNotEquals(PAreaSizeI.of(0, 100), PAreaSizeI.of(0, 99));
    Assertions.assertNotEquals(PAreaSizeI.of(0, 100), null);
    Assertions.assertNotEquals(PAreaSizeI.of(0, 100), Integer.valueOf(23));
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
          Assertions.assertTrue(PAreaSizesI.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final var generator = PAreaSizeIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeI<Object> a)
          throws Throwable
        {
          final var b = generator.next();
          final var c = generator.next();

          if (PAreaSizesI.includes(a, b) && PAreaSizesI.includes(b, c)) {
            Assertions.assertTrue(PAreaSizesI.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      PAreaSizeI.of(100, 0).toString(),
      PAreaSizeI.of(100, 0).toString());
    Assertions.assertEquals(
      PAreaSizeI.of(0, 100).toString(),
      PAreaSizeI.of(0, 100).toString());

    Assertions.assertNotEquals(
      PAreaSizeI.of(100, 0).toString(),
      PAreaSizeI.of(99, 0).toString());
    Assertions.assertNotEquals(
      PAreaSizeI.of(0, 100).toString(),
      PAreaSizeI.of(0, 99).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) PAreaSizeI.of(100, 0).hashCode(),
      (long) PAreaSizeI.of(100, 0).hashCode());
    Assertions.assertEquals(
      (long) PAreaSizeI.of(0, 100).hashCode(),
      (long) PAreaSizeI.of(0, 100).hashCode());

    Assertions.assertNotEquals(
      (long) PAreaSizeI.of(100, 0).hashCode(),
      (long) PAreaSizeI.of(99, 0).hashCode());
    Assertions.assertNotEquals(
      (long) PAreaSizeI.of(0, 100).hashCode(),
      (long) PAreaSizeI.of(0, 99).hashCode());
  }
}
