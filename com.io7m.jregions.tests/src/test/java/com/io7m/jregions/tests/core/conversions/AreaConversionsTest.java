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
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

public final class AreaConversionsTest
{
  @Test
  public void testIdentityL0()
  {
    QuickCheck.forAll(
      PAreaLGenerator.create(),
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> a0)
          throws Throwable
        {
          final AreaL a1 = AreaConversions.toAreaL(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX());
          Assert.assertEquals(a0.minimumY(), a1.minimumY());
          Assert.assertEquals(a0.maximumX(), a1.maximumX());
          Assert.assertEquals(a0.maximumY(), a1.maximumY());
          final PAreaL<Object> a2 = AreaConversions.toPAreaL(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }

  @Test
  public void testIdentityI0()
  {
    QuickCheck.forAll(
      PAreaIGenerator.create(),
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> a0)
          throws Throwable
        {
          final AreaI a1 = AreaConversions.toAreaI(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX());
          Assert.assertEquals(a0.minimumY(), a1.minimumY());
          Assert.assertEquals(a0.maximumX(), a1.maximumX());
          Assert.assertEquals(a0.maximumY(), a1.maximumY());
          final PAreaI<Object> a2 = AreaConversions.toPAreaI(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }

  @Test
  public void testIdentityBD0()
  {
    QuickCheck.forAll(
      PAreaBDGenerator.create(),
      new AbstractCharacteristic<PAreaBD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaBD<Object> a0)
          throws Throwable
        {
          final AreaBD a1 = AreaConversions.toAreaBD(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX());
          Assert.assertEquals(a0.minimumY(), a1.minimumY());
          Assert.assertEquals(a0.maximumX(), a1.maximumX());
          Assert.assertEquals(a0.maximumY(), a1.maximumY());
          final PAreaBD<Object> a2 = AreaConversions.toPAreaBD(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }

  @Test
  public void testIdentityBI0()
  {
    QuickCheck.forAll(
      PAreaBIGenerator.create(),
      new AbstractCharacteristic<PAreaBI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaBI<Object> a0)
          throws Throwable
        {
          final AreaBI a1 = AreaConversions.toAreaBI(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX());
          Assert.assertEquals(a0.minimumY(), a1.minimumY());
          Assert.assertEquals(a0.maximumX(), a1.maximumX());
          Assert.assertEquals(a0.maximumY(), a1.maximumY());
          final PAreaBI<Object> a2 = AreaConversions.toPAreaBI(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }

  @Test
  public void testIdentityD0()
  {
    QuickCheck.forAll(
      PAreaDGenerator.create(),
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> a0)
          throws Throwable
        {
          final AreaD a1 = AreaConversions.toAreaD(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
          Assert.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
          Assert.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
          Assert.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
          final PAreaD<Object> a2 = AreaConversions.toPAreaD(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }

  @Test
  public void testIdentityF0()
  {
    QuickCheck.forAll(
      PAreaFGenerator.create(),
      new AbstractCharacteristic<PAreaF<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaF<Object> a0)
          throws Throwable
        {
          final AreaF a1 = AreaConversions.toAreaF(a0);
          Assert.assertEquals(a0.minimumX(), a1.minimumX(), 0.0);
          Assert.assertEquals(a0.minimumY(), a1.minimumY(), 0.0);
          Assert.assertEquals(a0.maximumX(), a1.maximumX(), 0.0);
          Assert.assertEquals(a0.maximumY(), a1.maximumY(), 0.0);
          final PAreaF<Object> a2 = AreaConversions.toPAreaF(a1);
          Assert.assertEquals(a0, a2);
        }
      });
  }
}
