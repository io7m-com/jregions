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

import com.io7m.jregions.core.unparameterized.sizes.AreaSizeI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesI;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class AreaSizeITest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(100L, (long) AreaSizeI.of(100, 0).sizeX());
    Assertions.assertEquals(100L, (long) AreaSizeI.of(0, 100).sizeY());
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(AreaSizeI.of(100, 0), AreaSizeI.of(100, 0));
    Assertions.assertEquals(AreaSizeI.of(0, 100), AreaSizeI.of(0, 100));

    Assertions.assertNotEquals(AreaSizeI.of(100, 0), AreaSizeI.of(99, 0));
    Assertions.assertNotEquals(AreaSizeI.of(0, 100), AreaSizeI.of(0, 99));
    Assertions.assertNotEquals(AreaSizeI.of(0, 100), null);
    Assertions.assertNotEquals(AreaSizeI.of(0, 100), Integer.valueOf(23));
  }

  @Property
  public void testIncludesReflexive(
    final @ForAll AreaSizeI area)
  {
    Assertions.assertTrue(AreaSizesI.includes(area, area));
  }

  @Property
  public void testIncludesTransitive(
    final @ForAll AreaSizeI a,
    final @ForAll AreaSizeI b,
    final @ForAll AreaSizeI c)
  {
    if (AreaSizesI.includes(a, b) && AreaSizesI.includes(b, c)) {
      Assertions.assertTrue(AreaSizesI.includes(a, c));
    }
  }

  @Property
  public void testAreaIdentity(
    final @ForAll AreaSizeI a)
  {
    final var s = AreaSizesI.area(a);
    Assertions.assertEquals((long) a.sizeX(), (long) s.sizeX());
    Assertions.assertEquals((long) a.sizeY(), (long) s.sizeY());
    Assertions.assertEquals(0L, (long) s.minimumX());
    Assertions.assertEquals(0L, (long) s.minimumY());
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      AreaSizeI.of(100, 0).toString(),
      AreaSizeI.of(100, 0).toString());
    Assertions.assertEquals(
      AreaSizeI.of(0, 100).toString(),
      AreaSizeI.of(0, 100).toString());

    Assertions.assertNotEquals(
      AreaSizeI.of(100, 0).toString(),
      AreaSizeI.of(99, 0).toString());
    Assertions.assertNotEquals(
      AreaSizeI.of(0, 100).toString(),
      AreaSizeI.of(0, 99).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) AreaSizeI.of(100, 0).hashCode(),
      (long) AreaSizeI.of(100, 0).hashCode());
    Assertions.assertEquals(
      (long) AreaSizeI.of(0, 100).hashCode(),
      (long) AreaSizeI.of(0, 100).hashCode());

    Assertions.assertNotEquals(
      (long) AreaSizeI.of(100, 0).hashCode(),
      (long) AreaSizeI.of(99, 0).hashCode());
    Assertions.assertNotEquals(
      (long) AreaSizeI.of(0, 100).hashCode(),
      (long) AreaSizeI.of(0, 99).hashCode());
  }
}
