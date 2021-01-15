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
import com.io7m.jregions.core.unparameterized.areas.AreaF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesF;
import com.io7m.jregions.generators.AreaSizeFGenerator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class AreaSizeFTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(10.0f, AreaSizeF.of(10.0f, 0.0f).sizeX(), 0.0f);
    Assertions.assertEquals(10.0f, AreaSizeF.of(0.0f, 10.0f).sizeY(), 0.0f);
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      AreaSizeF.of(10.0f, 0.0f),
      AreaSizeF.of(10.0f, 0.0f));
    Assertions.assertEquals(
      AreaSizeF.of(0.0f, 10.0f),
      AreaSizeF.of(0.0f, 10.0f));

    Assertions.assertNotEquals(
      AreaSizeF.of(10.0f, 0.0f),
      AreaSizeF.of(9.9f, 0.0f));
    Assertions.assertNotEquals(
      AreaSizeF.of(0.0f, 10.0f),
      AreaSizeF.of(0.0f, 9.9f));
    Assertions.assertNotEquals(AreaSizeF.of(0.0f, 10.0f), null);
    Assertions.assertNotEquals(AreaSizeF.of(0.0f, 10.0f), Integer.valueOf(23));
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
          Assertions.assertTrue(AreaSizesF.includes(area, area));
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
            Assertions.assertTrue(AreaSizesF.includes(a, c));
          }
        }
      });
  }

  @Test
  public void testAreaIdentity()
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
          final AreaF s = AreaSizesF.area(a);
          Assertions.assertEquals((double) a.sizeX(), (double) s.sizeX(), 0.0);
          Assertions.assertEquals((double) a.sizeY(), (double) s.sizeY(), 0.0);
          Assertions.assertEquals(0.0, (double) s.minimumX(), 0.0);
          Assertions.assertEquals(0.0, (double) s.minimumY(), 0.0);
        }
      });
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeF.of(-1.0f, 0.0f);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeF.of(0.0f, -1.0f);
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      AreaSizeF.of(10.0f, 0.0f).toString(),
      AreaSizeF.of(10.0f, 0.0f).toString());
    Assertions.assertEquals(
      AreaSizeF.of(0.0f, 10.0f).toString(),
      AreaSizeF.of(0.0f, 10.0f).toString());

    Assertions.assertNotEquals(
      AreaSizeF.of(10.0f, 0.0f).toString(),
      AreaSizeF.of(9.9f, 0.0f).toString());
    Assertions.assertNotEquals(
      AreaSizeF.of(0.0f, 10.0f).toString(),
      AreaSizeF.of(0.0f, 9.9f).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode());
    Assertions.assertEquals(
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode());

    Assertions.assertNotEquals(
      (long) AreaSizeF.of(10.0f, 0.0f).hashCode(),
      (long) AreaSizeF.of(9.9f, 0.0f).hashCode());
    Assertions.assertNotEquals(
      (long) AreaSizeF.of(0.0f, 10.0f).hashCode(),
      (long) AreaSizeF.of(0.0f, 9.9f).hashCode());
  }
}
