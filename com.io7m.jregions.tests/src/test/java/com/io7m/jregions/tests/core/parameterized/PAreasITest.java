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

import com.io7m.jregions.core.parameterized.PAreaI;
import com.io7m.jregions.core.parameterized.PAreaXSplitI;
import com.io7m.jregions.core.parameterized.PAreaYSplitI;
import com.io7m.jregions.core.parameterized.PAreasI;
import com.io7m.jregions.generators.PAreaIGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.support.IntegerGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.Random;

public final class PAreasITest
{
  private static int absoluteDifference(
    final int m,
    final int n)
  {
    return Math.abs(Math.subtractExact(m, n));
  }

  @Test
  public void testCreateAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> c =
            PAreasI.create(
              outer.minimumX(),
              outer.minimumY(),
              outer.width(),
              outer.height());

          Assert.assertEquals(outer, c);
        }
      });
  }

  @Test
  public void testContainsSelfAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasI.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final PAreaI<Object> area = PAreaI.of(0, 10, 0, 10);

    final PAreaI<Object> left = PAreaI.of(-10, -1, 0, 10);
    final PAreaI<Object> right = PAreaI.of(11, 20, 0, 10);
    final PAreaI<Object> top = PAreaI.of(0, 10, -10, -1);
    final PAreaI<Object> bottom = PAreaI.of(0, 10, 11, 20);

    Assert.assertFalse(PAreasI.contains(area, left));
    Assert.assertFalse(PAreasI.contains(area, right));
    Assert.assertFalse(PAreasI.contains(area, top));
    Assert.assertFalse(PAreasI.contains(area, bottom));

    Assert.assertFalse(PAreasI.contains(left, area));
    Assert.assertFalse(PAreasI.contains(right, area));
    Assert.assertFalse(PAreasI.contains(top, area));
    Assert.assertFalse(PAreasI.contains(bottom, area));
  }

  @Test
  public void testMoveRelative()
    throws Exception
  {
    final Generator<Integer> gen =
      PrimitiveGenerators.integers(-1_000_000, 1_000_000);

    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x = gen.next().intValue();
          final int y = gen.next().intValue();

          final PAreaI<Object> moved = PAreasI.moveRelative(outer, x, y);

          Assert.assertEquals((long) outer.width(), (long) moved.width());
          Assert.assertEquals((long) outer.height(), (long) moved.height());
          Assert.assertEquals(
            (long) Math.abs(x),
            (long) absoluteDifference(outer.minimumX(), moved.minimumX()));
          Assert.assertEquals(
            (long) Math.abs(y),
            (long) absoluteDifference(outer.minimumY(), moved.minimumY()));
        }
      });
  }

  @Test
  public void testMoveAbsolute()
    throws Exception
  {
    final Generator<Integer> gen =
      PrimitiveGenerators.integers(-1_000_000, 1_000_000);

    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x = gen.next().intValue();
          final int y = gen.next().intValue();
          final PAreaI<Object> moved = PAreasI.moveAbsolute(outer, x, y);

          Assert.assertEquals((long) outer.width(), (long) moved.width());
          Assert.assertEquals((long) outer.height(), (long) moved.height());
          Assert.assertEquals((long) x, (long) moved.minimumX());
          Assert.assertEquals((long) y, (long) moved.minimumY());
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> moved = PAreasI.moveToOrigin(outer);

          Assert.assertEquals((long) outer.width(), (long) moved.width());
          Assert.assertEquals((long) outer.height(), (long) moved.height());
          Assert.assertEquals(0L, (long) moved.minimumX());
          Assert.assertEquals(0L, (long) moved.minimumY());
        }
      });
  }

  @Test
  public void testEqualsHashcode()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaIGenerator.create(),
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> area)
          throws Throwable
        {
          final PAreaI<Object> other =
            PAreaI.of(
              area.minimumX(),
              area.maximumX(),
              area.minimumY(),
              area.maximumY());
          Assert.assertEquals(area, area);
          Assert.assertEquals(area, other);
          Assert.assertEquals(other, area);
          Assert.assertEquals(
            (long) (int) area.hashCode(),
            (long) (int) other.hashCode());

          Assert.assertNotEquals(area, null);
          Assert.assertNotEquals(area, Integer.valueOf(23));
        }
      });
  }

  @Test
  public void testShowAll()
    throws Exception
  {
    final PAreaIGenerator<Object> area_gen = PAreaIGenerator.create();
    QuickCheck.forAll(
      area_gen,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> any)
          throws Throwable
        {
          Assert.assertEquals(PAreasI.show(any), PAreasI.show(any));

          final PAreaI<Object> next = area_gen.next();
          if (!Objects.equals(next, any)) {
            Assert.assertNotEquals(PAreasI.show(any), PAreasI.show(next));
          } else {
            Assert.assertEquals(PAreasI.show(any), PAreasI.show(next));
          }
        }
      });
  }

  @Test
  public void testZero()
    throws Exception
  {
    final PAreaI<Object> area = PAreaI.of(0, 0, 0, 0);
    Assert.assertEquals(0L, (long) area.width());
    Assert.assertEquals(0L, (long) area.height());
  }

  @Test
  public void testHollowOut()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 99, 0, 99);
    final PAreaI<Object> inner = PAreasI.hollowOut(
      outer,
      10,
      20,
      30,
      40);

    Assert.assertEquals(10L, (long) inner.minimumX());
    Assert.assertEquals((long) (99 - 20), (long) inner.maximumX());
    Assert.assertEquals(30L, (long) inner.minimumY());
    Assert.assertEquals((long) (99 - 40), (long) inner.maximumY());
  }

  @Test
  public void testHollowOutSelfAll()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaIGenerator.create(),
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> any)
          throws Throwable
        {
          Assert.assertEquals(any, PAreasI.hollowOut(any, 0, 0, 0, 0));
        }
      });
  }

  @Test
  public void testHollowOutEvenlyAll()
    throws Exception
  {
    final Generator<Integer> gen =
      PrimitiveGenerators.integers(0, 100);

    QuickCheck.forAll(
      PAreaIGenerator.create(),
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> any)
          throws Throwable
        {
          final int offset = gen.next().intValue();
          final PAreaI<Object> hollow0 =
            PAreasI.hollowOut(any, offset, offset, offset, offset);
          final PAreaI<Object> hollow1 =
            PAreasI.hollowOutEvenly(any, offset);
          Assert.assertEquals(hollow0, hollow1);
        }
      });
  }

  @Test
  public void testHollowOutTooLargeMinX()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreasI.hollowOut(
      outer,
      120,
      20,
      30,
      40);

    Assert.assertEquals(100L, (long) inner.minimumX());
    Assert.assertEquals(100L, (long) inner.maximumX());
    Assert.assertEquals(30L, (long) inner.minimumY());
    Assert.assertEquals((long) (100 - 40), (long) inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxX()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreasI.hollowOut(
      outer,
      10,
      120,
      30,
      40);

    Assert.assertEquals(10L, (long) inner.minimumX());
    Assert.assertEquals(10L, (long) inner.maximumX());
    Assert.assertEquals(30L, (long) inner.minimumY());
    Assert.assertEquals((long) (100 - 40), (long) inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMinY()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreasI.hollowOut(
      outer,
      10,
      20,
      120,
      40);

    Assert.assertEquals(10L, (long) inner.minimumX());
    Assert.assertEquals((long) (100 - 20), (long) inner.maximumX());
    Assert.assertEquals(100L, (long) inner.minimumY());
    Assert.assertEquals(100L, (long) inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxY()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreasI.hollowOut(
      outer,
      10,
      20,
      30,
      120);

    Assert.assertEquals(10L, (long) inner.minimumX());
    Assert.assertEquals((long) (100 - 20), (long) inner.maximumX());
    Assert.assertEquals(30L, (long) inner.minimumY());
    Assert.assertEquals(30L, (long) inner.maximumY());
  }

  @Test
  public void testAlignHorizontallyCenterSpecific()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreaI.of(0, 10, 0, 10);

    Assert.assertEquals(100L, (long) outer.width());
    Assert.assertEquals(100L, (long) outer.height());

    Assert.assertEquals(10L, (long) inner.width());
    Assert.assertEquals(10L, (long) inner.height());

    final PAreaI<Object> aligned = PAreasI.alignHorizontallyCenter(
      outer,
      inner);

    Assert.assertEquals(10L, (long) aligned.width());
    Assert.assertEquals(10L, (long) aligned.height());
    Assert.assertEquals(45L, (long) aligned.minimumX());
    Assert.assertEquals(55L, (long) aligned.maximumX());
  }

  @Test
  public void testAlignHorizontallyMinXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignHorizontallyMinX(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.minimumY(),
            (long) aligned.minimumY());
          Assert.assertEquals(
            (long) inner.maximumY(),
            (long) aligned.maximumY());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.minimumX(),
            (long) outer.minimumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMinXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignHorizontallyMinXOffset(outer, inner, offset);

          Assert.assertEquals(
            (long) inner.minimumY(),
            (long) aligned.minimumY());
          Assert.assertEquals(
            (long) inner.maximumY(),
            (long) aligned.maximumY());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.minimumX(),
            (long) (outer.minimumX() + offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignHorizontallyMaxX(
            outer,
            inner);

          Assert.assertEquals((long) inner.minimumY(), (long) aligned.minimumY());
          Assert.assertEquals((long) inner.maximumY(), (long) aligned.maximumY());
          Assert.assertEquals((long) inner.width(), (long) aligned.width());
          Assert.assertEquals((long) inner.height(), (long) aligned.height());

          Assert.assertEquals((long) aligned.maximumX(), (long) outer.maximumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignHorizontallyMaxXOffset(outer, inner, offset);

          Assert.assertEquals(
            (long) inner.minimumY(),
            (long) aligned.minimumY());
          Assert.assertEquals(
            (long) inner.maximumY(),
            (long) aligned.maximumY());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.maximumX(),
            (long) (outer.maximumX() - offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyCenterAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignHorizontallyCenter(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.minimumY(),
            (long) aligned.minimumY());
          Assert.assertEquals(
            (long) inner.maximumY(),
            (long) aligned.maximumY());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          final int diff_left =
            PAreasITest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final int diff_right =
            PAreasITest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          Assert.assertTrue(
            PAreasITest.absoluteDifference(diff_right, diff_left) <= 1);
        }
      });
  }

  @Test
  public void testAlignVerticallyCenterSpecific()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(0, 100, 0, 100);
    final PAreaI<Object> inner = PAreaI.of(0, 10, 0, 10);

    Assert.assertEquals(100L, (long) outer.width());
    Assert.assertEquals(100L, (long) outer.height());

    Assert.assertEquals(10L, (long) inner.width());
    Assert.assertEquals(10L, (long) inner.height());

    final PAreaI<Object> aligned =
      PAreasI.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10L, (long) aligned.width());
    Assert.assertEquals(10L, (long) aligned.height());
    Assert.assertEquals(0L, (long) aligned.minimumX());
    Assert.assertEquals(10L, (long) aligned.maximumX());
    Assert.assertEquals(45L, (long) aligned.minimumY());
    Assert.assertEquals(55L, (long) aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterSpecific2()
    throws Exception
  {
    final PAreaI<Object> outer = PAreaI.of(6, 634, 0, 16);
    final PAreaI<Object> inner = PAreaI.of(3, 13, 6, 16);

    Assert.assertEquals(628L, (long) outer.width());
    Assert.assertEquals(16L, (long) outer.height());
    Assert.assertEquals(10L, (long) inner.width());
    Assert.assertEquals(10L, (long) inner.height());

    final PAreaI<Object> aligned =
      PAreasI.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10L, (long) aligned.width());
    Assert.assertEquals(10L, (long) aligned.height());

    Assert.assertEquals(3L, (long) aligned.minimumX());
    Assert.assertEquals(13L, (long) aligned.maximumX());
    Assert.assertEquals(3L, (long) aligned.minimumY());
    Assert.assertEquals(13L, (long) aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignVerticallyCenter(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) inner.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          final int diff_top =
            PAreasITest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final int diff_bottom =
            PAreasITest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());
          final int diff_diff =
            PAreasITest.absoluteDifference(diff_bottom, diff_top);
          Assert.assertTrue(diff_diff <= 1);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignVerticallyMinY(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) inner.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.minimumY(),
            (long) outer.minimumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignVerticallyMinYOffset(outer, inner, offset);

          Assert.assertEquals(
            (long) inner.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) inner.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.minimumY(),
            (long) (outer.minimumY() + offset));
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignVerticallyMaxY(outer, inner);

          Assert.assertEquals(
            (long) inner.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) inner.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.maximumY(),
            (long) outer.maximumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset = gen.next().intValue();
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignVerticallyMaxYOffset(outer, inner, offset);

          Assert.assertEquals(
            (long) inner.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) inner.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) aligned.maximumY(),
            (long) (outer.maximumY() - offset));
        }
      });
  }

  @Test
  public void testAlignCenterAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignCenter(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          final int diff_left =
            PAreasITest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final int diff_right =
            PAreasITest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          final int diff_top =
            PAreasITest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final int diff_bottom =
            PAreasITest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());

          Assert.assertTrue(
            PAreasITest.absoluteDifference(diff_top, diff_bottom) <= 1);
          Assert.assertTrue(
            PAreasITest.absoluteDifference(diff_right, diff_left) <= 1);
        }
      });
  }

  @Test
  public void testAlignMinYMinXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignMinYMinX(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) outer.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) outer.minimumY(),
            (long) aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMinXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset_top = gen.next().intValue();
          final int offset_left = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignMinYMinXOffset(outer, inner, offset_left,
                                        offset_top);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) (outer.minimumX() + offset_left),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) (outer.minimumY() + offset_top),
            (long) aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignMaxYMinX(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) outer.minimumX(),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) outer.maximumY(),
            (long) aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset_bottom = gen.next().intValue();
          final int offset_left = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignMaxYMinXOffset(
              outer,
              inner,
              offset_left,
              offset_bottom);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) (outer.minimumX() + offset_left),
            (long) aligned.minimumX());
          Assert.assertEquals(
            (long) (outer.maximumY() - offset_bottom),
            (long) aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignMaxYMaxX(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) outer.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) outer.maximumY(),
            (long) aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset_bottom = gen.next().intValue();
          final int offset_right = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignMaxYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_bottom);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) (outer.maximumX() - offset_right),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) (outer.maximumY() - offset_bottom),
            (long) aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned = PAreasI.alignMinYMaxX(
            outer,
            inner);

          Assert.assertEquals(
            (long) inner.width(),
            (long) aligned.width());
          Assert.assertEquals(
            (long) inner.height(),
            (long) aligned.height());

          Assert.assertEquals(
            (long) outer.maximumX(),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) outer.minimumY(),
            (long) aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int offset_top = gen.next().intValue();
          final int offset_right = gen.next().intValue();

          final PAreaI<Object> inner = generator.next();
          final PAreaI<Object> aligned =
            PAreasI.alignMinYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_top);

          Assert.assertEquals((long) inner.width(), (long) aligned.width());
          Assert.assertEquals((long) inner.height(), (long) aligned.height());

          Assert.assertEquals(
            (long) (outer.maximumX() - offset_right),
            (long) aligned.maximumX());
          Assert.assertEquals(
            (long) (outer.minimumY() + offset_top),
            (long) aligned.minimumY());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMinXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int expected_width = gen.next().intValue();
          final int expected_height = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.setSizeFromMinYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            (long) outer.maximumX(),
            (long) resized.maximumX());
          Assert.assertEquals(
            (long) outer.maximumY(),
            (long) resized.maximumY());
          Assert.assertEquals((long) expected_width, (long) resized.width());
          Assert.assertEquals((long) expected_height, (long) resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int expected_width = gen.next().intValue();
          final int expected_height = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.setSizeFromMinYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            (long) outer.minimumX(),
            (long) resized.minimumX());
          Assert.assertEquals(
            (long) outer.maximumY(),
            (long) resized.maximumY());
          Assert.assertEquals((long) expected_width, (long) resized.width());
          Assert.assertEquals((long) expected_height, (long) resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int expected_width = gen.next().intValue();
          final int expected_height = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.setSizeFromMaxYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            (long) outer.maximumX(),
            (long) resized.maximumX());
          Assert.assertEquals(
            (long) outer.minimumY(),
            (long) resized.minimumY());
          Assert.assertEquals((long) expected_width, (long) resized.width());
          Assert.assertEquals((long) expected_height, (long) resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int expected_width = gen.next().intValue();
          final int expected_height = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.setSizeFromMaxYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            (long) outer.minimumX(),
            (long) resized.minimumX());
          Assert.assertEquals(
            (long) outer.minimumY(),
            (long) resized.minimumY());
          Assert.assertEquals((long) expected_width, (long) resized.width());
          Assert.assertEquals((long) expected_height, (long) resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromCenterAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int expected_width = gen.next().intValue();
          final int expected_height = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.setSizeFromCenter(outer, expected_width,
                                      expected_height);

          Assert.assertEquals((long) expected_width, (long) resized.width());
          Assert.assertEquals((long) expected_height, (long) resized.height());

          final int diff_left =
            PAreasITest.absoluteDifference(
              outer.minimumX(),
              resized.minimumX());
          final int diff_right =
            PAreasITest.absoluteDifference(
              outer.maximumX(),
              resized.maximumX());
          final int diff_top =
            PAreasITest.absoluteDifference(
              outer.minimumY(),
              resized.minimumY());
          final int diff_bottom =
            PAreasITest.absoluteDifference(
              outer.maximumY(),
              resized.maximumY());

          Assert.assertTrue(
            PAreasITest.absoluteDifference(diff_top, diff_bottom) <= 1);
          Assert.assertTrue(
            PAreasITest.absoluteDifference(diff_right, diff_left) <= 1);
        }
      });
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final PAreaI<Object> area = PAreaI.of(0, 10, 0, 10);

    final PAreaI<Object> left = PAreaI.of(-10, -1, 0, 10);
    final PAreaI<Object> right = PAreaI.of(11, 20, 0, 10);
    final PAreaI<Object> top = PAreaI.of(0, 10, -10, -1);
    final PAreaI<Object> bottom = PAreaI.of(0, 10, 11, 20);

    Assert.assertFalse(PAreasI.overlaps(area, left));
    Assert.assertFalse(PAreasI.overlaps(area, right));
    Assert.assertFalse(PAreasI.overlaps(area, top));
    Assert.assertFalse(PAreasI.overlaps(area, bottom));

    Assert.assertFalse(PAreasI.overlaps(left, area));
    Assert.assertFalse(PAreasI.overlaps(right, area));
    Assert.assertFalse(PAreasI.overlaps(top, area));
    Assert.assertFalse(PAreasI.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final PAreaI<Object> area = PAreaI.of(0, 10, 0, 10);

    final PAreaI<Object> left = PAreaI.of(-10, 1, 0, 10);
    final PAreaI<Object> right = PAreaI.of(9, 20, 0, 10);
    final PAreaI<Object> top = PAreaI.of(0, 10, -10, 1);
    final PAreaI<Object> bottom = PAreaI.of(0, 10, 9, 20);

    Assert.assertTrue(PAreasI.overlaps(area, left));
    Assert.assertTrue(PAreasI.overlaps(area, right));
    Assert.assertTrue(PAreasI.overlaps(area, top));
    Assert.assertTrue(PAreasI.overlaps(area, bottom));

    Assert.assertTrue(PAreasI.overlaps(left, area));
    Assert.assertTrue(PAreasI.overlaps(right, area));
    Assert.assertTrue(PAreasI.overlaps(top, area));
    Assert.assertTrue(PAreasI.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          if (outer.height() > 0 && outer.width() > 0) {
            Assert.assertTrue(PAreasI.overlaps(outer, outer));
          } else {
            Assert.assertFalse(PAreasI.overlaps(outer, outer));
          }
        }
      });
  }

  @Test
  public void testContainsTrueAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 100);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int left_offset = gen.next().intValue();
          final int right_offset = gen.next().intValue();
          final int top_offset = gen.next().intValue();
          final int bottom_offset = gen.next().intValue();
          final PAreaI<Object> inner =
            PAreasI.hollowOut(
              outer, left_offset, right_offset, top_offset,
              bottom_offset);

          Assert.assertTrue(PAreasI.contains(outer, inner));
          Assert.assertFalse(PAreasI.contains(inner, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasI.couldFitInside(outer, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(0, 100);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> inner = PAreasI.hollowOutEvenly(
            outer,
            gen.next().intValue());
          Assert.assertTrue(PAreasI.couldFitInside(inner, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int w = Math.max(1, outer.width());
          final int h = Math.max(1, outer.height());
          final int px =  random.nextInt(w);
          final int py =  random.nextInt(h);
          final int ppx = outer.minimumX() + px;
          final int ppy = outer.minimumY() + py;

          System.out.printf(
            "%d %d\n",
            Integer.valueOf(ppx),
            Integer.valueOf(ppy));

          if (outer.width() > 0 && outer.height() > 0) {
            Assert.assertTrue(PAreasI.containsPoint(outer, ppx, ppy));
          } else {
            Assert.assertFalse(PAreasI.containsPoint(outer, ppx, ppy));
          }
        }
      });
  }

  @Test
  public void testContainingAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> a)
          throws Throwable
        {
          final PAreaI<Object> b = generator.next();
          final PAreaI<Object> c = PAreasI.containing(a, b);
          Assert.assertTrue(c.width() >= a.width());
          Assert.assertTrue(c.height() >= a.height());
          Assert.assertTrue(c.width() >= b.width());
          Assert.assertTrue(c.height() >= b.height());
          Assert.assertTrue(PAreasI.contains(c, a));
          Assert.assertTrue(PAreasI.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroWidth()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int w = Math.max(1, outer.width());
          final int h = Math.max(1, outer.height());
          final int px =  random.nextInt(w);
          final int py =  random.nextInt(h);
          final int ppx = outer.minimumX() + px;
          final int ppy = outer.minimumY() + py;

          final PAreaI<Object> scaled = PAreasI.create(
            outer.minimumX(),
            outer.minimumY(),
            0,
            outer.height());

          System.out.printf(
            "%d %d\n",
            Integer.valueOf(ppx),
            Integer.valueOf(ppy));

          Assert.assertFalse(PAreasI.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testContainsZeroHeight()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int w = Math.max(1, outer.width());
          final int h = Math.max(1, outer.height());
          final int px =  random.nextInt(w);
          final int py =  random.nextInt(h);
          final int ppx = outer.minimumX() + px;
          final int ppy = outer.minimumY() + py;

          final PAreaI<Object> scaled = PAreasI.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.width(),
            0);

          System.out.printf(
            "%d %d\n",
            Integer.valueOf(ppx),
            Integer.valueOf(ppy));

          Assert.assertFalse(PAreasI.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testFitBetweenHorizontalAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> left = generator.next();
          final PAreaI<Object> right = generator.next();

          final int leftmost = Math.min(
            Math.min(left.minimumX(), left.maximumX()),
            Math.min(right.minimumX(), right.maximumX()));
          final int rightmost = Math.max(
            Math.max(left.minimumX(), left.maximumX()),
            Math.max(right.minimumX(), right.maximumX()));

          final PAreaI<Object> fitted =
            PAreasI.fitBetweenHorizontal(outer, left, right);

          Assert.assertTrue(fitted.minimumX() >= leftmost);
          Assert.assertTrue(fitted.maximumX() <= rightmost);
          Assert.assertEquals((long) outer.minimumY(), (long) fitted.minimumY());
          Assert.assertEquals((long) outer.maximumY(), (long) fitted.maximumY());
        }
      });
  }

  @Test
  public void testFitBetweenVerticalAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Object> top = generator.next();
          final PAreaI<Object> bottom = generator.next();

          final int topmost = Math.min(
            Math.min(top.minimumY(), top.maximumY()),
            Math.min(bottom.minimumY(), bottom.maximumY()));
          final int bottommost = Math.max(
            Math.max(top.minimumY(), top.maximumY()),
            Math.max(bottom.minimumY(), bottom.maximumY()));

          final PAreaI<Object> fitted =
            PAreasI.fitBetweenVertical(outer, top, bottom);

          Assert.assertTrue(fitted.minimumY() >= topmost);
          Assert.assertTrue(fitted.maximumY() <= bottommost);
          Assert.assertEquals((long) outer.minimumX(), (long) fitted.minimumX());
          Assert.assertEquals((long) outer.maximumX(), (long) fitted.maximumX());
        }
      });
  }

  @Test
  public void testScaleFromMinYMinXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x_diff = gen.next().intValue();
          final int y_diff = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.scaleFromMinYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            (long) Math.max(0, outer.width() + x_diff),
            (long) resized.width());
          Assert.assertEquals(
            (long) Math.max(0, outer.height() + y_diff),
            (long) resized.height());

          Assert.assertEquals((long) outer.maximumX(), (long) resized.maximumX());
          Assert.assertEquals((long) outer.maximumY(), (long) resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x_diff = gen.next().intValue();
          final int y_diff = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.scaleFromMinYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            (long) Math.max(0, outer.width() + x_diff),
            (long) resized.width());
          Assert.assertEquals(
            (long) Math.max(0, outer.height() + y_diff),
            (long) resized.height());

          Assert.assertEquals((long) outer.minimumX(), (long) resized.minimumX());
          Assert.assertEquals((long) outer.maximumY(), (long) resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Integer> gen = PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x_diff = gen.next().intValue();
          final int y_diff = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.scaleFromMaxYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            (long) Math.max(0, outer.width() + x_diff),
            (long) resized.width());
          Assert.assertEquals(
            (long) Math.max(0, outer.height() + y_diff),
            (long) resized.height());

          Assert.assertEquals((long) outer.minimumX(), (long) resized.minimumX());
          Assert.assertEquals((long) outer.minimumY(), (long) resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Integer> gen =
      PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x_diff = gen.next().intValue();
          final int y_diff = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.scaleFromMaxYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            (long) Math.max(0, outer.width() + x_diff),
            (long) resized.width());
          Assert.assertEquals(
            (long) Math.max(0, outer.height() + y_diff),
            (long) resized.height());

          Assert.assertEquals((long) outer.maximumX(), (long) resized.maximumX());
          Assert.assertEquals((long) outer.minimumY(), (long) resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromCenterAll()
    throws Exception
  {
    final Generator<Integer> gen =
      PrimitiveGenerators.integers(-400, 400);
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int x_diff = gen.next().intValue();
          final int y_diff = gen.next().intValue();

          final PAreaI<Object> resized =
            PAreasI.scaleFromCenter(outer, x_diff, y_diff);

          Assert.assertEquals(
            (long) Math.max(0, outer.width() + x_diff),
            (long) resized.width());
          Assert.assertEquals(
            (long) Math.max(0, outer.height() + y_diff),
            (long) resized.height());
        }
      });
  }

  @Test
  public void testCastAll()
    throws Exception
  {
    final Generator<PAreaI<Object>> generator = PAreaIGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final PAreaI<Integer> other = PAreasI.cast(outer);
          Assert.assertSame(outer, other);
        }
      });
  }

  @Test
  public void testSplitAlongXAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaI<Object>> generator =
      new PAreaIGenerator<>(new IntegerGenerator(0, 100000));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int height = random.nextInt(outer.height() + 1);
          final PAreaXSplitI<Object, PAreaI<Object>> pair =
            PAreasI.splitAlongParallelToX(outer, (int) height);

          final PAreaI<Object> lower = pair.lower();
          final PAreaI<Object> upper = pair.upper();

          System.out.println("height: " + height);
          System.out.println("lower:  " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.height() <= outer.height());
          Assert.assertTrue(upper.height() <= outer.height());
          Assert.assertEquals(
            (long) outer.height(),
            (long) (lower.height() + upper.height()));
          Assert.assertEquals((long) outer.width(), (long) lower.width());
          Assert.assertEquals((long) outer.width(), (long) upper.width());

          if (outer.width() > 0 && outer.height() > 0) {
            final boolean lower_ok = lower.width() > 0 && lower.height() > 0;
            final boolean upper_ok = upper.width() > 0 && upper.height() > 0;
            if (lower_ok) {
              Assert.assertTrue(PAreasI.overlaps(outer, lower));
              Assert.assertTrue(PAreasI.contains(outer, lower));
              if (upper_ok) {
                Assert.assertFalse(PAreasI.overlaps(lower, upper));
              }
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasI.overlaps(outer, upper));
              Assert.assertTrue(PAreasI.contains(outer, upper));
              if (lower_ok) {
                Assert.assertFalse(PAreasI.overlaps(lower, upper));
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
    final PAreaI<Object> box = PAreaI.of(0, 10, 0, 10);
    final PAreaXSplitI<Object, PAreaI<Object>> pair =
      PAreasI.splitAlongParallelToX(box, 5);

    final PAreaI<Object> lower = pair.lower();
    final PAreaI<Object> upper = pair.upper();

    Assert.assertEquals(0L, (long) lower.minimumX());
    Assert.assertEquals(10L, (long) lower.maximumX());
    Assert.assertEquals(0L, (long) upper.minimumX());
    Assert.assertEquals(10L, (long) upper.maximumX());

    Assert.assertEquals(5L, (long) lower.minimumY());
    Assert.assertEquals(10L, (long) lower.maximumY());
    Assert.assertEquals(0L, (long) upper.minimumY());
    Assert.assertEquals(5L, (long) upper.maximumY());

    Assert.assertEquals(5L, (long) lower.height());
    Assert.assertEquals(5L, (long) upper.height());
  }

  @Test
  public void testSplitAlongXTiny()
    throws Exception
  {
    final PAreaI<Object> box = PAreaI.of(0, 10, 0, 10);
    final PAreaXSplitI<Object, PAreaI<Object>> pair =
      PAreasI.splitAlongParallelToX(box, 20);

    final PAreaI<Object> lower = pair.lower();
    final PAreaI<Object> upper = pair.upper();

    Assert.assertEquals(0L, (long) lower.minimumX());
    Assert.assertEquals(10L, (long) lower.maximumX());
    Assert.assertEquals(0L, (long) upper.minimumX());
    Assert.assertEquals(10L, (long) upper.maximumX());

    Assert.assertEquals(10L, (long) lower.minimumY());
    Assert.assertEquals(10L, (long) lower.maximumY());
    Assert.assertEquals(0L, (long) upper.minimumY());
    Assert.assertEquals(10L, (long) upper.maximumY());

    Assert.assertEquals(0L, (long) lower.height());
    Assert.assertEquals(10L, (long) upper.height());
  }

  @Test
  public void testSplitAlongYSpecific()
    throws Exception
  {
    final PAreaI<Object> box = PAreaI.of(0, 10, 0, 10);
    final PAreaYSplitI<Object, PAreaI<Object>> pair =
      PAreasI.splitAlongParallelToY(box, 5);

    final PAreaI<Object> lower = pair.lower();
    final PAreaI<Object> upper = pair.upper();

    Assert.assertEquals(0L, (long) lower.minimumY());
    Assert.assertEquals(10L, (long) lower.maximumY());
    Assert.assertEquals(0L, (long) upper.minimumY());
    Assert.assertEquals(10L, (long) upper.maximumY());

    Assert.assertEquals(0L, (long) lower.minimumX());
    Assert.assertEquals(5L, (long) lower.maximumX());
    Assert.assertEquals(5L, (long) upper.minimumX());
    Assert.assertEquals(10L, (long) upper.maximumX());

    Assert.assertEquals(5L, (long) lower.width());
    Assert.assertEquals(5L, (long) upper.width());
  }

  @Test
  public void testSplitAlongYTiny()
    throws Exception
  {
    final PAreaI<Object> box = PAreaI.of(0, 10, 0, 10);
    final PAreaYSplitI<Object, PAreaI<Object>> pair =
      PAreasI.splitAlongParallelToY(box, 20);

    final PAreaI<Object> lower = pair.lower();
    final PAreaI<Object> upper = pair.upper();

    Assert.assertEquals(0L, (long) lower.minimumY());
    Assert.assertEquals(10L, (long) lower.maximumY());
    Assert.assertEquals(0L, (long) upper.minimumY());
    Assert.assertEquals(10L, (long) upper.maximumY());

    Assert.assertEquals(0L, (long) lower.minimumX());
    Assert.assertEquals(10L, (long) lower.maximumX());
    Assert.assertEquals(10L, (long) upper.minimumX());
    Assert.assertEquals(10L, (long) upper.maximumX());

    Assert.assertEquals(10L, (long) lower.width());
    Assert.assertEquals(0L, (long) upper.width());
  }

  @Test
  public void testSplitAlongYAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaI<Object>> generator =
      new PAreaIGenerator<>(new IntegerGenerator(0, 10000));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaI<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaI<Object> outer)
          throws Throwable
        {
          final int width = random.nextInt(outer.width() + 1);
          final PAreaYSplitI<Object, PAreaI<Object>> pair =
            PAreasI.splitAlongParallelToY(outer, (int) width);

          final PAreaI<Object> lower = pair.lower();
          final PAreaI<Object> upper = pair.upper();

          System.out.println("height: " + width);
          System.out.println("lower:   " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.width() <= outer.width());
          Assert.assertTrue(upper.width() <= outer.width());
          Assert.assertEquals(
            (long) outer.width(),
            (long) (lower.width() + upper.width()));
          Assert.assertEquals((long) outer.height(), (long) lower.height());
          Assert.assertEquals((long) outer.height(), (long) upper.height());

          if (outer.width() > 0 && outer.height() > 0) {
            final boolean lower_ok = lower.width() > 0 && lower.height() > 0;
            final boolean upper_ok = upper.width() > 0 && upper.height() > 0;
            if (lower_ok) {
              Assert.assertTrue(PAreasI.overlaps(outer, lower));
              Assert.assertTrue(PAreasI.contains(outer, lower));
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasI.overlaps(outer, upper));
              Assert.assertTrue(PAreasI.contains(outer, upper));
            }
          }
        }
      });
  }
}
