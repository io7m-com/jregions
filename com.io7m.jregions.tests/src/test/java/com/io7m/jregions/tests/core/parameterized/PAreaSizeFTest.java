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
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeF;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesF;
import com.io7m.jregions.generators.PAreaSizeFGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PAreaSizeFTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(10.0f, PAreaSizeF.of(10.0f, 0.0f).sizeX(), 0.0f);
    Assertions.assertEquals(10.0f, PAreaSizeF.of(0.0f, 10.0f).sizeY(), 0.0f);
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      PAreaSizeF.of(10.0f, 0.0f),
      PAreaSizeF.of(10.0f, 0.0f));
    Assertions.assertEquals(
      PAreaSizeF.of(0.0f, 10.0f),
      PAreaSizeF.of(0.0f, 10.0f));

    Assertions.assertNotEquals(
      PAreaSizeF.of(10.0f, 0.0f),
      PAreaSizeF.of(9.9f, 0.0f));
    Assertions.assertNotEquals(
      PAreaSizeF.of(0.0f, 10.0f),
      PAreaSizeF.of(0.0f, 9.9f));
    Assertions.assertNotEquals(PAreaSizeF.of(0.0f, 10.0f), null);
    Assertions.assertNotEquals(PAreaSizeF.of(0.0f, 10.0f), Integer.valueOf(23));
  }

  @Test
  public void testIncludesReflexive()
  {
    QuickCheck.forAll(
      PAreaSizeFGenerator.create(),
      new AbstractCharacteristic<PAreaSizeF<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeF<Object> area)
          throws Throwable
        {
          Assertions.assertTrue(PAreaSizesF.includes(area, area));
        }
      });
  }

  @Test
  public void testIncludesTransitive()
  {
    final PAreaSizeFGenerator<Object> generator = PAreaSizeFGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaSizeF<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaSizeF<Object> a)
          throws Throwable
        {
          final PAreaSizeF<Object> b = generator.next();
          final PAreaSizeF<Object> c = generator.next();

          if (PAreaSizesF.includes(a, b) && PAreaSizesF.includes(b, c)) {
            Assertions.assertTrue(PAreaSizesF.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeF.of(-1.0f, 0.0f);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeF.of(0.0f, -1.0f);
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      PAreaSizeF.of(10.0f, 0.0f).toString(),
      PAreaSizeF.of(10.0f, 0.0f).toString());
    Assertions.assertEquals(
      PAreaSizeF.of(0.0f, 10.0f).toString(),
      PAreaSizeF.of(0.0f, 10.0f).toString());

    Assertions.assertNotEquals(
      PAreaSizeF.of(10.0f, 0.0f).toString(),
      PAreaSizeF.of(9.9f, 0.0f).toString());
    Assertions.assertNotEquals(
      PAreaSizeF.of(0.0f, 10.0f).toString(),
      PAreaSizeF.of(0.0f, 9.9f).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) PAreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) PAreaSizeF.of(10.0f, 0.0f).hashCode());
    Assertions.assertEquals(
      (long) PAreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) PAreaSizeF.of(0.0f, 10.0f).hashCode());

    Assertions.assertNotEquals(
      (long) PAreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) PAreaSizeF.of(9.9f, 0.0f).hashCode());
    Assertions.assertNotEquals(
      (long) PAreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) PAreaSizeF.of(0.0f, 9.9f).hashCode());
  }
}
