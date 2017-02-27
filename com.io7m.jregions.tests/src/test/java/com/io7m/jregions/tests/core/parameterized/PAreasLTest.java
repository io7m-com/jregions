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

import com.io7m.jregions.core.parameterized.PAreaL;
import com.io7m.jregions.core.parameterized.PAreaXSplitL;
import com.io7m.jregions.core.parameterized.PAreaYSplitL;
import com.io7m.jregions.core.parameterized.PAreasL;
import com.io7m.jregions.generators.PAreaLGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.support.LongGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.Random;

public final class PAreasLTest
{
  private static long absoluteDifference(
    final long m,
    final long n)
  {
    return Math.abs(Math.subtractExact(m, n));
  }

  @Test
  public void testCreateAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> c =
            PAreasL.create(
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
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasL.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final PAreaL<Object> area = PAreaL.of(0L, 10L, 0L, 10L);

    final PAreaL<Object> left = PAreaL.of(-10L, -1L, 0L, 10L);
    final PAreaL<Object> right = PAreaL.of(11L, 20L, 0L, 10L);
    final PAreaL<Object> top = PAreaL.of(0L, 10L, -10L, -1L);
    final PAreaL<Object> bottom = PAreaL.of(0L, 10L, 11L, 20L);

    Assert.assertFalse(PAreasL.contains(area, left));
    Assert.assertFalse(PAreasL.contains(area, right));
    Assert.assertFalse(PAreasL.contains(area, top));
    Assert.assertFalse(PAreasL.contains(area, bottom));

    Assert.assertFalse(PAreasL.contains(left, area));
    Assert.assertFalse(PAreasL.contains(right, area));
    Assert.assertFalse(PAreasL.contains(top, area));
    Assert.assertFalse(PAreasL.contains(bottom, area));
  }

  @Test
  public void testMoveRelative()
    throws Exception
  {
    final Generator<Long> gen =
      PrimitiveGenerators.longs(-1_000_000L, 1_000_000L);

    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x = gen.next().longValue();
          final long y = gen.next().longValue();

          final PAreaL<Object> moved = PAreasL.moveRelative(outer, x, y);

          Assert.assertEquals(outer.width(), moved.width());
          Assert.assertEquals(outer.height(), moved.height());
          Assert.assertEquals(
            Math.abs(x),
            absoluteDifference(outer.minimumX(), moved.minimumX()));
          Assert.assertEquals(
            Math.abs(y),
            absoluteDifference(outer.minimumY(), moved.minimumY()));
        }
      });
  }

  @Test
  public void testMoveAbsolute()
    throws Exception
  {
    final Generator<Long> gen =
      PrimitiveGenerators.longs(-1_000_000L, 1_000_000L);

    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x = gen.next().longValue();
          final long y = gen.next().longValue();
          final PAreaL<Object> moved = PAreasL.moveAbsolute(outer, x, y);

          Assert.assertEquals(outer.width(), moved.width());
          Assert.assertEquals(outer.height(), moved.height());
          Assert.assertEquals(x, moved.minimumX());
          Assert.assertEquals(y, moved.minimumY());
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> moved = PAreasL.moveToOrigin(outer);

          Assert.assertEquals(outer.width(), moved.width());
          Assert.assertEquals(outer.height(), moved.height());
          Assert.assertEquals(0L, moved.minimumX());
          Assert.assertEquals(0L, moved.minimumY());
        }
      });
  }

  @Test
  public void testEqualsHashcode()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaLGenerator.create(),
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> area)
          throws Throwable
        {
          final PAreaL<Object> other =
            PAreaL.of(
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
    final PAreaLGenerator<Object> area_gen = PAreaLGenerator.create();
    QuickCheck.forAll(
      area_gen,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> any)
          throws Throwable
        {
          Assert.assertEquals(PAreasL.show(any), PAreasL.show(any));

          final PAreaL<Object> next = area_gen.next();
          if (!Objects.equals(next, any)) {
            Assert.assertNotEquals(PAreasL.show(any), PAreasL.show(next));
          } else {
            Assert.assertEquals(PAreasL.show(any), PAreasL.show(next));
          }
        }
      });
  }

  @Test
  public void testZero()
    throws Exception
  {
    final PAreaL<Object> area = PAreaL.of(0L, 0L, 0L, 0L);
    Assert.assertEquals(0L, area.width());
    Assert.assertEquals(0L, area.height());
  }

  @Test
  public void testHollowOut()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 99L, 0L, 99L);
    final PAreaL<Object> inner = PAreasL.hollowOut(
      outer,
      10L,
      20L,
      30L,
      40L);

    Assert.assertEquals(10L, inner.minimumX());
    Assert.assertEquals(99L - 20L, inner.maximumX());
    Assert.assertEquals(30L, inner.minimumY());
    Assert.assertEquals(99L - 40L, inner.maximumY());
  }

  @Test
  public void testHollowOutSelfAll()
    throws Exception
  {
    QuickCheck.forAll(
      PAreaLGenerator.create(),
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> any)
          throws Throwable
        {
          Assert.assertEquals(any, PAreasL.hollowOut(any, 0L, 0L, 0L, 0L));
        }
      });
  }

  @Test
  public void testHollowOutEvenlyAll()
    throws Exception
  {
    final Generator<Long> gen =
      PrimitiveGenerators.longs(0L, 100L);

    QuickCheck.forAll(
      PAreaLGenerator.create(),
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> any)
          throws Throwable
        {
          final long offset = gen.next().longValue();
          final PAreaL<Object> hollow0 =
            PAreasL.hollowOut(any, offset,
                              offset, offset, offset);
          final PAreaL<Object> hollow1 =
            PAreasL.hollowOutEvenly(any, offset);
          Assert.assertEquals(hollow0, hollow1);
        }
      });
  }

  @Test
  public void testHollowOutTooLargeMinX()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreasL.hollowOut(
      outer,
      120L,
      20L,
      30L,
      40L);

    Assert.assertEquals(100L, inner.minimumX());
    Assert.assertEquals(100L, inner.maximumX());
    Assert.assertEquals(30L, inner.minimumY());
    Assert.assertEquals(100L - 40L, inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxX()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreasL.hollowOut(
      outer,
      10L,
      120L,
      30L,
      40L);

    Assert.assertEquals(10L, inner.minimumX());
    Assert.assertEquals(10L, inner.maximumX());
    Assert.assertEquals(30L, inner.minimumY());
    Assert.assertEquals(100L - 40L, inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMinY()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreasL.hollowOut(
      outer,
      10L,
      20L,
      120L,
      40L);

    Assert.assertEquals(10L, inner.minimumX());
    Assert.assertEquals(100L - 20L, inner.maximumX());
    Assert.assertEquals(100L, inner.minimumY());
    Assert.assertEquals(100L, inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxY()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreasL.hollowOut(
      outer,
      10L,
      20L,
      30L,
      120L);

    Assert.assertEquals(10L, inner.minimumX());
    Assert.assertEquals(100L - 20L, inner.maximumX());
    Assert.assertEquals(30L, inner.minimumY());
    Assert.assertEquals(30L, inner.maximumY());
  }

  @Test
  public void testAlignHorizontallyCenterSpecific()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreaL.of(0L, 10L, 0L, 10L);

    Assert.assertEquals(100L, outer.width());
    Assert.assertEquals(100L, outer.height());

    Assert.assertEquals(10L, inner.width());
    Assert.assertEquals(10L, inner.height());

    final PAreaL<Object> aligned = PAreasL.alignHorizontallyCenter(
      outer,
      inner);

    Assert.assertEquals(10L, aligned.width());
    Assert.assertEquals(10L, aligned.height());
    Assert.assertEquals(45L, aligned.minimumX());
    Assert.assertEquals(55L, aligned.maximumX());
  }

  @Test
  public void testAlignHorizontallyMinXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignHorizontallyMinX(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY());
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.minimumX(),
            outer.minimumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMinXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignHorizontallyMinXOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY());
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.minimumX(),
            outer.minimumX() + offset);
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignHorizontallyMaxX(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY());
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.maximumX(),
            outer.maximumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignHorizontallyMaxXOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY());
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.maximumX(),
            outer.maximumX() - offset);
        }
      });
  }

  @Test
  public void testAlignHorizontallyCenterAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignHorizontallyCenter(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumY(),
            aligned.minimumY());
          Assert.assertEquals(
            inner.maximumY(),
            aligned.maximumY());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          final long diff_left =
            PAreasLTest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final long diff_right =
            PAreasLTest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          Assert.assertTrue(
            PAreasLTest.absoluteDifference(diff_right, diff_left) <= 1L);
        }
      });
  }

  @Test
  public void testAlignVerticallyCenterSpecific()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(0L, 100L, 0L, 100L);
    final PAreaL<Object> inner = PAreaL.of(0L, 10L, 0L, 10L);

    Assert.assertEquals(100L, outer.width());
    Assert.assertEquals(100L, outer.height());

    Assert.assertEquals(10L, inner.width());
    Assert.assertEquals(10L, inner.height());

    final PAreaL<Object> aligned =
      PAreasL.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10L, aligned.width());
    Assert.assertEquals(10L, aligned.height());
    Assert.assertEquals(0L, aligned.minimumX());
    Assert.assertEquals(10L, aligned.maximumX());
    Assert.assertEquals(45L, aligned.minimumY());
    Assert.assertEquals(55L, aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterSpecific2()
    throws Exception
  {
    final PAreaL<Object> outer = PAreaL.of(6L, 634L, 0L, 16L);
    final PAreaL<Object> inner = PAreaL.of(3L, 13L, 6L, 16L);

    Assert.assertEquals(628L, outer.width());
    Assert.assertEquals(16L, outer.height());
    Assert.assertEquals(10L, inner.width());
    Assert.assertEquals(10L, inner.height());

    final PAreaL<Object> aligned =
      PAreasL.alignVerticallyCenter(outer, inner);

    Assert.assertEquals(10L, aligned.width());
    Assert.assertEquals(10L, aligned.height());

    Assert.assertEquals(3L, aligned.minimumX());
    Assert.assertEquals(13L, aligned.maximumX());
    Assert.assertEquals(3L, aligned.minimumY());
    Assert.assertEquals(13L, aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignVerticallyCenter(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            inner.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          final long diff_top =
            PAreasLTest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final long diff_bottom =
            PAreasLTest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());
          final long diff_diff =
            PAreasLTest.absoluteDifference(diff_bottom, diff_top);
          Assert.assertTrue(diff_diff <= 1L);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignVerticallyMinY(
            outer,
            inner);

          Assert.assertEquals(
            inner.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            inner.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.minimumY(),
            outer.minimumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignVerticallyMinYOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            inner.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.minimumY(),
            outer.minimumY() + offset);
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignVerticallyMaxY(outer, inner);

          Assert.assertEquals(
            inner.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            inner.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.maximumY(),
            outer.maximumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset = gen.next().longValue();
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignVerticallyMaxYOffset(outer, inner, offset);

          Assert.assertEquals(
            inner.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            inner.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            aligned.maximumY(),
            outer.maximumY() - offset);
        }
      });
  }

  @Test
  public void testAlignCenterAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignCenter(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          final long diff_left =
            PAreasLTest.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final long diff_right =
            PAreasLTest.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          final long diff_top =
            PAreasLTest.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final long diff_bottom =
            PAreasLTest.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());

          Assert.assertTrue(
            PAreasLTest.absoluteDifference(diff_top, diff_bottom) <= 1L);
          Assert.assertTrue(
            PAreasLTest.absoluteDifference(diff_right, diff_left) <= 1L);
        }
      });
  }

  @Test
  public void testAlignMinYMinXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignMinYMinX(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            outer.minimumY(),
            aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMinXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset_top = gen.next().longValue();
          final long offset_left = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignMinYMinXOffset(outer, inner, offset_left,
                                        offset_top);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.minimumX() + offset_left,
            aligned.minimumX());
          Assert.assertEquals(
            outer.minimumY() + offset_top,
            aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignMaxYMinX(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.minimumX(),
            aligned.minimumX());
          Assert.assertEquals(
            outer.maximumY(),
            aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset_bottom = gen.next().longValue();
          final long offset_left = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignMaxYMinXOffset(
              outer,
              inner,
              offset_left,
              offset_bottom);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.minimumX() + offset_left,
            aligned.minimumX());
          Assert.assertEquals(
            outer.maximumY() - offset_bottom,
            aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignMaxYMaxX(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            outer.maximumY(),
            aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset_bottom = gen.next().longValue();
          final long offset_right = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignMaxYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_bottom);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.maximumX() - offset_right,
            aligned.maximumX());
          Assert.assertEquals(
            outer.maximumY() - offset_bottom,
            aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned = PAreasL.alignMinYMaxX(
            outer,
            inner);

          Assert.assertEquals(
            inner.width(),
            aligned.width());
          Assert.assertEquals(
            inner.height(),
            aligned.height());

          Assert.assertEquals(
            outer.maximumX(),
            aligned.maximumX());
          Assert.assertEquals(
            outer.minimumY(),
            aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXOffsetAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long offset_top = gen.next().longValue();
          final long offset_right = gen.next().longValue();

          final PAreaL<Object> inner = generator.next();
          final PAreaL<Object> aligned =
            PAreasL.alignMinYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_top);

          Assert.assertEquals(inner.width(), aligned.width());
          Assert.assertEquals(inner.height(), aligned.height());

          Assert.assertEquals(
            outer.maximumX() - offset_right,
            aligned.maximumX());
          Assert.assertEquals(
            outer.minimumY() + offset_top,
            aligned.minimumY());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMinXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long expected_width = gen.next().longValue();
          final long expected_height = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.setSizeFromMinYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.maximumX(),
            resized.maximumX());
          Assert.assertEquals(
            outer.maximumY(),
            resized.maximumY());
          Assert.assertEquals(expected_width, resized.width());
          Assert.assertEquals(expected_height, resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long expected_width = gen.next().longValue();
          final long expected_height = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.setSizeFromMinYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.minimumX(),
            resized.minimumX());
          Assert.assertEquals(
            outer.maximumY(),
            resized.maximumY());
          Assert.assertEquals(expected_width, resized.width());
          Assert.assertEquals(expected_height, resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long expected_width = gen.next().longValue();
          final long expected_height = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.setSizeFromMaxYMinX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.maximumX(),
            resized.maximumX());
          Assert.assertEquals(
            outer.minimumY(),
            resized.minimumY());
          Assert.assertEquals(expected_width, resized.width());
          Assert.assertEquals(expected_height, resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long expected_width = gen.next().longValue();
          final long expected_height = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.setSizeFromMaxYMaxX(
              outer,
              expected_width,
              expected_height);

          Assert.assertEquals(
            outer.minimumX(),
            resized.minimumX());
          Assert.assertEquals(
            outer.minimumY(),
            resized.minimumY());
          Assert.assertEquals(expected_width, resized.width());
          Assert.assertEquals(expected_height, resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromCenterAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long expected_width = gen.next().longValue();
          final long expected_height = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.setSizeFromCenter(outer, expected_width,
                                      expected_height);

          Assert.assertEquals(expected_width, resized.width());
          Assert.assertEquals(expected_height, resized.height());

          final long diff_left =
            PAreasLTest.absoluteDifference(
              outer.minimumX(),
              resized.minimumX());
          final long diff_right =
            PAreasLTest.absoluteDifference(
              outer.maximumX(),
              resized.maximumX());
          final long diff_top =
            PAreasLTest.absoluteDifference(
              outer.minimumY(),
              resized.minimumY());
          final long diff_bottom =
            PAreasLTest.absoluteDifference(
              outer.maximumY(),
              resized.maximumY());

          Assert.assertTrue(
            PAreasLTest.absoluteDifference(diff_top, diff_bottom) <= 1L);
          Assert.assertTrue(
            PAreasLTest.absoluteDifference(diff_right, diff_left) <= 1L);
        }
      });
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final PAreaL<Object> area = PAreaL.of(0L, 10L, 0L, 10L);

    final PAreaL<Object> left = PAreaL.of(-10L, -1L, 0L, 10L);
    final PAreaL<Object> right = PAreaL.of(11L, 20L, 0L, 10L);
    final PAreaL<Object> top = PAreaL.of(0L, 10L, -10L, -1L);
    final PAreaL<Object> bottom = PAreaL.of(0L, 10L, 11L, 20L);

    Assert.assertFalse(PAreasL.overlaps(area, left));
    Assert.assertFalse(PAreasL.overlaps(area, right));
    Assert.assertFalse(PAreasL.overlaps(area, top));
    Assert.assertFalse(PAreasL.overlaps(area, bottom));

    Assert.assertFalse(PAreasL.overlaps(left, area));
    Assert.assertFalse(PAreasL.overlaps(right, area));
    Assert.assertFalse(PAreasL.overlaps(top, area));
    Assert.assertFalse(PAreasL.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final PAreaL<Object> area = PAreaL.of(0L, 10L, 0L, 10L);

    final PAreaL<Object> left = PAreaL.of(-10L, 1L, 0L, 10L);
    final PAreaL<Object> right = PAreaL.of(9L, 20L, 0L, 10L);
    final PAreaL<Object> top = PAreaL.of(0L, 10L, -10L, 1L);
    final PAreaL<Object> bottom = PAreaL.of(0L, 10L, 9L, 20L);

    Assert.assertTrue(PAreasL.overlaps(area, left));
    Assert.assertTrue(PAreasL.overlaps(area, right));
    Assert.assertTrue(PAreasL.overlaps(area, top));
    Assert.assertTrue(PAreasL.overlaps(area, bottom));

    Assert.assertTrue(PAreasL.overlaps(left, area));
    Assert.assertTrue(PAreasL.overlaps(right, area));
    Assert.assertTrue(PAreasL.overlaps(top, area));
    Assert.assertTrue(PAreasL.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          if (outer.height() > 0L && outer.width() > 0L) {
            Assert.assertTrue(PAreasL.overlaps(outer, outer));
          } else {
            Assert.assertFalse(PAreasL.overlaps(outer, outer));
          }
        }
      });
  }

  @Test
  public void testContainsTrueAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 100L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long left_offset = gen.next().longValue();
          final long right_offset = gen.next().longValue();
          final long top_offset = gen.next().longValue();
          final long bottom_offset = gen.next().longValue();
          final PAreaL<Object> inner =
            PAreasL.hollowOut(
              outer, left_offset, right_offset, top_offset,
              bottom_offset);

          Assert.assertTrue(PAreasL.contains(outer, inner));
          Assert.assertFalse(PAreasL.contains(inner, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          Assert.assertTrue(PAreasL.couldFitInside(outer, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(0L, 100L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> inner = PAreasL.hollowOutEvenly(
            outer,
            gen.next().longValue());
          Assert.assertTrue(PAreasL.couldFitInside(inner, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long w = Math.max(1L, outer.width());
          final long h = Math.max(1L, outer.height());
          final long px = (long) random.nextInt((int) w);
          final long py = (long) random.nextInt((int) h);
          final long ppx = outer.minimumX() + px;
          final long ppy = outer.minimumY() + py;

          System.out.printf(
            "%d %d\n",
            Long.valueOf(ppx),
            Long.valueOf(ppy));

          if (outer.width() > 0L && outer.height() > 0L) {
            Assert.assertTrue(PAreasL.containsPoint(outer, ppx, ppy));
          } else {
            Assert.assertFalse(PAreasL.containsPoint(outer, ppx, ppy));
          }
        }
      });
  }

  @Test
  public void testContainingAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> a)
          throws Throwable
        {
          final PAreaL<Object> b = generator.next();
          final PAreaL<Object> c = PAreasL.containing(a, b);
          Assert.assertTrue(c.width() >= a.width());
          Assert.assertTrue(c.height() >= a.height());
          Assert.assertTrue(c.width() >= b.width());
          Assert.assertTrue(c.height() >= b.height());
          Assert.assertTrue(PAreasL.contains(c, a));
          Assert.assertTrue(PAreasL.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroWidth()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long w = Math.max(1L, outer.width());
          final long h = Math.max(1L, outer.height());
          final long px = (long) random.nextInt((int) w);
          final long py = (long) random.nextInt((int) h);
          final long ppx = outer.minimumX() + px;
          final long ppy = outer.minimumY() + py;

          final PAreaL<Object> scaled = PAreasL.create(
            outer.minimumX(),
            outer.minimumY(),
            0L,
            outer.height());

          System.out.printf(
            "%d %d\n",
            Long.valueOf(ppx),
            Long.valueOf(ppy));

          Assert.assertFalse(PAreasL.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testContainsZeroHeight()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long w = Math.max(1L, outer.width());
          final long h = Math.max(1L, outer.height());
          final long px = (long) random.nextInt((int) w);
          final long py = (long) random.nextInt((int) h);
          final long ppx = outer.minimumX() + px;
          final long ppy = outer.minimumY() + py;

          final PAreaL<Object> scaled = PAreasL.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.width(),
            0L);

          System.out.printf(
            "%d %d\n",
            Long.valueOf(ppx),
            Long.valueOf(ppy));

          Assert.assertFalse(PAreasL.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testFitBetweenHorizontalAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> left = generator.next();
          final PAreaL<Object> right = generator.next();

          final long leftmost = Math.min(
            Math.min(left.minimumX(), left.maximumX()),
            Math.min(right.minimumX(), right.maximumX()));
          final long rightmost = Math.max(
            Math.max(left.minimumX(), left.maximumX()),
            Math.max(right.minimumX(), right.maximumX()));

          final PAreaL<Object> fitted =
            PAreasL.fitBetweenHorizontal(outer, left, right);

          Assert.assertTrue(fitted.minimumX() >= leftmost);
          Assert.assertTrue(fitted.maximumX() <= rightmost);
          Assert.assertEquals(outer.minimumY(), fitted.minimumY());
          Assert.assertEquals(outer.maximumY(), fitted.maximumY());
        }
      });
  }

  @Test
  public void testFitBetweenVerticalAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Object> top = generator.next();
          final PAreaL<Object> bottom = generator.next();

          final long topmost = Math.min(
            Math.min(top.minimumY(), top.maximumY()),
            Math.min(bottom.minimumY(), bottom.maximumY()));
          final long bottommost = Math.max(
            Math.max(top.minimumY(), top.maximumY()),
            Math.max(bottom.minimumY(), bottom.maximumY()));

          final PAreaL<Object> fitted =
            PAreasL.fitBetweenVertical(outer, top, bottom);

          Assert.assertTrue(fitted.minimumY() >= topmost);
          Assert.assertTrue(fitted.maximumY() <= bottommost);
          Assert.assertEquals(outer.minimumX(), fitted.minimumX());
          Assert.assertEquals(outer.maximumX(), fitted.maximumX());
        }
      });
  }

  @Test
  public void testScaleFromMinYMinXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x_diff = gen.next().longValue();
          final long y_diff = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.scaleFromMinYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0L, outer.width() + x_diff),
            resized.width());
          Assert.assertEquals(
            Math.max(0L, outer.height() + y_diff),
            resized.height());

          Assert.assertEquals(outer.maximumX(), resized.maximumX());
          Assert.assertEquals(outer.maximumY(), resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMinYMaxXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x_diff = gen.next().longValue();
          final long y_diff = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.scaleFromMinYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0L, outer.width() + x_diff),
            resized.width());
          Assert.assertEquals(
            Math.max(0L, outer.height() + y_diff),
            resized.height());

          Assert.assertEquals(outer.minimumX(), resized.minimumX());
          Assert.assertEquals(outer.maximumY(), resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMaxXAll()
    throws Exception
  {
    final Generator<Long> gen = PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x_diff = gen.next().longValue();
          final long y_diff = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.scaleFromMaxYMaxX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0L, outer.width() + x_diff),
            resized.width());
          Assert.assertEquals(
            Math.max(0L, outer.height() + y_diff),
            resized.height());

          Assert.assertEquals(outer.minimumX(), resized.minimumX());
          Assert.assertEquals(outer.minimumY(), resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMinXAll()
    throws Exception
  {
    final Generator<Long> gen =
      PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x_diff = gen.next().longValue();
          final long y_diff = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.scaleFromMaxYMinX(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0L, outer.width() + x_diff),
            resized.width());
          Assert.assertEquals(
            Math.max(0L, outer.height() + y_diff),
            resized.height());

          Assert.assertEquals(outer.maximumX(), resized.maximumX());
          Assert.assertEquals(outer.minimumY(), resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromCenterAll()
    throws Exception
  {
    final Generator<Long> gen =
      PrimitiveGenerators.longs(-400L, 400L);
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final long x_diff = gen.next().longValue();
          final long y_diff = gen.next().longValue();

          final PAreaL<Object> resized =
            PAreasL.scaleFromCenter(outer, x_diff, y_diff);

          Assert.assertEquals(
            Math.max(0L, outer.width() + x_diff),
            resized.width());
          Assert.assertEquals(
            Math.max(0L, outer.height() + y_diff),
            resized.height());
        }
      });
  }

  @Test
  public void testCastAll()
    throws Exception
  {
    final Generator<PAreaL<Object>> generator = PAreaLGenerator.create();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final PAreaL<Integer> other = PAreasL.cast(outer);
          Assert.assertSame(outer, other);
        }
      });
  }

  @Test
  public void testSplitAlongXAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaL<Object>> generator =
      new PAreaLGenerator<>(new LongGenerator(0L, 100000L));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final int height = random.nextInt((int) (outer.height() + 1L));
          final PAreaXSplitL<Object, PAreaL<Object>> pair =
            PAreasL.splitAlongParallelToX(outer, (long) height);

          final PAreaL<Object> lower = pair.lower();
          final PAreaL<Object> upper = pair.upper();

          System.out.println("height: " + height);
          System.out.println("lower:  " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.height() <= outer.height());
          Assert.assertTrue(upper.height() <= outer.height());
          Assert.assertEquals(
            outer.height(),
            lower.height() + upper.height());
          Assert.assertEquals(outer.width(), lower.width());
          Assert.assertEquals(outer.width(), upper.width());

          if (outer.width() > 0L && outer.height() > 0L) {
            final boolean lower_ok = lower.width() > 0L && lower.height() > 0L;
            final boolean upper_ok = upper.width() > 0L && upper.height() > 0L;
            if (lower_ok) {
              Assert.assertTrue(PAreasL.overlaps(outer, lower));
              Assert.assertTrue(PAreasL.contains(outer, lower));
              if (upper_ok) {
                Assert.assertFalse(PAreasL.overlaps(lower, upper));
              }
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasL.overlaps(outer, upper));
              Assert.assertTrue(PAreasL.contains(outer, upper));
              if (lower_ok) {
                Assert.assertFalse(PAreasL.overlaps(lower, upper));
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
    final PAreaL<Object> box = PAreaL.of(0L, 10L, 0L, 10L);
    final PAreaXSplitL<Object, PAreaL<Object>> pair =
      PAreasL.splitAlongParallelToX(box, 5L);

    final PAreaL<Object> lower = pair.lower();
    final PAreaL<Object> upper = pair.upper();

    Assert.assertEquals(0L, lower.minimumX());
    Assert.assertEquals(10L, lower.maximumX());
    Assert.assertEquals(0L, upper.minimumX());
    Assert.assertEquals(10L, upper.maximumX());

    Assert.assertEquals(5L, lower.minimumY());
    Assert.assertEquals(10L, lower.maximumY());
    Assert.assertEquals(0L, upper.minimumY());
    Assert.assertEquals(5L, upper.maximumY());

    Assert.assertEquals(5L, lower.height());
    Assert.assertEquals(5L, upper.height());
  }

  @Test
  public void testSplitAlongXTiny()
    throws Exception
  {
    final PAreaL<Object> box = PAreaL.of(0L, 10L, 0L, 10L);
    final PAreaXSplitL<Object, PAreaL<Object>> pair =
      PAreasL.splitAlongParallelToX(box, 20L);

    final PAreaL<Object> lower = pair.lower();
    final PAreaL<Object> upper = pair.upper();

    Assert.assertEquals(0L, lower.minimumX());
    Assert.assertEquals(10L, lower.maximumX());
    Assert.assertEquals(0L, upper.minimumX());
    Assert.assertEquals(10L, upper.maximumX());

    Assert.assertEquals(10L, lower.minimumY());
    Assert.assertEquals(10L, lower.maximumY());
    Assert.assertEquals(0L, upper.minimumY());
    Assert.assertEquals(10L, upper.maximumY());

    Assert.assertEquals(0L, lower.height());
    Assert.assertEquals(10L, upper.height());
  }

  @Test
  public void testSplitAlongYSpecific()
    throws Exception
  {
    final PAreaL<Object> box = PAreaL.of(0L, 10L, 0L, 10L);
    final PAreaYSplitL<Object, PAreaL<Object>> pair =
      PAreasL.splitAlongParallelToY(box, 5L);

    final PAreaL<Object> lower = pair.lower();
    final PAreaL<Object> upper = pair.upper();

    Assert.assertEquals(0L, lower.minimumY());
    Assert.assertEquals(10L, lower.maximumY());
    Assert.assertEquals(0L, upper.minimumY());
    Assert.assertEquals(10L, upper.maximumY());

    Assert.assertEquals(0L, lower.minimumX());
    Assert.assertEquals(5L, lower.maximumX());
    Assert.assertEquals(5L, upper.minimumX());
    Assert.assertEquals(10L, upper.maximumX());

    Assert.assertEquals(5L, lower.width());
    Assert.assertEquals(5L, upper.width());
  }

  @Test
  public void testSplitAlongYTiny()
    throws Exception
  {
    final PAreaL<Object> box = PAreaL.of(0L, 10L, 0L, 10L);
    final PAreaYSplitL<Object, PAreaL<Object>> pair =
      PAreasL.splitAlongParallelToY(box, 20L);

    final PAreaL<Object> lower = pair.lower();
    final PAreaL<Object> upper = pair.upper();

    Assert.assertEquals(0L, lower.minimumY());
    Assert.assertEquals(10L, lower.maximumY());
    Assert.assertEquals(0L, upper.minimumY());
    Assert.assertEquals(10L, upper.maximumY());

    Assert.assertEquals(0L, lower.minimumX());
    Assert.assertEquals(10L, lower.maximumX());
    Assert.assertEquals(10L, upper.minimumX());
    Assert.assertEquals(10L, upper.maximumX());

    Assert.assertEquals(10L, lower.width());
    Assert.assertEquals(0L, upper.width());
  }

  @Test
  public void testSplitAlongYAll()
    throws Exception
  {
    final Random random = new Random(0L);
    final Generator<PAreaL<Object>> generator =
      new PAreaLGenerator<>(new LongGenerator(0L, 10000L));

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic<PAreaL<Object>>()
      {
        @Override
        protected void doSpecify(final PAreaL<Object> outer)
          throws Throwable
        {
          final int width = random.nextInt((int) (outer.width() + 1L));
          final PAreaYSplitL<Object, PAreaL<Object>> pair =
            PAreasL.splitAlongParallelToY(outer, (long) width);

          final PAreaL<Object> lower = pair.lower();
          final PAreaL<Object> upper = pair.upper();

          System.out.println("height: " + width);
          System.out.println("lower:   " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(lower.width() <= outer.width());
          Assert.assertTrue(upper.width() <= outer.width());
          Assert.assertEquals(outer.width(), lower.width() + upper.width());
          Assert.assertEquals(outer.height(), lower.height());
          Assert.assertEquals(outer.height(), upper.height());

          if (outer.width() > 0L && outer.height() > 0L) {
            final boolean lower_ok = lower.width() > 0L && lower.height() > 0L;
            final boolean upper_ok = upper.width() > 0L && upper.height() > 0L;
            if (lower_ok) {
              Assert.assertTrue(PAreasL.overlaps(outer, lower));
              Assert.assertTrue(PAreasL.contains(outer, lower));
            }
            if (upper_ok) {
              Assert.assertTrue(PAreasL.overlaps(outer, upper));
              Assert.assertTrue(PAreasL.contains(outer, upper));
            }
          }
        }
      });
  }
}
