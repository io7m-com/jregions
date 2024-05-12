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
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizesBI;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public final class AreaSizeBITest
{
  @Test
  public void testIdentities()
  {
    Assertions.assertEquals(
      new BigInteger("100"),
      AreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO).sizeX());
    Assertions.assertEquals(
      new BigInteger("100"),
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")).sizeY());
  }

  @Property
  public void testIncludesReflexive(
    final @ForAll AreaSizeBI area)
  {
    Assertions.assertTrue(AreaSizesBI.includes(area, area));
  }

  @Property
  public void testIncludesTransitive(
    final @ForAll AreaSizeBI a,
    final @ForAll AreaSizeBI b,
    final @ForAll AreaSizeBI c)
  {
    if (AreaSizesBI.includes(a, b) && AreaSizesBI.includes(b, c)) {
      Assertions.assertTrue(AreaSizesBI.includes(a, c));
    }
  }

  @Property
  public void testAreaIdentity(
    final @ForAll AreaSizeBI a)
  {
    final var s = AreaSizesBI.area(a);
    Assertions.assertEquals(a.sizeX(), s.sizeX());
    Assertions.assertEquals(a.sizeY(), s.sizeY());
    Assertions.assertEquals(BigInteger.ZERO, s.minimumX());
    Assertions.assertEquals(BigInteger.ZERO, s.minimumY());
  }

  @Test
  public void testNegativeWidth()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeBI.of(new BigInteger("-1"), BigInteger.ZERO);
      });
    Assertions.assertTrue(ex.getMessage().contains("Width"));
  }

  @Test
  public void testNegativeHeight()
  {
    final var ex =
      Assertions.assertThrows(PreconditionViolationException.class, () -> {
        AreaSizeBI.of(BigInteger.ZERO, new BigInteger("-1"));
      });
    Assertions.assertTrue(ex.getMessage().contains("Height"));
  }

  @Test
  public void testEquals()
  {
    Assertions.assertEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO),
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO));
    Assertions.assertEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")));

    Assertions.assertNotEquals(
      AreaSizeBI.of(
        new BigInteger("100"),
        BigInteger.ZERO),
      AreaSizeBI.of(
        new BigInteger("99"),
        BigInteger.ZERO));
    Assertions.assertNotEquals(
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("99")));
    Assertions.assertNotEquals(AreaSizeBI.of(
      BigInteger.ZERO,
      new BigInteger("100")), null);
    Assertions.assertNotEquals(
      AreaSizeBI.of(
        BigInteger.ZERO,
        new BigInteger("100")),
      Integer.valueOf(23));
  }

  @Test
  public void testToString()
  {
    Assertions.assertEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString());
    Assertions.assertEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString());

    Assertions.assertNotEquals(
      AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).toString(),
      AreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).toString());
    Assertions.assertNotEquals(
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).toString(),
      AreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).toString());
  }

  @Test
  public void testHashCode()
  {
    Assertions.assertEquals(
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode());
    Assertions.assertEquals(
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode());

    Assertions.assertNotEquals(
      (long) AreaSizeBI.of(new BigInteger("100"), BigInteger.ZERO).hashCode(),
      (long) AreaSizeBI.of(new BigInteger("99"), BigInteger.ZERO).hashCode());
    Assertions.assertNotEquals(
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("100")).hashCode(),
      (long) AreaSizeBI.of(BigInteger.ZERO, new BigInteger("99")).hashCode());
  }
}
