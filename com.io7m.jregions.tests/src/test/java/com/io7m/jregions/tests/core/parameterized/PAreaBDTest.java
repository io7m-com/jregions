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
import com.io7m.jregions.core.parameterized.areas.PAreaBD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class PAreaBDTest
{
  @Test
  public void testIdentities()
  {
    final PAreaBD<Object> area = PAreaBD.of(
      BigDecimal.ZERO,
      new BigDecimal("100"),
      BigDecimal.ZERO,
      new BigDecimal("100"));
    Assertions.assertEquals(BigDecimal.ZERO, area.minimumX());
    Assertions.assertEquals(BigDecimal.ZERO, area.minimumY());
    Assertions.assertEquals(new BigDecimal("100"), area.sizeX());
    Assertions.assertEquals(new BigDecimal("100"), area.sizeY());
    Assertions.assertEquals(new BigDecimal("100"), area.maximumX());
    Assertions.assertEquals(new BigDecimal("100"), area.maximumY());
  }

  @Test
  public void testBadX()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaBD.of(
          BigDecimal.TEN,
          new BigDecimal("9"),
          BigDecimal.ZERO,
          new BigDecimal("100"));
      });
    Assertions.assertTrue(e.getMessage().contains("X"));
  }

  @Test
  public void testBadY()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaBD.of(
          BigDecimal.ZERO,
          new BigDecimal("100"),
          BigDecimal.TEN,
          new BigDecimal("9"));
      });
    Assertions.assertTrue(e.getMessage().contains("Y"));
  }
}
