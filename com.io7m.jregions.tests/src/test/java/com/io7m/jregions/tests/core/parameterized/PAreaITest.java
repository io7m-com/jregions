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
import com.io7m.jregions.core.parameterized.areas.PAreaI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PAreaITest
{
  @Test
  public void testIdentities()
  {
    final PAreaI<Object> area = PAreaI.of(0, 100, 0, 100);
    Assertions.assertEquals(0L, area.minimumX());
    Assertions.assertEquals(0L, area.minimumY());
    Assertions.assertEquals(100L, area.sizeX());
    Assertions.assertEquals(100L, area.sizeY());
    Assertions.assertEquals(100L, area.maximumX());
    Assertions.assertEquals(100L, area.maximumY());
  }

  @Test
  public void testBadX()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaI.of(10, 9, 0, 100);
      });
    Assertions.assertTrue(e.getMessage().contains("X"));
  }

  @Test
  public void testBadY()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaI.of(0, 100, 10, 9);
      });
    Assertions.assertTrue(e.getMessage().contains("Y"));
  }
}
