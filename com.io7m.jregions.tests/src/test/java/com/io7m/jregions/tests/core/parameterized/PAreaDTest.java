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
import com.io7m.jregions.core.parameterized.areas.PAreaD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PAreaDTest
{
  @Test
  public void testIdentities()
  {
    final PAreaD<Object> area = PAreaD.of(0, 100.0, 0, 100.0);
    Assertions.assertEquals(0.0, area.minimumX(), 0.0);
    Assertions.assertEquals(0.0, area.minimumY(), 0.0);
    Assertions.assertEquals(100.0, area.sizeX(), 0.0);
    Assertions.assertEquals(100.0, area.sizeY(), 0.0);
    Assertions.assertEquals(100.0, area.maximumX(), 0.0);
    Assertions.assertEquals(100.0, area.maximumY(), 0.0);
  }

  @Test
  public void testBadX()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaD.of(10.0, 9.0, 0.0, 100.0);
      });
    Assertions.assertTrue(e.getMessage().contains("X"));
  }

  @Test
  public void testBadY()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        PAreaD.of(0, 100.0, 10.0, 9.0);
      });
    Assertions.assertTrue(e.getMessage().contains("Y"));
  }
}
