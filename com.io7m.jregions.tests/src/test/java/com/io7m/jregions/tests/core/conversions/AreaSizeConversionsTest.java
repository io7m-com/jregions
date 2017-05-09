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
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeL;
import com.io7m.jregions.generators.PAreaSizeBDGenerator;
import com.io7m.jregions.generators.PAreaSizeBIGenerator;
import com.io7m.jregions.generators.PAreaSizeDGenerator;
import com.io7m.jregions.generators.PAreaSizeFGenerator;
import com.io7m.jregions.generators.PAreaSizeIGenerator;
import com.io7m.jregions.generators.PAreaSizeLGenerator;
import org.junit.Assert;
import org.junit.Test;

public final class AreaSizeConversionsTest
{
  @Test
  public void testIdentityL0()
  {
    final PAreaSizeLGenerator<Object> generator = PAreaSizeLGenerator.create();
    final PAreaSizeL<Object> a0 = generator.next();
    final AreaSizeL a1 = AreaSizeConversions.toAreaSizeL(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX());
    Assert.assertEquals(a0.sizeY(), a1.sizeY());
    final PAreaSizeL<Object> a2 = AreaSizeConversions.toPAreaSizeL(a1);
    Assert.assertEquals(a0, a2);
  }

  @Test
  public void testIdentityI0()
  {
    final PAreaSizeIGenerator<Object> generator = PAreaSizeIGenerator.create();
    final PAreaSizeI<Object> a0 = generator.next();
    final AreaSizeI a1 = AreaSizeConversions.toAreaSizeI(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX());
    Assert.assertEquals(a0.sizeY(), a1.sizeY());
    final PAreaSizeI<Object> a2 = AreaSizeConversions.toPAreaSizeI(a1);
    Assert.assertEquals(a0, a2);
  }

  @Test
  public void testIdentityBD0()
  {
    final PAreaSizeBDGenerator<Object> generator = PAreaSizeBDGenerator.create();
    final PAreaSizeBD<Object> a0 = generator.next();
    final AreaSizeBD a1 = AreaSizeConversions.toAreaSizeBD(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX());
    Assert.assertEquals(a0.sizeY(), a1.sizeY());
    final PAreaSizeBD<Object> a2 = AreaSizeConversions.toPAreaSizeBD(a1);
    Assert.assertEquals(a0, a2);
  }

  @Test
  public void testIdentityBI0()
  {
    final PAreaSizeBIGenerator<Object> generator = PAreaSizeBIGenerator.create();
    final PAreaSizeBI<Object> a0 = generator.next();
    final AreaSizeBI a1 = AreaSizeConversions.toAreaSizeBI(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX());
    Assert.assertEquals(a0.sizeY(), a1.sizeY());
    final PAreaSizeBI<Object> a2 = AreaSizeConversions.toPAreaSizeBI(a1);
    Assert.assertEquals(a0, a2);
  }

  @Test
  public void testIdentityD0()
  {
    final PAreaSizeDGenerator<Object> generator = PAreaSizeDGenerator.create();
    final PAreaSizeD<Object> a0 = generator.next();
    final AreaSizeD a1 = AreaSizeConversions.toAreaSizeD(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX(), 0.0);
    Assert.assertEquals(a0.sizeY(), a1.sizeY(), 0.0);
    final PAreaSizeD<Object> a2 = AreaSizeConversions.toPAreaSizeD(a1);
    Assert.assertEquals(a0, a2);
  }

  @Test
  public void testIdentityF0()
  {
    final PAreaSizeFGenerator<Object> generator = PAreaSizeFGenerator.create();
    final PAreaSizeF<Object> a0 = generator.next();
    final AreaSizeF a1 = AreaSizeConversions.toAreaSizeF(a0);
    Assert.assertEquals(a0.sizeX(), a1.sizeX(), 0.0);
    Assert.assertEquals(a0.sizeY(), a1.sizeY(), 0.0);
    final PAreaSizeF<Object> a2 = AreaSizeConversions.toPAreaSizeF(a1);
    Assert.assertEquals(a0, a2);
  }
}
