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
import com.io7m.jregions.core.parameterized.areas.PAreaBD;
import com.io7m.jregions.core.parameterized.areas.PAreaBI;
import com.io7m.jregions.core.parameterized.areas.PAreaD;
import com.io7m.jregions.core.parameterized.areas.PAreaF;
import com.io7m.jregions.core.parameterized.areas.PAreaI;
import com.io7m.jregions.core.parameterized.areas.PAreaL;
import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaF;
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.generators.PAreaBDGenerator;
import com.io7m.jregions.generators.PAreaBIGenerator;
import com.io7m.jregions.generators.PAreaDGenerator;
import com.io7m.jregions.generators.PAreaFGenerator;
import com.io7m.jregions.generators.PAreaIGenerator;
import com.io7m.jregions.generators.PAreaLGenerator;
import com.io7m.jregions.tests.percentage_pass.PercentPassExtension;
import com.io7m.jregions.tests.percentage_pass.PercentPassing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PercentPassExtension.class)
public final class AreaConversionsTest
{
  @PercentPassing
  public void testIdentityL0()
  {
    final PAreaLGenerator<Object> generator = PAreaLGenerator.create();

    final PAreaL<Object> a0 = generator.next();
    final AreaL a1 = AreaConversions.toAreaL(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final PAreaL<Object> a2 = AreaConversions.toPAreaL(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityI0()
  {
    final PAreaIGenerator<Object> generator = PAreaIGenerator.create();
    final PAreaI<Object> a0 = generator.next();
    final AreaI a1 = AreaConversions.toAreaI(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final PAreaI<Object> a2 = AreaConversions.toPAreaI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBD0()
  {
    final PAreaBDGenerator<Object> generator = PAreaBDGenerator.create();
    final PAreaBD<Object> a0 = generator.next();
    final AreaBD a1 = AreaConversions.toAreaBD(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final PAreaBD<Object> a2 = AreaConversions.toPAreaBD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityBI0()
  {
    final PAreaBIGenerator<Object> generator = PAreaBIGenerator.create();
    final PAreaBI<Object> a0 = generator.next();
    final AreaBI a1 = AreaConversions.toAreaBI(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX());
    Assertions.assertEquals(a0.minimumY(), a1.minimumY());
    Assertions.assertEquals(a0.maximumX(), a1.maximumX());
    Assertions.assertEquals(a0.maximumY(), a1.maximumY());
    final PAreaBI<Object> a2 = AreaConversions.toPAreaBI(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityD0()
  {
    final PAreaDGenerator<Object> generator = PAreaDGenerator.create();
    final PAreaD<Object> a0 = generator.next();
    final AreaD a1 = AreaConversions.toAreaD(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
    Assertions.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
    Assertions.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
    Assertions.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
    final PAreaD<Object> a2 = AreaConversions.toPAreaD(a1);
    Assertions.assertEquals(a0, a2);
  }

  @PercentPassing
  public void testIdentityF0()
  {
    final PAreaFGenerator<Object> generator = PAreaFGenerator.create();
    final PAreaF<Object> a0 = generator.next();
    final AreaF a1 = AreaConversions.toAreaF(a0);
    Assertions.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
    Assertions.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
    Assertions.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
    Assertions.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
    final PAreaF<Object> a2 = AreaConversions.toPAreaF(a1);
    Assertions.assertEquals(a0, a2);
  }
}
