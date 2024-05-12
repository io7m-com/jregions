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
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizesD;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PAreaSizeDTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(10.0, PAreaSizeD.of(10.0, 0.0).sizeX(), 0.0);
    Assertions.assertEquals(10.0, PAreaSizeD.of(0.0, 10.0).sizeY(), 0.0);
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(PAreaSizeD.of(10.0, 0.0), PAreaSizeD.of(10.0, 0.0));
    Assertions.assertEquals(PAreaSizeD.of(0.0, 10.0), PAreaSizeD.of(0.0, 10.0));

    Assertions.assertNotEquals(
      PAreaSizeD.of(10.0, 0.0),
      PAreaSizeD.of(9.9, 0.0));
    Assertions.assertNotEquals(
      PAreaSizeD.of(0.0, 10.0),
      PAreaSizeD.of(0.0, 9.9));
    Assertions.assertNotEquals(PAreaSizeD.of(0.0, 10.0), null);
    Assertions.assertNotEquals(PAreaSizeD.of(0.0, 10.0), Integer.valueOf(23));
  }

  @Property
  public void testIncludesReflexive(
    final @ForAll PAreaSizeD area)
  {
    Assertions.assertTrue(PAreaSizesD.includes(area, area));
  }

  @Property
  public void testIncludesTransitive(
    final @ForAll PAreaSizeD a,
    final @ForAll PAreaSizeD b,
    final @ForAll PAreaSizeD c)
  {
    if (PAreaSizesD.includes(a, b) && PAreaSizesD.includes(b, c)) {
      Assertions.assertTrue(PAreaSizesD.includes(a, c));
    }
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeD.of(-1.0, 0.0);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        PAreaSizeD.of(0.0, -1.0);
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      PAreaSizeD.of(10.0, 0.0).toString(),
      PAreaSizeD.of(10.0, 0.0).toString());
    Assertions.assertEquals(
      PAreaSizeD.of(0.0, 10.0).toString(),
      PAreaSizeD.of(0.0, 10.0).toString());

    Assertions.assertNotEquals(
      PAreaSizeD.of(10.0, 0.0).toString(),
      PAreaSizeD.of(9.9, 0.0).toString());
    Assertions.assertNotEquals(
      PAreaSizeD.of(0.0, 10.0).toString(),
      PAreaSizeD.of(0.0, 9.9).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) PAreaSizeD.of(10.0, 0.0).hashCode(),
      (long) PAreaSizeD.of(10.0, 0.0).hashCode());
    Assertions.assertEquals(
      (long) PAreaSizeD.of(0.0, 10.0).hashCode(),
      (long) PAreaSizeD.of(0.0, 10.0).hashCode());

    Assertions.assertNotEquals(
      (long) PAreaSizeD.of(10.0, 0.0).hashCode(),
      (long) PAreaSizeD.of(9.9, 0.0).hashCode());
    Assertions.assertNotEquals(
      (long) PAreaSizeD.of(0.0, 10.0).hashCode(),
      (long) PAreaSizeD.of(0.0, 9.9).hashCode());
  }
}
