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

import com.io7m.jregions.core.unparameterized.sizes.AreaSizeL;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesL;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class AreaSizeLTest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(100L, AreaSizeL.of(100L, 0L).sizeX());
    Assertions.assertEquals(100L, AreaSizeL.of(0L, 100L).sizeY());
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(AreaSizeL.of(100L, 0L), AreaSizeL.of(100L, 0L));
    Assertions.assertEquals(AreaSizeL.of(0L, 100L), AreaSizeL.of(0L, 100L));

    Assertions.assertNotEquals(AreaSizeL.of(100L, 0L), AreaSizeL.of(99L, 0L));
    Assertions.assertNotEquals(AreaSizeL.of(0L, 100L), AreaSizeL.of(0L, 99L));
    Assertions.assertNotEquals(AreaSizeL.of(0L, 100L), null);
    Assertions.assertNotEquals(AreaSizeL.of(0L, 100L), Integer.valueOf(23));
  }

  @Property
  public void testIncludesReflexive(
    final @ForAll AreaSizeL area)
  {
    Assertions.assertTrue(AreaSizesL.includes(area, area));
  }

  @Property
  public void testIncludesTransitive(
    final @ForAll AreaSizeL a,
    final @ForAll AreaSizeL b,
    final @ForAll AreaSizeL c)
  {
    if (AreaSizesL.includes(a, b) && AreaSizesL.includes(b, c)) {
      Assertions.assertTrue(AreaSizesL.includes(a, c));
    }
  }

  @Property
  public void testAreaIdentity(
    final @ForAll AreaSizeL a)
  {
    final var s = AreaSizesL.area(a);
    Assertions.assertEquals(a.sizeX(), s.sizeX());
    Assertions.assertEquals(a.sizeY(), s.sizeY());
    Assertions.assertEquals(0L, s.minimumX());
    Assertions.assertEquals(0L, s.minimumY());
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      AreaSizeL.of(100L, 0L).toString(),
      AreaSizeL.of(100L, 0L).toString());
    Assertions.assertEquals(
      AreaSizeL.of(0L, 100L).toString(),
      AreaSizeL.of(0L, 100L).toString());

    Assertions.assertNotEquals(
      AreaSizeL.of(100L, 0L).toString(),
      AreaSizeL.of(99L, 0L).toString());
    Assertions.assertNotEquals(
      AreaSizeL.of(0L, 100L).toString(),
      AreaSizeL.of(0L, 99L).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) AreaSizeL.of(100L, 0L).hashCode(),
      (long) AreaSizeL.of(100L, 0L).hashCode());
    Assertions.assertEquals(
      (long) AreaSizeL.of(0L, 100L).hashCode(),
      (long) AreaSizeL.of(0L, 100L).hashCode());

    Assertions.assertNotEquals(
      (long) AreaSizeL.of(100L, 0L).hashCode(),
      (long) AreaSizeL.of(99L, 0L).hashCode());
    Assertions.assertNotEquals(
      (long) AreaSizeL.of(0L, 100L).hashCode(),
      (long) AreaSizeL.of(0L, 99L).hashCode());
  }
}
