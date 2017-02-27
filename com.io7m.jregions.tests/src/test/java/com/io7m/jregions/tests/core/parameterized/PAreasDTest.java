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

import com.io7m.jregions.core.parameterized.PAreaD;
import com.io7m.jregions.core.parameterized.PAreaXSplitD;
import com.io7m.jregions.core.parameterized.PAreaYSplitD;
import com.io7m.jregions.core.parameterized.PAreasD;
import com.io7m.jregions.generators.PAreaDGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.support.DoubleGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.Random;

public final class PAreasDTest
{
  private static final double DELTA = 0.000001;

  private static double absoluteDifference(
    final double m,
    final double n)
  {
    return Math.abs(m - n);
  }

  @Test
  public void testCreateAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> c =
            PAreasD.create(
              outer.minimumX(),
              outer.minimumY(),
              outer.width(),
              outer.height());

          Assert.assertEquals(outer.minimumX(), c.minimumX(), DELTA);
          Assert.assertEquals(outer.minimumY(), c.minimumY(), DELTA);
          Assert.assertEquals(outer.maximumX(), c.maximumX(), DELTA);
          Assert.assertEquals(outer.maximumY(), c.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testContainsSelfAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasD.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final PAreaD<Object> area = PAreaD.of(0.0, 10.0, 0.0, 10.0);

    final PAreaD<Object> left = PAreaD.of(-10.0, -1.0, 0.0, 10.0);
    final PAreaD<Object> right = PAreaD.of(11.0, 20.0, 0.0, 10.0);
    final PAreaD<Object> top = PAreaD.of(0.0, 10.0, -10.0, -1.0);
    final PAreaD<Object> bottom = PAreaD.of(0.0, 10.0, 11.0, 20.0);

    Assert.assertFalse(PAreasD.contains(area, left));
    Assert.assertFalse(PAreasD.contains(area, right));
    Assert.assertFalse(PAreasD.contains(area, top));
    Assert.assertFalse(PAreasD.contains(area, bottom));

    Assert.assertFalse(PAreasD.contains(left, area));
    Assert.assertFalse(PAreasD.contains(right, area));
    Assert.assertFalse(PAreasD.contains(top, area));
    Assert.assertFalse(PAreasD.contains(bottom, area));
  }

  @Test
  public void testMoveRelative()
    throws Exception
  {
    final Generator<Double> gen =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);

    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x = gen.next().doubleValue();
          final double y = gen.next().doubleValue();

          final PAreaD<Object> moved = PAreasD.moveRelative(outer, x, y);

          Assert.assertEquals(outer.width(), moved.width(), DELTA);
          Assert.assertEquals(outer.height(), moved.height(), DELTA);
          Assert.assertEquals(
            Math.abs(x),
            absoluteDifference(outer.minimumX(), moved.minimumX()), DELTA);
          Assert.assertEquals(
            Math.abs(y),
            absoluteDifference(outer.minimumY(), moved.minimumY()), DELTA);
        }
      });
  }

  @Test
  public void testMoveAbsolute()
    throws Exception
  {
    final Generator<Double> gen =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);

    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x = gen.next().doubleValue();
          final double y = gen.next().doubleValue();
          final PAreaD<Object> moved = PAreasD.moveAbsolute(outer, x, y);

          Assert.assertEquals(outer.width(), moved.width(), DELTA);
          Assert.assertEquals(outer.height(), moved.height(), DELTA);
          Assert.assertEquals(x, moved.minimumX(), DELTA);
          Assert.assertEquals(y, moved.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> moved = PAreasD.moveToOrigin(outer);

          Assert.assertEquals(outer.width(),  moved.width(), DELTA);
          Assert.assertEquals(outer.height(),  moved.height(), DELTA);
          Assert.assertEquals(0.0,  moved.minimumX(), DELTA);
          Assert.assertEquals(0.0,  moved.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testEqualsHashcode()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaDGenerator.create(),
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> area)
          throws Throwable
        {
          final PAreaD<Object> other =
            PAreaD.of(
              area.minimumX(),
              area.maximumX(),
              area.minimumY(),
              area.maximumY());
          Assert.assertEquals(area, area);
          Assert.assertEquals(area, other);
          Assert.assertEquals(other, area);
          Assert.assertEquals((long) area.hashCode(), (long) other.hashCode());

          Assert.assertNotEquals(area, null);
          Assert.assertNotEquals(area, Integer.valueOf(23));
        }
      });
  }

  @Test
  public void testShowAll()
    throws Exception
  {
    final PAreaDGenerator<Object> area_gen = PAreaDGenerator.create();
    QuickCheck.forAll(
      area_gen,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> any)
          throws Throwable
        {
          Assert.assertEquals(PAreasD.show(any), PAreasD.show(any));

          final PAreaD<Object> next = area_gen.next();
          if (!Objects.equals(next, any)) {
            Assert.assertNotEquals(PAreasD.show(any), PAreasD.show(next));
          } else {
            Assert.assertEquals(PAreasD.show(any), PAreasD.show(next));
          }
        }
      });
  }

  @Test
  public void testZero()
    throws Exception
  {
    final PAreaD<Object> area = PAreaD.of(0.0, 0.0, 0.0, 0.0);
    Assert.assertEquals(0.0,  area.width(), DELTA);
    Assert.assertEquals(0.0,  area.height(), DELTA);
  }

  @Test
  public void testHollowOut()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 99.0, 0.0, 99.0);
    final PAreaD<Object> inner = PAreasD.hollowOut(
      outer,
      10.0,
      20.0,
      30.0,
      40.0);

    Assert.assertEquals(10.0,  inner.minimumX(), DELTA);
    Assert.assertEquals(99.0 - 20.0,  inner.maximumX(), DELTA);
    Assert.assertEquals(30.0,  inner.minimumY(), DELTA);
    Assert.assertEquals(99.0 - 40.0,  inner.maximumY(), DELTA);
  }

  @Test
  public void testHollowOutSelfAll()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaDGenerator.create(),
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> any)
          throws Throwable
        {
          Assert.assertEquals(any, PAreasD.hollowOut(any, 0.0, 0.0,  0.0, 0.0));
        }
      });
  }

  @Test
  public void testHollowOutEvenlyAll()
    throws Exception
  {
    final Generator<Double> gen =
      PrimitiveGenerators.doubles(0.0, 100.0);

    QuickCheck.forAll(
      PAreaDGenerator.create(),
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> any)
          throws Throwable
        {
          final double offset = gen.next().doubleValue();
          final PAreaD<Object> hollow0 =
            PAreasD.hollowOut(any, offset,
                              offset, offset, offset);
          final PAreaD<Object> hollow1 =
            PAreasD.hollowOutEvenly(any, offset);
          Assert.assertEquals(hollow0, hollow1);
        }
      });
  }

  @Test
  public void testHollowOutTooLargeMinX()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreasD.hollowOut(
      outer,
      120.0,
      20.0,
      30.0,
      40.0);

    Assert.assertEquals(100.0,  inner.minimumX(), DELTA);
    Assert.assertEquals(100.0,  inner.maximumX(), DELTA);
    Assert.assertEquals(30.0,  inner.minimumY(), DELTA);
    Assert.assertEquals(100.0 - 40.0,  inner.maximumY(), DELTA);
  }

  @Test
  public void testHollowOutTooLargeMaxX()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreasD.hollowOut(
      outer,
      10.0,
      120.0,
      30.0,
      40.0);

    Assert.assertEquals(10.0,  inner.minimumX(), DELTA);
    Assert.assertEquals(10.0,  inner.maximumX(), DELTA);
    Assert.assertEquals(30.0,  inner.minimumY(), DELTA);
    Assert.assertEquals(100.0 - 40.0,  inner.maximumY(), DELTA);
  }

  @Test
  public void testHollowOutTooLargeMinY()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreasD.hollowOut(
      outer,
      10.0,
      20.0,
      120.0,
      40.0);

    Assert.assertEquals(10.0,  inner.minimumX(), DELTA);
    Assert.assertEquals(100.0 - 20.0,  inner.maximumX(), DELTA);
    Assert.assertEquals(100.0,  inner.minimumY(), DELTA);
    Assert.assertEquals(100.0,  inner.maximumY(), DELTA);
  }

  @Test
  public void testHollowOutTooLargeMaxY()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreasD.hollowOut(
      outer,
      10.0,
      20.0,
      30.0,
      120.0);

    Assert.assertEquals(10.0,  inner.minimumX(), DELTA);
    Assert.assertEquals(100.0 - 20.0,  inner.maximumX(), DELTA);
    Assert.assertEquals(30.0,  inner.minimumY(), DELTA);
    Assert.assertEquals(30.0,  inner.maximumY(), DELTA);
  }

  @Test
  public void testAlignHorizontallyCenterSpecific()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreaD.of(0.0, 10.0, 0.0, 10.0);

    Assert.assertEquals(100.0,  outer.width(), DELTA);
    Assert.assertEquals(100.0,  outer.height(), DELTA);

    Assert.assertEquals(10.0,  inner.width(), DELTA);
    Assert.assertEquals(10.0,  inner.height(), DELTA);

    final PAreaD<Object> aligned =
      PAreasD.alignHorizontallyCenter(outer, inner);

    Assert.assertEquals(10.0,  aligned.width(), DELTA);
    Assert.assertEquals(10.0,  aligned.height(), DELTA);
    Assert.assertEquals(45.0,  aligned.minimumX(), DELTA);
    Assert.assertEquals(55.0,  aligned.maximumX(), DELTA);
  }

  @Test
  public void testAlignHorizontallyMinXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignHorizontallyMinX(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY(), DELTA);
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY(), DELTA);
          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.minimumX(),
            outer.minimumX(), DELTA);
        }
      });
  }

  @Test
  public void testAlignHorizontallyMinXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignHorizontallyMinXOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY(), DELTA);
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY(), DELTA);
          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.minimumX(),
            outer.minimumX() + offset, DELTA);
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignHorizontallyMaxX(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY(), DELTA);
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY(), DELTA);
          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.maximumX(),
            outer.maximumX(), DELTA);
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignHorizontallyMaxXOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY(), DELTA);
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY(), DELTA);
          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.maximumX(),
            outer.maximumX() - offset, DELTA);
        }
      });
  }

  @Test
  public void testAlignHorizontallyCenterAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignHorizontallyCenter(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY(), DELTA);
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY(), DELTA);
          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          final double diff_left =
            PAreasDTest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final double diff_right =
            PAreasDTest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          Assert.assertTrue(
            PAreasDTest.absoluteDifference(diff_right, diff_left) <= 1.0);
        }
      });
  }

  @Test
  public void testAlignVerticallyCenterSpecific()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(0.0, 100.0, 0.0, 100.0);
    final PAreaD<Object> inner = PAreaD.of(0.0, 10.0, 0.0, 10.0);

    Assert.assertEquals(100.0,  outer.width(), DELTA);
    Assert.assertEquals(100.0,  outer.height(), DELTA);

    Assert.assertEquals(10.0,  inner.width(), DELTA);
    Assert.assertEquals(10.0,  inner.height(), DELTA);

    final PAreaD<Object> aligned =
      PAreasD.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10.0,  aligned.width(), DELTA);
    Assert.assertEquals(10.0,  aligned.height(), DELTA);
    Assert.assertEquals(0.0,  aligned.minimumX(), DELTA);
    Assert.assertEquals(10.0,  aligned.maximumX(), DELTA);
    Assert.assertEquals(45.0,  aligned.minimumY(), DELTA);
    Assert.assertEquals(55.0,  aligned.maximumY(), DELTA);
  }

  @Test
  public void testAlignVerticallyCenterSpecific2()
    throws Exception
  {
    final PAreaD<Object> outer = PAreaD.of(6.0, 634.0, 0.0, 16.0);
    final PAreaD<Object> inner = PAreaD.of(3.0, 13.0, 6.0, 16.0);

    Assert.assertEquals(628.0,  outer.width(), DELTA);
    Assert.assertEquals(16.0,  outer.height(), DELTA);
    Assert.assertEquals(10.0,  inner.width(), DELTA);
    Assert.assertEquals(10.0,  inner.height(), DELTA);

    final PAreaD<Object> aligned =
      PAreasD.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10.0,  aligned.width(), DELTA);
    Assert.assertEquals(10.0,  aligned.height(), DELTA);

    Assert.assertEquals(3.0,  aligned.minimumX(), DELTA);
    Assert.assertEquals(13.0,  aligned.maximumX(), DELTA);
    Assert.assertEquals(3.0,  aligned.minimumY(), DELTA);
    Assert.assertEquals(13.0,  aligned.maximumY(), DELTA);
  }

  @Test
  public void testAlignVerticallyCenterAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignVerticallyCenter(
            outer,
            inner);

          Assert.assertEquals(inner.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(inner.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          final double diff_top =
            PAreasDTest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final double diff_bottom =
            PAreasDTest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());
          final double diff_diff =
            PAreasDTest.absoluteDifference(diff_bottom, diff_top);
          Assert.assertTrue(diff_diff <= 1.0);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignVerticallyMinY(
            outer,
            inner);

          Assert.assertEquals(inner.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(inner.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(aligned.minimumY(),  outer.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignVerticallyMinYOffset(outer, inner, offset);

          Assert.assertEquals(inner.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(inner.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.minimumY(),
            outer.minimumY() + offset,
            0.0);
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignVerticallyMaxY(outer, inner);

          Assert.assertEquals(inner.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(inner.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(aligned.maximumY(),  outer.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset = gen.next().doubleValue();
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignVerticallyMaxYOffset(outer, inner, offset);

          Assert.assertEquals(inner.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(inner.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            aligned.maximumY(),
            outer.maximumY() - offset,
            0.0);
        }
      });
  }

  @Test
  public void testAlignCenterAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignCenter(
            outer,
            inner);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          final double diff_left =
            PAreasDTest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final double diff_right =
            PAreasDTest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          final double diff_top =
            PAreasDTest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final double diff_bottom =
            PAreasDTest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());

          Assert.assertTrue(
            PAreasDTest.absoluteDifference(diff_top, diff_bottom) <= 1.0);
          Assert.assertTrue(
            PAreasDTest.absoluteDifference(diff_right, diff_left) <= 1.0);
        }
      });
  }

  @Test
  public void testAlignMinYMinXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignMinYMinX(
            outer,
            inner);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(outer.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(outer.minimumY(),  aligned.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignMinYMinXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset_top = gen.next().doubleValue();
          final double offset_left = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignMinYMinXOffset(outer, inner, offset_left,
                                        offset_top);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            outer.minimumX() + offset_left,
            aligned.minimumX(),
            0.0);
          Assert.assertEquals(
            outer.minimumY() + offset_top,
            aligned.minimumY(),
            0.0);
        }
      });
  }

  @Test
  public void testAlignMaxYMinXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignMaxYMinX(
            outer,
            inner);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(outer.minimumX(),  aligned.minimumX(), DELTA);
          Assert.assertEquals(outer.maximumY(),  aligned.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignMaxYMinXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset_bottom = gen.next().doubleValue();
          final double offset_left = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignMaxYMinXOffset(
              outer,
              inner,
              offset_left,
              offset_bottom);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            outer.minimumX() + offset_left,
            aligned.minimumX(),
            0.0);
          Assert.assertEquals(
            outer.maximumY() - offset_bottom,
            aligned.maximumY(),
            0.0);
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignMaxYMaxX(
            outer,
            inner);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(outer.maximumX(),  aligned.maximumX(), DELTA);
          Assert.assertEquals(outer.maximumY(),  aligned.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset_bottom = gen.next().doubleValue();
          final double offset_right = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignMaxYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_bottom);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            outer.maximumX() - offset_right,
            aligned.maximumX(),
            0.0);
          Assert.assertEquals(
            outer.maximumY() - offset_bottom,
            aligned.maximumY(),
            0.0);
        }
      });
  }

  @Test
  public void testAlignMinYMaxXAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned = PAreasD.alignMinYMaxX(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width(), DELTA);
          Assert.assertEquals(
            inner.height(),
            aligned.height(), DELTA);

          Assert.assertEquals(
            outer.maximumX(),
            aligned.maximumX(), DELTA);
          Assert.assertEquals(
            outer.minimumY(),
            aligned.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testAlignMinYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double offset_top = gen.next().doubleValue();
          final double offset_right = gen.next().doubleValue();

          final PAreaD<Object> inner = generator.next();
          final PAreaD<Object> aligned =
            PAreasD.alignMinYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_top);

          Assert.assertEquals(inner.width(),  aligned.width(), DELTA);
          Assert.assertEquals(inner.height(),  aligned.height(), DELTA);

          Assert.assertEquals(
            outer.maximumX() - offset_right,
            aligned.maximumX(), DELTA);
          Assert.assertEquals(
            outer.minimumY() + offset_top,
            aligned.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMinXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double expected_width = gen.next().doubleValue();
          final double expected_height = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.setSizeFromMinYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.maximumX(),
            resized.maximumX(), DELTA);
          Assert.assertEquals(
            outer.maximumY(),
            resized.maximumY(), DELTA);
          Assert.assertEquals(expected_width,  resized.width(), DELTA);
          Assert.assertEquals(expected_height,  resized.height(), DELTA);
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double expected_width = gen.next().doubleValue();
          final double expected_height = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.setSizeFromMinYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.minimumX(),
            resized.minimumX(), DELTA);
          Assert.assertEquals(
            outer.maximumY(),
            resized.maximumY(), DELTA);
          Assert.assertEquals(expected_width,  resized.width(), DELTA);
          Assert.assertEquals(expected_height,  resized.height(), DELTA);
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double expected_width = gen.next().doubleValue();
          final double expected_height = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.setSizeFromMaxYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.maximumX(),
            resized.maximumX(), DELTA);
          Assert.assertEquals(
            outer.minimumY(),
            resized.minimumY(), DELTA);
          Assert.assertEquals(expected_width,  resized.width(), DELTA);
          Assert.assertEquals(expected_height,  resized.height(), DELTA);
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double expected_width = gen.next().doubleValue();
          final double expected_height = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.setSizeFromMaxYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.minimumX(),
            resized.minimumX(), DELTA);
          Assert.assertEquals(
            outer.minimumY(),
            resized.minimumY(), DELTA);
          Assert.assertEquals(expected_width,  resized.width(), DELTA);
          Assert.assertEquals(expected_height,  resized.height(), DELTA);
        }
      });
  }

  @Test
  public void testSetSizeFromCenterAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double expected_width = gen.next().doubleValue();
          final double expected_height = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.setSizeFromCenter(outer, expected_width,
                                      expected_height);

          Assert.assertEquals(expected_width,  resized.width(), DELTA);
          Assert.assertEquals(expected_height,  resized.height(), DELTA);

          final double diff_left =
            PAreasDTest.absoluteDifference(
              outer.minimumX(),
              resized.minimumX());
          final double diff_right =
            PAreasDTest.absoluteDifference(
              outer.maximumX(),
              resized.maximumX());
          final double diff_top =
            PAreasDTest.absoluteDifference(
              outer.minimumY(),
              resized.minimumY());
          final double diff_bottom =
            PAreasDTest.absoluteDifference(
              outer.maximumY(),
              resized.maximumY());

          Assert.assertTrue(
            PAreasDTest.absoluteDifference(diff_top, diff_bottom) <= 1.0);
          Assert.assertTrue(
            PAreasDTest.absoluteDifference(diff_right, diff_left) <= 1.0);
        }
      });
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final PAreaD<Object> area = PAreaD.of(0.0, 10.0, 0.0, 10.0);

    final PAreaD<Object> left = PAreaD.of(-10.0, -1.0, 0.0, 10.0);
    final PAreaD<Object> right = PAreaD.of(11.0, 20.0, 0.0, 10.0);
    final PAreaD<Object> top = PAreaD.of(0.0, 10.0, -10.0, -1.0);
    final PAreaD<Object> bottom = PAreaD.of(0.0, 10.0, 11.0, 20.0);

    Assert.assertFalse(PAreasD.overlaps(area, left));
    Assert.assertFalse(PAreasD.overlaps(area, right));
    Assert.assertFalse(PAreasD.overlaps(area, top));
    Assert.assertFalse(PAreasD.overlaps(area, bottom));

    Assert.assertFalse(PAreasD.overlaps(left, area));
    Assert.assertFalse(PAreasD.overlaps(right, area));
    Assert.assertFalse(PAreasD.overlaps(top, area));
    Assert.assertFalse(PAreasD.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final PAreaD<Object> area = PAreaD.of(0.0, 10.0, 0.0, 10.0);

    final PAreaD<Object> left = PAreaD.of(-10.0, 1.0, 0.0, 10.0);
    final PAreaD<Object> right = PAreaD.of(9.0, 20.0, 0.0, 10.0);
    final PAreaD<Object> top = PAreaD.of(0.0, 10.0, -10.0, 1.0);
    final PAreaD<Object> bottom = PAreaD.of(0.0, 10.0, 9.0, 20.0);

    Assert.assertTrue(PAreasD.overlaps(area, left));
    Assert.assertTrue(PAreasD.overlaps(area, right));
    Assert.assertTrue(PAreasD.overlaps(area, top));
    Assert.assertTrue(PAreasD.overlaps(area, bottom));

    Assert.assertTrue(PAreasD.overlaps(left, area));
    Assert.assertTrue(PAreasD.overlaps(right, area));
    Assert.assertTrue(PAreasD.overlaps(top, area));
    Assert.assertTrue(PAreasD.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          if (outer.height() > 0.0 && outer.width() > 0.0) {
            Assert.assertTrue(PAreasD.overlaps(outer, outer));
          } else {
            Assert.assertFalse(PAreasD.overlaps(outer, outer));
          }
        }
      });
  }

  @Test
  public void testContainsTrueAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 100.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double left_offset = gen.next().doubleValue();
          final double right_offset = gen.next().doubleValue();
          final double top_offset = gen.next().doubleValue();
          final double bottom_offset = gen.next().doubleValue();
          final PAreaD<Object> inner =
            PAreasD.hollowOut(
              outer, left_offset, right_offset, top_offset,
              bottom_offset);

          Assert.assertTrue(PAreasD.contains(outer, inner));
          Assert.assertFalse(PAreasD.contains(inner, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasD.couldFitInside(outer, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(0.0, 100.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> inner = PAreasD.hollowOutEvenly(
            outer,
            gen.next().doubleValue());
          Assert.assertTrue(PAreasD.couldFitInside(inner, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double w = Math.max(1.0, outer.width());
          final double h = Math.max(1.0, outer.height());
          final double px = random.nextDouble() * w;
          final double py = random.nextDouble() * h;
          final double ppx = outer.minimumX() + px;
          final double ppy = outer.minimumY() + py;

          System.out.printf(
            "%f %f\n",
            Double.valueOf(ppx),
            Double.valueOf(ppy));

          if (outer.width() > 0.0 && outer.height() > 0.0) {
            Assert.assertTrue(PAreasD.containsPoint(outer, ppx, ppy));
          } else {
            Assert.assertFalse(PAreasD.containsPoint(outer, ppx, ppy));
          }
        }
      });
  }

  @Test
  public void testContainingAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> a)
          throws Throwable
        {
          final PAreaD<Object> b = generator.next();
          final PAreaD<Object> c = PAreasD.containing(a, b);
          Assert.assertTrue(c.width() >= a.width());
          Assert.assertTrue(c.height() >= a.height());
          Assert.assertTrue(c.width() >= b.width());
          Assert.assertTrue(c.height() >= b.height());
          Assert.assertTrue(PAreasD.contains(c, a));
          Assert.assertTrue(PAreasD.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroWidth()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double w = Math.max(1.0, outer.width());
          final double h = Math.max(1.0, outer.height());
          final double px = random.nextDouble() * w;
          final double py = random.nextDouble() * h;
          final double ppx = outer.minimumX() + px;
          final double ppy = outer.minimumY() + py;

          final PAreaD<Object> scaled = PAreasD.create(
            outer.minimumX(),
            outer.minimumY(),
            0.0,
            outer.height());

          System.out.printf(
            "%f %f\n",
            Double.valueOf(ppx),
            Double.valueOf(ppy));

          Assert.assertFalse(PAreasD.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testContainsZeroHeight()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double w = Math.max(1.0, outer.width());
          final double h = Math.max(1.0, outer.height());
          final double px = random.nextDouble() * w;
          final double py = random.nextDouble() * h;
          final double ppx = outer.minimumX() + px;
          final double ppy = outer.minimumY() + py;

          final PAreaD<Object> scaled = PAreasD.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.width(),
            0.0);

          System.out.printf(
            "%f %f\n",
            Double.valueOf(ppx),
            Double.valueOf(ppy));

          Assert.assertFalse(PAreasD.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testFitBetweenHorizontalAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> left = generator.next();
          final PAreaD<Object> right = generator.next();

          final double leftmost = Math.min(
            Math.min(left.minimumX(), left.maximumX()),
            Math.min(right.minimumX(), right.maximumX()));
          final double rightmost = Math.max(
            Math.max(left.minimumX(), left.maximumX()),
            Math.max(right.minimumX(), right.maximumX()));

          final PAreaD<Object> fitted =
            PAreasD.fitBetweenHorizontal(outer, left, right);

          Assert.assertTrue(fitted.minimumX() >= leftmost);
          Assert.assertTrue(fitted.maximumX() <= rightmost);
          Assert.assertEquals(outer.minimumY(),  fitted.minimumY(), DELTA);
          Assert.assertEquals(outer.maximumY(),  fitted.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testFitBetweenVerticalAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Object> top = generator.next();
          final PAreaD<Object> bottom = generator.next();

          final double topmost = Math.min(
            Math.min(top.minimumY(), top.maximumY()),
            Math.min(bottom.minimumY(), bottom.maximumY()));
          final double bottommost = Math.max(
            Math.max(top.minimumY(), top.maximumY()),
            Math.max(bottom.minimumY(), bottom.maximumY()));

          final PAreaD<Object> fitted =
            PAreasD.fitBetweenVertical(outer, top, bottom);

          Assert.assertTrue(fitted.minimumY() >= topmost);
          Assert.assertTrue(fitted.maximumY() <= bottommost);
          Assert.assertEquals(outer.minimumX(),  fitted.minimumX(), DELTA);
          Assert.assertEquals(outer.maximumX(),  fitted.maximumX(), DELTA);
        }
      });
  }

  @Test
  public void testScaleFromMinYMinXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x_diff = gen.next().doubleValue();
          final double y_diff = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.scaleFromMinYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0.0, outer.width() + x_diff),
            resized.width(), DELTA);
          Assert.assertEquals(
            Math.max(0.0, outer.height() + y_diff),
            resized.height(), DELTA);

          Assert.assertEquals(outer.maximumX(),  resized.maximumX(), DELTA);
          Assert.assertEquals(outer.maximumY(),  resized.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testScaleFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x_diff = gen.next().doubleValue();
          final double y_diff = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.scaleFromMinYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0.0, outer.width() + x_diff),
            resized.width(),
            DELTA);
          Assert.assertEquals(
            Math.max(0.0, outer.height() + y_diff),
            resized.height(),
            DELTA);

          Assert.assertEquals(outer.minimumX(),  resized.minimumX(), DELTA);
          Assert.assertEquals(outer.maximumY(),  resized.maximumY(), DELTA);
        }
      });
  }

  @Test
  public void testScaleFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Double> gen = PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x_diff = gen.next().doubleValue();
          final double y_diff = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.scaleFromMaxYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0.0, outer.width() + x_diff),
            resized.width(), DELTA);
          Assert.assertEquals(
            Math.max(0.0, outer.height() + y_diff),
            resized.height(), DELTA);

          Assert.assertEquals(outer.minimumX(),  resized.minimumX(), DELTA);
          Assert.assertEquals(outer.minimumY(),  resized.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testScaleFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Double> gen =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x_diff = gen.next().doubleValue();
          final double y_diff = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.scaleFromMaxYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0.0, outer.width() + x_diff),
            resized.width(), DELTA);
          Assert.assertEquals(
            Math.max(0.0, outer.height() + y_diff),
            resized.height(), DELTA);

          Assert.assertEquals(outer.maximumX(),  resized.maximumX(), DELTA);
          Assert.assertEquals(outer.minimumY(),  resized.minimumY(), DELTA);
        }
      });
  }

  @Test
  public void testScaleFromCenterAll()
    throws Exception
  {
    final Generator<Double> gen =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double x_diff = gen.next().doubleValue();
          final double y_diff = gen.next().doubleValue();

          final PAreaD<Object> resized =
            PAreasD.scaleFromCenter(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0.0, outer.width() + x_diff),
            resized.width(), DELTA);
          Assert.assertEquals(
            Math.max(0.0, outer.height() + y_diff),
            resized.height(), DELTA);
        }
      });
  }

  @Test
  public void testCastAll()
    throws Exception
  {
    final Generator<PAreaD<Object>> generator = PAreaDGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final PAreaD<Integer> other = PAreasD.cast(outer);
          Assert.assertSame(outer, other);
        }
      });
  }

  @Test
  public void testSplitAlongXAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaD<Object>> generator =
      new PAreaDGenerator<>(new DoubleGenerator(0.0, 100000.0));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double height = random.nextDouble() * (outer.height() + 1.0);
          final PAreaXSplitD<Object, PAreaD<Object>> pair =
            PAreasD.splitAlongParallelToX(outer, height);

          final PAreaD<Object> lower = pair.lower();
          final PAreaD<Object> upper = pair.upper();

          System.out.println("height: " + height);
          System.out.println("lower:  " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.height() <= outer.height());
          Assert.assertTrue(upper.height() <= outer.height());
          Assert.assertEquals(
            outer.height(),
            lower.height() + upper.height(), DELTA);
          Assert.assertEquals(outer.width(),  lower.width(), DELTA);
          Assert.assertEquals(outer.width(),  upper.width(), DELTA);

          if (outer.width() > 0.0 && outer.height() > 0.0) {
            final boolean lower_ok = lower.width() > 0.0 && lower.height() > 0.0;
            final boolean upper_ok = upper.width() > 0.0 && upper.height() > 0.0;
            if (lower_ok) {
              Assert.assertTrue(PAreasD.overlaps(outer, lower));
              Assert.assertTrue(PAreasD.contains(outer, lower));
              if (upper_ok) {
                Assert.assertFalse(PAreasD.overlaps(lower, upper));
              }
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasD.overlaps(outer, upper));
              Assert.assertTrue(PAreasD.contains(outer, upper));
              if (lower_ok) {
                Assert.assertFalse(PAreasD.overlaps(lower, upper));
              }
            }
          }
        }
      });
  }

  @Test
  public void testSplitAlongXSpecific()
    throws Exception
  {
    final PAreaD<Object> box = PAreaD.of(0.0, 10.0, 0.0, 10.0);
    final PAreaXSplitD<Object, PAreaD<Object>> pair =
      PAreasD.splitAlongParallelToX(box, 5.0);

    final PAreaD<Object> lower = pair.lower();
    final PAreaD<Object> upper = pair.upper();

    Assert.assertEquals(0.0,  lower.minimumX(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumX(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumX(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumX(), DELTA);

    Assert.assertEquals(5.0,  lower.minimumY(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumY(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumY(), DELTA);
    Assert.assertEquals(5.0,  upper.maximumY(), DELTA);

    Assert.assertEquals(5.0,  lower.height(), DELTA);
    Assert.assertEquals(5.0,  upper.height(), DELTA);
  }

  @Test
  public void testSplitAlongXTiny()
    throws Exception
  {
    final PAreaD<Object> box = PAreaD.of(0.0, 10.0, 0.0, 10.0);
    final PAreaXSplitD<Object, PAreaD<Object>> pair =
      PAreasD.splitAlongParallelToX(box, 20.0);

    final PAreaD<Object> lower = pair.lower();
    final PAreaD<Object> upper = pair.upper();

    Assert.assertEquals(0.0,  lower.minimumX(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumX(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumX(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumX(), DELTA);

    Assert.assertEquals(10.0,  lower.minimumY(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumY(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumY(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumY(), DELTA);

    Assert.assertEquals(0.0,  lower.height(), DELTA);
    Assert.assertEquals(10.0,  upper.height(), DELTA);
  }

  @Test
  public void testSplitAlongYSpecific()
    throws Exception
  {
    final PAreaD<Object> box = PAreaD.of(0.0, 10.0, 0.0, 10.0);
    final PAreaYSplitD<Object, PAreaD<Object>> pair =
      PAreasD.splitAlongParallelToY(box, 5.0);

    final PAreaD<Object> lower = pair.lower();
    final PAreaD<Object> upper = pair.upper();

    Assert.assertEquals(0.0,  lower.minimumY(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumY(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumY(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumY(), DELTA);

    Assert.assertEquals(0.0,  lower.minimumX(), DELTA);
    Assert.assertEquals(5.0,  lower.maximumX(), DELTA);
    Assert.assertEquals(5.0,  upper.minimumX(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumX(), DELTA);

    Assert.assertEquals(5.0,  lower.width(), DELTA);
    Assert.assertEquals(5.0,  upper.width(), DELTA);
  }

  @Test
  public void testSplitAlongYTiny()
    throws Exception
  {
    final PAreaD<Object> box = PAreaD.of(0.0, 10.0, 0.0, 10.0);
    final PAreaYSplitD<Object, PAreaD<Object>> pair =
      PAreasD.splitAlongParallelToY(box, 20.0);

    final PAreaD<Object> lower = pair.lower();
    final PAreaD<Object> upper = pair.upper();

    Assert.assertEquals(0.0,  lower.minimumY(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumY(), DELTA);
    Assert.assertEquals(0.0,  upper.minimumY(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumY(), DELTA);

    Assert.assertEquals(0.0,  lower.minimumX(), DELTA);
    Assert.assertEquals(10.0,  lower.maximumX(), DELTA);
    Assert.assertEquals(10.0,  upper.minimumX(), DELTA);
    Assert.assertEquals(10.0,  upper.maximumX(), DELTA);

    Assert.assertEquals(10.0,  lower.width(), DELTA);
    Assert.assertEquals(0.0,  upper.width(), DELTA);
  }

  @Test
  public void testSplitAlongYAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaD<Object>> generator =
      new PAreaDGenerator<>(new DoubleGenerator(0.0, 10000.0));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaD<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaD<Object> outer)
          throws Throwable
        {
          final double width = random.nextDouble() * (outer.width() + 1.0);
          final PAreaYSplitD<Object, PAreaD<Object>> pair =
            PAreasD.splitAlongParallelToY(outer, width);

          final PAreaD<Object> lower = pair.lower();
          final PAreaD<Object> upper = pair.upper();

          System.out.println("height: " + width);
          System.out.println("lower:   " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.width() <= outer.width());
          Assert.assertTrue(upper.width() <= outer.width());
          Assert.assertEquals(
            outer.width(),
            lower.width() + upper.width(),
            DELTA);
          Assert.assertEquals(outer.height(),  lower.height(), DELTA);
          Assert.assertEquals(outer.height(),  upper.height(), DELTA);

          if (outer.width() > 0.0 && outer.height() > 0.0) {
            final boolean lower_ok = lower.width() > 0.0 && lower.height() > 0.0;
            final boolean upper_ok = upper.width() > 0.0 && upper.height() > 0.0;
            if (lower_ok) {
              Assert.assertTrue(PAreasD.overlaps(outer, lower));
              Assert.assertTrue(PAreasD.contains(outer, lower));
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasD.overlaps(outer, upper));
              Assert.assertTrue(PAreasD.contains(outer, upper));
            }
          }
        }
      });
  }
}
