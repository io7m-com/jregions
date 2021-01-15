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
import com.io7m.jregions.core.unparameterized.volumes.VolumeBD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public final class VolumeBDTest
{
  @Test
  public void testIdentities()
  {
    final var volume = VolumeBD.of(new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("0"), new BigDecimal("100"));
    Assertions.assertEquals(new BigDecimal("0"), volume.minimumX());
    Assertions.assertEquals(new BigDecimal("0"), volume.minimumY());
    Assertions.assertEquals(new BigDecimal("0"), volume.minimumZ());
    Assertions.assertEquals(new BigDecimal("100"), volume.sizeX());
    Assertions.assertEquals(new BigDecimal("100"), volume.sizeY());
    Assertions.assertEquals(new BigDecimal("100"), volume.sizeZ());
    Assertions.assertEquals(new BigDecimal("100"), volume.maximumX());
    Assertions.assertEquals(new BigDecimal("100"), volume.maximumY());
    Assertions.assertEquals(new BigDecimal("100"), volume.maximumZ());
  }

  @Test
  public void testBadX()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        VolumeBD.of(new BigDecimal("10"), new BigDecimal("9"), new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("0"), new BigDecimal("100"));
      });
    Assertions.assertTrue(e.getMessage().contains("X"));
  }

  @Test
  public void testBadY()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        VolumeBD.of(new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("10"), new BigDecimal("9"), new BigDecimal("0"), new BigDecimal("100"));
      });
    Assertions.assertTrue(e.getMessage().contains("Y"));
  }

  @Test
  public void testBadZ()
  {
    final var e = Assertions.assertThrows(
      PreconditionViolationException.class,
      () -> {
        VolumeBD.of(new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("10"), new BigDecimal("9"));
      });
    Assertions.assertTrue(e.getMessage().contains("Z"));
  }
}
