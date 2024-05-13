/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABDLITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jregions.tests.core.conversions;

import com.io7m.jregions.core.conversions.AreaSizeConversions;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeF;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeL;
import com.io7m.percentpass.extension.PercentPassing;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.Assertions;

public final class AreaSizeConversionsTest
{
  @PercentPassing
  public void testIdentityL0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeL.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeL(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX());
    Assertions.assertEquals(a0.sizeY(), a1.sizeY());
    final var a2 = AreaSizeConversions.toPAreaSizeL(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityI0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeI.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeI(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX());
    Assertions.assertEquals(a0.sizeY(), a1.sizeY());
    final var a2 = AreaSizeConversions.toPAreaSizeI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBD0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeBD.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeBD(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX());
    Assertions.assertEquals(a0.sizeY(), a1.sizeY());
    final var a2 = AreaSizeConversions.toPAreaSizeBD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBI0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeBI.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeBI(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX());
    Assertions.assertEquals(a0.sizeY(), a1.sizeY());
    final var a2 = AreaSizeConversions.toPAreaSizeBI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityD0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeD.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeD(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX(), 0.0);
    Assertions.assertEquals(a0.sizeY(), a1.sizeY(), 0.0);
    final var a2 = AreaSizeConversions.toPAreaSizeD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityF0()
  {
    final var generator = Arbitraries.defaultFor(PAreaSizeF.class);
    final var a0 = generator.sample();
    final var a1 = AreaSizeConversions.toAreaSizeF(a0);
    Assertions.assertEquals(a0.sizeX(), a1.sizeX(), 0.0);
    Assertions.assertEquals(a0.sizeY(), a1.sizeY(), 0.0);
    final var a2 = AreaSizeConversions.toPAreaSizeF(a1);
    Assertions.assertEquals(a0, a2);
  }
}
