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

import com.io7m.jregions.core.conversions.AreaConversions;
import com.io7m.jregions.generators.PAreaBDGenerator;
import com.io7m.jregions.generators.PAreaBIGenerator;
import com.io7m.jregions.generators.PAreaDGenerator;
import com.io7m.jregions.generators.PAreaFGenerator;
import com.io7m.jregions.generators.PAreaIGenerator;
import com.io7m.jregions.generators.PAreaLGenerator;
import com.io7m.percentpass.extension.PercentPassing;
import org.junit.jupiter.api.Assertions;

public final class AreaConversionsTest
{
  @PercentPassing
  public void testIdentityL0()
  {
    final var generator = PAreaLGenerator.create();

    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaL(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final var a2 = AreaConversions.toPAreaL(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityI0()
  {
    final var generator = PAreaIGenerator.create();
    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaI(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final var a2 = AreaConversions.toPAreaI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBD0()
  {
    final var generator = PAreaBDGenerator.create();
    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaBD(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final var a2 = AreaConversions.toPAreaBD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBI0()
  {
    final var generator = PAreaBIGenerator.create();
    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaBI(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final var a2 = AreaConversions.toPAreaBI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityD0()
  {
    final var generator = PAreaDGenerator.create();
    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaD(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
    Assertions.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
    Assertions.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
    Assertions.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
    final var a2 = AreaConversions.toPAreaD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityF0()
  {
    final var generator = PAreaFGenerator.create();
    final var a0 = generator.next();
    final var a1 = AreaConversions.toAreaF(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
    Assertions.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
    Assertions.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
    Assertions.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
    final var a2 = AreaConversions.toPAreaF(a1);
    Assertions.assertEquals(a0, a2);
  }
}
