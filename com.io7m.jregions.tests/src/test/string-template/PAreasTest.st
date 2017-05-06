PAreasTest(
  scalarType,
  scalarGeneratorType,
  areaType,
  areaSizeType,
  areaOpsType,
  className,
  splitXType,
  splitYType,
  splitXYType,
  opClass) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public final class <className>
{

  @Test
  public void testCreateAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> c =
            <areaOpsType>.create(
              outer.minimumX(),
              outer.minimumY(),
              outer.width(),
              outer.height());

          <opClass>.checkEquals(outer.minimumX(), c.minimumX());
          <opClass>.checkEquals(outer.minimumY(), c.minimumY());
          <opClass>.checkEquals(outer.maximumX(), c.maximumX());
          <opClass>.checkEquals(outer.maximumY(), c.maximumY());
        }
      });
  }

  @Test
  public void testContainsSelfAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          Assert.assertTrue(<areaOpsType>.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final <areaType>\<Object\> area = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <areaType>\<Object\> left = <areaType>.of(<opClass>.constant("-10"), <opClass>.constant("-1"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> right = <areaType>.of(<opClass>.constant("11"), <opClass>.constant("20"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> top = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <areaType>\<Object\> bottom = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("11"), <opClass>.constant("20"));

    Assert.assertFalse(<areaOpsType>.contains(area, left));
    Assert.assertFalse(<areaOpsType>.contains(area, right));
    Assert.assertFalse(<areaOpsType>.contains(area, top));
    Assert.assertFalse(<areaOpsType>.contains(area, bottom));

    Assert.assertFalse(<areaOpsType>.contains(left, area));
    Assert.assertFalse(<areaOpsType>.contains(right, area));
    Assert.assertFalse(<areaOpsType>.contains(top, area));
    Assert.assertFalse(<areaOpsType>.contains(bottom, area));
  }

  @Test
  public void testMoveRelative()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();

    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();

          final <areaType>\<Object\> moved = <areaOpsType>.moveRelative(outer, x, y);

          <opClass>.checkEquals(outer.width(), moved.width());
          <opClass>.checkEquals(outer.height(), moved.height());
          <opClass>.checkEquals(<opClass>.absolute(x), <opClass>.absoluteDifference(outer.minimumX(), moved.minimumX()));
          <opClass>.checkEquals(<opClass>.absolute(y), <opClass>.absoluteDifference(outer.minimumY(), moved.minimumY()));
        }
      });
  }

  @Test
  public void testMoveAbsolute()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();
          final <areaType>\<Object\> moved = <areaOpsType>.moveAbsolute(outer, x, y);

          <opClass>.checkEquals(outer.width(), moved.width());
          <opClass>.checkEquals(outer.height(), moved.height());
          <opClass>.checkEquals(x, moved.minimumX());
          <opClass>.checkEquals(y, moved.minimumY());
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> moved = <areaOpsType>.moveToOrigin(outer);

          <opClass>.checkEquals(outer.width(),  moved.width());
          <opClass>.checkEquals(outer.height(),  moved.height());
          <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumX());
          <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumY());
        }
      });
  }

  @Test
  public void testEqualsHashcode()
    throws Exception
  {
    QuickCheck.forAll(
      <opClass>.createGenerator(),
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> area)
          throws Throwable
        {
          final <areaType>\<Object\> other =
            <areaType>.of(
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
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> any)
          throws Throwable
        {
          Assert.assertEquals(<areaOpsType>.show(any), <areaOpsType>.show(any));

          final <areaType>\<Object\> next = generator.next();
          if (!Objects.equals(next, any)) {
            Assert.assertNotEquals(<areaOpsType>.show(any), <areaOpsType>.show(next));
          } else {
            Assert.assertEquals(<areaOpsType>.show(any), <areaOpsType>.show(next));
          }
        }
      });
  }

  @Test
  public void testZero()
    throws Exception
  {
    final <areaType>\<Object\> area = <areaType>.of(<opClass>.ZERO, <opClass>.ZERO, <opClass>.ZERO, <opClass>.ZERO);
    <opClass>.checkEquals(<opClass>.ZERO,  area.width());
    <opClass>.checkEquals(<opClass>.ZERO,  area.height());
  }

  @Test
  public void testHollowOut()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("99"), <opClass>.ZERO, <opClass>.constant("99"));
    final <areaType>\<Object\> inner = <areaOpsType>.hollowOut(
      outer,
      <opClass>.constant("10"),
      <opClass>.constant("20"),
      <opClass>.constant("30"),
      <opClass>.constant("40"));

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.minimumX());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("99"), <opClass>.constant("20")),  inner.maximumX());
    <opClass>.checkEquals(<opClass>.constant("30"),  inner.minimumY());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("99"), <opClass>.constant("40")),  inner.maximumY());
  }

  @Test
  public void testHollowOutSelfAll()
    throws Exception
  {
    QuickCheck.forAll(
      <opClass>.createGenerator(),
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> any)
          throws Throwable
        {
          Assert.assertEquals(any, <areaOpsType>.hollowOut(any, <opClass>.ZERO, <opClass>.ZERO,  <opClass>.ZERO, <opClass>.ZERO));
        }
      });
  }

  @Test
  public void testHollowOutEvenlyAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();

    QuickCheck.forAll(
      <opClass>.createGenerator(),
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> any)
          throws Throwable
        {
          final <scalarType> offset = gen.next();
          final <areaType>\<Object\> hollow0 =
            <areaOpsType>.hollowOut(any, offset, offset, offset, offset);
          final <areaType>\<Object\> hollow1 =
            <areaOpsType>.hollowOutEvenly(any, offset);
          Assert.assertEquals(hollow0, hollow1);
        }
      });
  }

  @Test
  public void testHollowOutTooLargeMinX()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaOpsType>.hollowOut(
      outer,
      <opClass>.constant("120"),
      <opClass>.constant("20"),
      <opClass>.constant("30"),
      <opClass>.constant("40"));

    <opClass>.checkEquals(<opClass>.constant("100"),  inner.minimumX());
    <opClass>.checkEquals(<opClass>.constant("100"),  inner.maximumX());
    <opClass>.checkEquals(<opClass>.constant("30"),  inner.minimumY());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("100"), <opClass>.constant("40")),  inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxX()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaOpsType>.hollowOut(
      outer,
      <opClass>.constant("10"),
      <opClass>.constant("120"),
      <opClass>.constant("30"),
      <opClass>.constant("40"));

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.maximumX());
    <opClass>.checkEquals(<opClass>.constant("30"),  inner.minimumY());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("100"), <opClass>.constant("40")),  inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMinY()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaOpsType>.hollowOut(
      outer,
      <opClass>.constant("10"),
      <opClass>.constant("20"),
      <opClass>.constant("120"),
      <opClass>.constant("40"));

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.minimumX());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("100"), <opClass>.constant("20")),  inner.maximumX());
    <opClass>.checkEquals(<opClass>.constant("100"),  inner.minimumY());
    <opClass>.checkEquals(<opClass>.constant("100"),  inner.maximumY());
  }

  @Test
  public void testHollowOutTooLargeMaxY()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaOpsType>.hollowOut(
      outer,
      <opClass>.constant("10"),
      <opClass>.constant("20"),
      <opClass>.constant("30"),
      <opClass>.constant("120"));

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.minimumX());
    <opClass>.checkEquals(<opClass>.subtract(<opClass>.constant("100"), <opClass>.constant("20")),  inner.maximumX());
    <opClass>.checkEquals(<opClass>.constant("30"),  inner.minimumY());
    <opClass>.checkEquals(<opClass>.constant("30"),  inner.maximumY());
  }

  @Test
  public void testAlignHorizontallyCenterSpecific()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    <opClass>.checkEquals(<opClass>.constant("100"),  outer.width());
    <opClass>.checkEquals(<opClass>.constant("100"),  outer.height());

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.height());

    final <areaType>\<Object\> aligned =
      <areaOpsType>.alignHorizontallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.height());
    <opClass>.checkEquals(<opClass>.constant("45"),  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("55"),  aligned.maximumX());
  }

  @Test
  public void testAlignHorizontallyMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned = <areaOpsType>.alignHorizontallyMinX(
            outer,
            inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          <opClass>.checkEquals(aligned.minimumX(), outer.minimumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMinXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignHorizontallyMinXOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          <opClass>.checkEquals(aligned.minimumX(), <opClass>.add(outer.minimumX(), offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignHorizontallyMaxX(outer, inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          <opClass>.checkEquals(aligned.maximumX(), outer.maximumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignHorizontallyMaxXOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          <opClass>.checkEquals(aligned.maximumX(), <opClass>.subtract(outer.maximumX(), offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignHorizontallyCenter(outer, inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          final <scalarType> diff_left =
            <opClass>.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final <scalarType> diff_right =
            <opClass>.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_right, diff_left), <opClass>.constant("1")) \<= 0);
        }
      });
  }

  @Test
  public void testAlignVerticallyCenterSpecific()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType>\<Object\> inner = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    <opClass>.checkEquals(<opClass>.constant("100"),  outer.width());
    <opClass>.checkEquals(<opClass>.constant("100"),  outer.height());

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.height());

    final <areaType>\<Object\> aligned =
      <areaOpsType>.alignVerticallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.height());
    <opClass>.checkEquals(<opClass>.ZERO,  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.maximumX());
    <opClass>.checkEquals(<opClass>.constant("45"),  aligned.minimumY());
    <opClass>.checkEquals(<opClass>.constant("55"),  aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterSpecific2()
    throws Exception
  {
    final <areaType>\<Object\> outer = <areaType>.of(<opClass>.constant("6"), <opClass>.constant("634"), <opClass>.ZERO, <opClass>.constant("16"));
    final <areaType>\<Object\> inner = <areaType>.of(<opClass>.constant("3"), <opClass>.constant("13"), <opClass>.constant("6"), <opClass>.constant("16"));

    <opClass>.checkEquals(<opClass>.constant("628"),  outer.width());
    <opClass>.checkEquals(<opClass>.constant("16"),  outer.height());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.height());

    final <areaType>\<Object\> aligned =
      <areaOpsType>.alignVerticallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.width());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.height());

    <opClass>.checkEquals(<opClass>.constant("3"),  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("13"),  aligned.maximumX());
    <opClass>.checkEquals(<opClass>.constant("3"),  aligned.minimumY());
    <opClass>.checkEquals(<opClass>.constant("13"),  aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignVerticallyCenter(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          final <scalarType> diff_top =
            <opClass>.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final <scalarType> diff_bottom =
            <opClass>.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());
          final <scalarType> diff_diff =
            <opClass>.absoluteDifference(diff_bottom, diff_top);
          Assert.assertTrue(<opClass>.compare(diff_diff, <opClass>.ONE) \<= 0);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignVerticallyMinY(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(aligned.minimumY(),  outer.minimumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignVerticallyMinYOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(aligned.minimumY(), <opClass>.add(outer.minimumY(), offset));
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignVerticallyMaxY(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(aligned.maximumY(),  outer.maximumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignVerticallyMaxYOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(aligned.maximumY(), <opClass>.subtract(outer.maximumY(), offset));
        }
      });
  }

  @Test
  public void testAlignCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignCenter(outer, inner);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          final <scalarType> diff_left =
            <opClass>.absoluteDifference(
              outer.minimumX(),
              aligned.minimumX());
          final <scalarType> diff_right =
            <opClass>.absoluteDifference(
              outer.maximumX(),
              aligned.maximumX());
          final <scalarType> diff_top =
            <opClass>.absoluteDifference(
              outer.minimumY(),
              aligned.minimumY());
          final <scalarType> diff_bottom =
            <opClass>.absoluteDifference(
              outer.maximumY(),
              aligned.maximumY());

          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_top, diff_bottom), <opClass>.ONE) \<= 0);
          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_right, diff_left), <opClass>.ONE) \<= 0);
        }
      });
  }

  @Test
  public void testAlignMinYMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMinYMinX(outer, inner);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(outer.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(outer.minimumY(),  aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMinXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset_top = gen.next();
          final <scalarType> offset_left = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMinYMinXOffset(outer, inner, offset_left, offset_top);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(<opClass>.add(outer.minimumX(), offset_left), aligned.minimumX());
          <opClass>.checkEquals(<opClass>.add(outer.minimumY(), offset_top), aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMaxYMinX(outer, inner);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(outer.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(outer.maximumY(),  aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset_bottom = gen.next();
          final <scalarType> offset_left = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMaxYMinXOffset(
              outer,
              inner,
              offset_left,
              offset_bottom);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(<opClass>.add(outer.minimumX(), offset_left), aligned.minimumX());
          <opClass>.checkEquals(<opClass>.subtract(outer.maximumY(), offset_bottom), aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMaxYMaxX(outer, inner);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(outer.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(outer.maximumY(),  aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset_bottom = gen.next();
          final <scalarType> offset_right = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMaxYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_bottom);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(<opClass>.subtract(outer.maximumX(), offset_right), aligned.maximumX());
          <opClass>.checkEquals(<opClass>.subtract(outer.maximumY(), offset_bottom), aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMinYMaxX(outer, inner);

          <opClass>.checkEquals(inner.width(), aligned.width());
          <opClass>.checkEquals(inner.height(), aligned.height());

          <opClass>.checkEquals(outer.maximumX(), aligned.maximumX());
          <opClass>.checkEquals(outer.minimumY(), aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> offset_top = gen.next();
          final <scalarType> offset_right = gen.next();

          final <areaType>\<Object\> inner = generator.next();
          final <areaType>\<Object\> aligned =
            <areaOpsType>.alignMinYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_top);

          <opClass>.checkEquals(inner.width(),  aligned.width());
          <opClass>.checkEquals(inner.height(),  aligned.height());

          <opClass>.checkEquals(<opClass>.subtract(outer.maximumX(), offset_right), aligned.maximumX());
          <opClass>.checkEquals(<opClass>.add(outer.minimumY(), offset_top), aligned.minimumY());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMinXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> expected_width = gen.next();
          final <scalarType> expected_height = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.setSizeFromMinYMinX(
              outer,
              expected_width,
              expected_height);

          <opClass>.checkEquals(outer.maximumX(), resized.maximumX());
          <opClass>.checkEquals(outer.maximumY(), resized.maximumY());
          <opClass>.checkEquals(expected_width,  resized.width());
          <opClass>.checkEquals(expected_height,  resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> expected_width = gen.next();
          final <scalarType> expected_height = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.setSizeFromMinYMaxX(
              outer,
              expected_width,
              expected_height);

          <opClass>.checkEquals(outer.minimumX(), resized.minimumX());
          <opClass>.checkEquals(outer.maximumY(), resized.maximumY());
          <opClass>.checkEquals(expected_width,  resized.width());
          <opClass>.checkEquals(expected_height,  resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMinXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> expected_width = gen.next();
          final <scalarType> expected_height = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.setSizeFromMaxYMinX(
              outer,
              expected_width,
              expected_height);

          <opClass>.checkEquals(outer.maximumX(), resized.maximumX());
          <opClass>.checkEquals(outer.minimumY(), resized.minimumY());
          <opClass>.checkEquals(expected_width,  resized.width());
          <opClass>.checkEquals(expected_height,  resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> expected_width = gen.next();
          final <scalarType> expected_height = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.setSizeFromMaxYMaxX(
              outer,
              expected_width,
              expected_height);

          <opClass>.checkEquals(outer.minimumX(), resized.minimumX());
          <opClass>.checkEquals(outer.minimumY(), resized.minimumY());
          <opClass>.checkEquals(expected_width,  resized.width());
          <opClass>.checkEquals(expected_height,  resized.height());
        }
      });
  }

  @Test
  public void testSetSizeFromCenterAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> expected_width = gen.next();
          final <scalarType> expected_height = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.setSizeFromCenter(outer, expected_width, expected_height);

          <opClass>.checkEquals(expected_width,  resized.width());
          <opClass>.checkEquals(expected_height,  resized.height());

          final <scalarType> diff_left =
            <opClass>.absoluteDifference(
              outer.minimumX(),
              resized.minimumX());

          final <scalarType> diff_right =
            <opClass>.absoluteDifference(
              outer.maximumX(),
              resized.maximumX());

          final <scalarType> diff_top =
            <opClass>.absoluteDifference(
              outer.minimumY(),
              resized.minimumY());

          final <scalarType> diff_bottom =
            <opClass>.absoluteDifference(
              outer.maximumY(),
              resized.maximumY());

          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_top, diff_bottom), <opClass>.ONE) \<= 0);
          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_right, diff_left), <opClass>.ONE) \<= 0);
        }
      });
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final <areaType>\<Object\> area = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <areaType>\<Object\> left = <areaType>.of(<opClass>.constant("-10"), <opClass>.constant("-1"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> right = <areaType>.of(<opClass>.constant("11"), <opClass>.constant("20"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> top = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <areaType>\<Object\> bottom = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("11"), <opClass>.constant("20"));

    Assert.assertFalse(<areaOpsType>.overlaps(area, left));
    Assert.assertFalse(<areaOpsType>.overlaps(area, right));
    Assert.assertFalse(<areaOpsType>.overlaps(area, top));
    Assert.assertFalse(<areaOpsType>.overlaps(area, bottom));

    Assert.assertFalse(<areaOpsType>.overlaps(left, area));
    Assert.assertFalse(<areaOpsType>.overlaps(right, area));
    Assert.assertFalse(<areaOpsType>.overlaps(top, area));
    Assert.assertFalse(<areaOpsType>.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final <areaType>\<Object\> area = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <areaType>\<Object\> left = <areaType>.of(<opClass>.constant("-10"), <opClass>.ONE, <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> right = <areaType>.of(<opClass>.constant("9"), <opClass>.constant("20"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType>\<Object\> top = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("-10"), <opClass>.ONE);
    final <areaType>\<Object\> bottom = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("9"), <opClass>.constant("20"));

    Assert.assertTrue(<areaOpsType>.overlaps(area, left));
    Assert.assertTrue(<areaOpsType>.overlaps(area, right));
    Assert.assertTrue(<areaOpsType>.overlaps(area, top));
    Assert.assertTrue(<areaOpsType>.overlaps(area, bottom));

    Assert.assertTrue(<areaOpsType>.overlaps(left, area));
    Assert.assertTrue(<areaOpsType>.overlaps(right, area));
    Assert.assertTrue(<areaOpsType>.overlaps(top, area));
    Assert.assertTrue(<areaOpsType>.overlaps(bottom, area));
  }

  @Test
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          boolean height_over = <opClass>.compare(outer.height(), <opClass>.ZERO) \> 0;
          boolean width_over = <opClass>.compare(outer.width(), <opClass>.ZERO) \> 0;

          if (height_over && width_over) {
            Assert.assertTrue(<areaOpsType>.overlaps(outer, outer));
          } else {
            Assert.assertFalse(<areaOpsType>.overlaps(outer, outer));
          }
        }
      });
  }

  @Test
  public void testContainsTrueAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> left_offset = gen.next();
          final <scalarType> right_offset = gen.next();
          final <scalarType> top_offset = gen.next();
          final <scalarType> bottom_offset = gen.next();
          final <areaType>\<Object\> inner =
            <areaOpsType>.hollowOut(outer, left_offset, right_offset, top_offset, bottom_offset);

          Assert.assertTrue(<areaOpsType>.contains(outer, inner));
          Assert.assertFalse(<areaOpsType>.contains(inner, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          Assert.assertTrue(<areaOpsType>.couldFitInside(outer, outer));
        }
      });
  }

  @Test
  public void testCouldFitInsideAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();

    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> inner =
          <areaOpsType>.hollowOutEvenly(outer, gen.next());
          Assert.assertTrue(<areaOpsType>.couldFitInside(inner, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.width());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.height());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          System.out.println(ppx + " " + ppy);

          boolean height_over = <opClass>.compare(outer.height(), <opClass>.ZERO) \> 0;
          boolean width_over = <opClass>.compare(outer.width(), <opClass>.ZERO) \> 0;

          if (width_over && height_over) {
            Assert.assertTrue(<areaOpsType>.containsPoint(outer, ppx, ppy));
          } else {
            Assert.assertFalse(<areaOpsType>.containsPoint(outer, ppx, ppy));
          }
        }
      });
  }

  @Test
  public void testContainingAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> a)
          throws Throwable
        {
          final <areaType>\<Object\> b = generator.next();
          final <areaType>\<Object\> c = <areaOpsType>.containing(a, b);
          Assert.assertTrue(<opClass>.compare(c.width(), a.width()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.height(), a.height()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.width(), b.width()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.height(), b.height()) >= 0);
          Assert.assertTrue(<areaOpsType>.contains(c, a));
          Assert.assertTrue(<areaOpsType>.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroWidth()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.width());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.height());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          final <areaType>\<Object\> scaled = <areaOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            <opClass>.ZERO,
            outer.height());

          System.out.println(ppx + " " + ppy);

          Assert.assertFalse(<areaOpsType>.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testContainsZeroHeight()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.width());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.height());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          final <areaType>\<Object\> scaled = <areaOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.width(),
            <opClass>.ZERO);

          System.out.println(ppx + " " + ppy);

          Assert.assertFalse(<areaOpsType>.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testFitBetweenHorizontalAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> left = generator.next();
          final <areaType>\<Object\> right = generator.next();

          final <scalarType> leftmost = <opClass>.minimum(
            <opClass>.minimum(left.minimumX(), left.maximumX()),
            <opClass>.minimum(right.minimumX(), right.maximumX()));
          final <scalarType> rightmost = <opClass>.maximum(
            <opClass>.maximum(left.minimumX(), left.maximumX()),
            <opClass>.maximum(right.minimumX(), right.maximumX()));

          final <areaType>\<Object\> fitted =
            <areaOpsType>.fitBetweenHorizontal(outer, left, right);

          Assert.assertTrue(<opClass>.compare(fitted.minimumX(), leftmost) >= 0);
          Assert.assertTrue(<opClass>.compare(fitted.maximumX(), rightmost) \<= 0);
          <opClass>.checkEquals(outer.minimumY(),  fitted.minimumY());
          <opClass>.checkEquals(outer.maximumY(),  fitted.maximumY());
        }
      });
  }

  @Test
  public void testFitBetweenVerticalAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Object\> top = generator.next();
          final <areaType>\<Object\> bottom = generator.next();

          final <scalarType> topmost = <opClass>.minimum(
            <opClass>.minimum(top.minimumY(), top.maximumY()),
            <opClass>.minimum(bottom.minimumY(), bottom.maximumY()));
          final <scalarType> bottommost = <opClass>.maximum(
            <opClass>.maximum(top.minimumY(), top.maximumY()),
            <opClass>.maximum(bottom.minimumY(), bottom.maximumY()));

          final <areaType>\<Object\> fitted =
            <areaOpsType>.fitBetweenVertical(outer, top, bottom);

          Assert.assertTrue(<opClass>.compare(fitted.minimumY(), topmost) >= 0);
          Assert.assertTrue(<opClass>.compare(fitted.maximumY(), bottommost) \<= 0);
          <opClass>.checkEquals(outer.minimumX(),  fitted.minimumX());
          <opClass>.checkEquals(outer.maximumX(),  fitted.maximumX());
        }
      });
  }

  @Test
  public void testScaleFromMinYMinXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.scaleFromMinYMinX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.width(), x_diff)), resized.width());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.height(), y_diff)), resized.height());

          <opClass>.checkEquals(outer.maximumX(),  resized.maximumX());
          <opClass>.checkEquals(outer.maximumY(),  resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMinYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.scaleFromMinYMaxX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.width(), x_diff)), resized.width());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.height(), y_diff)), resized.height());

          <opClass>.checkEquals(outer.minimumX(),  resized.minimumX());
          <opClass>.checkEquals(outer.maximumY(),  resized.maximumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.scaleFromMaxYMaxX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.width(), x_diff)), resized.width());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.height(), y_diff)), resized.height());

          <opClass>.checkEquals(outer.minimumX(),  resized.minimumX());
          <opClass>.checkEquals(outer.minimumY(),  resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromMaxYMinXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.scaleFromMaxYMinX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.width(), x_diff)), resized.width());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.height(), y_diff)), resized.height());

          <opClass>.checkEquals(outer.maximumX(),  resized.maximumX());
          <opClass>.checkEquals(outer.minimumY(),  resized.minimumY());
        }
      });
  }

  @Test
  public void testScaleFromCenterAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType>\<Object\> resized =
            <areaOpsType>.scaleFromCenter(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.width(), x_diff)), resized.width());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.ZERO, <opClass>.add(outer.height(), y_diff)), resized.height());
        }
      });
  }

  @Test
  public void testCastAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaType>\<Integer\> other = <areaOpsType>.cast(outer);
          Assert.assertSame(outer, other);
        }
      });
  }

  @Test
  public void testSplitAlongXAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> bound = <opClass>.add(outer.height(), <opClass>.ONE);
          final <scalarType> height = <opClass>.randomBounded(bound);

          final <splitXType>\<Object, <areaType>\<Object\>\> pair =
            <areaOpsType>.splitAlongParallelToX(outer, height);

          final <areaType>\<Object\> lower = pair.lower();
          final <areaType>\<Object\> upper = pair.upper();

          System.out.println("height: " + height);
          System.out.println("lower:  " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(<opClass>.compare(lower.height(), outer.height()) \<= 0);
          Assert.assertTrue(<opClass>.compare(upper.height(), outer.height()) \<= 0);
          <opClass>.checkEquals(outer.height(), <opClass>.add(lower.height(), upper.height()));
          <opClass>.checkEquals(outer.width(),  lower.width());
          <opClass>.checkEquals(outer.width(),  upper.width());

          boolean height_over = <opClass>.compare(outer.height(), <opClass>.ZERO) \> 0;
          boolean width_over = <opClass>.compare(outer.width(), <opClass>.ZERO) \> 0;

          if (width_over && height_over) {
            final boolean lower_ok = <opClass>.compare(lower.width(), <opClass>.ZERO) \> 0 && <opClass>.compare(lower.height(), <opClass>.ZERO) \> 0;
            final boolean upper_ok = <opClass>.compare(upper.width(), <opClass>.ZERO) \> 0 && <opClass>.compare(upper.height(), <opClass>.ZERO) \> 0;

            if (lower_ok) {
              Assert.assertTrue(<areaOpsType>.overlaps(outer, lower));
              Assert.assertTrue(<areaOpsType>.contains(outer, lower));
              if (upper_ok) {
                Assert.assertFalse(<areaOpsType>.overlaps(lower, upper));
              }
            }
            if (upper_ok) {
              Assert.assertTrue(<areaOpsType>.overlaps(outer, upper));
              Assert.assertTrue(<areaOpsType>.contains(outer, upper));
              if (lower_ok) {
                Assert.assertFalse(<areaOpsType>.overlaps(lower, upper));
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
    final <areaType>\<Object\> box =
      <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <splitXType>\<Object, <areaType>\<Object\>\> pair =
      <areaOpsType>.splitAlongParallelToX(box, <opClass>.constant("5"));

    final <areaType>\<Object\> lower = pair.lower();
    final <areaType>\<Object\> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.height());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.height());
  }

  @Test
  public void testSplitAlongXTiny()
    throws Exception
  {
    final <areaType>\<Object\> box =
      <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));
    final <splitXType>\<Object, <areaType>\<Object\>\> pair =
      <areaOpsType>.splitAlongParallelToX(box, <opClass>.constant("20"));

    final <areaType>\<Object\> lower = pair.lower();
    final <areaType>\<Object\> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("10"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.ZERO,  lower.height());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.height());
  }

  @Test
  public void testSplitAlongYSpecific()
    throws Exception
  {
    final <areaType>\<Object\> box =
      <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));
    final <splitYType>\<Object, <areaType>\<Object\>\> pair =
      <areaOpsType>.splitAlongParallelToY(box, <opClass>.constant("5"));

    final <areaType>\<Object\> lower = pair.lower();
    final <areaType>\<Object\> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.width());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.width());
  }

  @Test
  public void testSplitAlongYTiny()
    throws Exception
  {
    final <areaType>\<Object\> box =
      <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));
    final <splitYType>\<Object, <areaType>\<Object\>\> pair =
      <areaOpsType>.splitAlongParallelToY(box, <opClass>.constant("20"));

    final <areaType>\<Object\> lower = pair.lower();
    final <areaType>\<Object\> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.ZERO,  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("10"),  lower.width());
    <opClass>.checkEquals(<opClass>.ZERO,  upper.width());
  }

  @Test
  public void testSplitAlongYAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> bound = <opClass>.add(outer.width(), <opClass>.ONE);
          final <scalarType> width = <opClass>.randomBounded(bound);
          final <splitYType>\<Object, <areaType>\<Object\>\> pair =
            <areaOpsType>.splitAlongParallelToY(outer, width);

          final <areaType>\<Object\> lower = pair.lower();
          final <areaType>\<Object\> upper = pair.upper();

          System.out.println("height: " + width);
          System.out.println("lower:   " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(<opClass>.compare(lower.width(), outer.width()) \<= 0);
          Assert.assertTrue(<opClass>.compare(upper.width(), outer.width()) \<= 0);
          <opClass>.checkEquals(outer.width(), <opClass>.add(lower.width(), upper.width()));
          <opClass>.checkEquals(outer.height(), lower.height());
          <opClass>.checkEquals(outer.height(), upper.height());

          boolean height_over = <opClass>.compare(outer.height(), <opClass>.ZERO) \> 0;
          boolean width_over = <opClass>.compare(outer.width(), <opClass>.ZERO) \> 0;

          if (width_over && height_over) {
            final boolean lower_ok = <opClass>.compare(lower.width(), <opClass>.ZERO) \> 0 && <opClass>.compare(lower.height(), <opClass>.ZERO) \> 0;
            final boolean upper_ok = <opClass>.compare(upper.width(), <opClass>.ZERO) \> 0 && <opClass>.compare(upper.height(), <opClass>.ZERO) \> 0;

            if (lower_ok) {
              Assert.assertTrue(<areaOpsType>.overlaps(outer, lower));
              Assert.assertTrue(<areaOpsType>.contains(outer, lower));
            }
            if (upper_ok) {
              Assert.assertTrue(<areaOpsType>.overlaps(outer, upper));
              Assert.assertTrue(<areaOpsType>.contains(outer, upper));
            }
          }
        }
      });
  }

  @Test
  public void testSplitAlongXYAll()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x_bound = <opClass>.add(outer.width(), <opClass>.ONE);
          final <scalarType> y_bound = <opClass>.add(outer.height(), <opClass>.ONE);
          final <scalarType> x = <opClass>.randomBounded(x_bound);
          final <scalarType> y = <opClass>.randomBounded(y_bound);

          System.out.println("outer:      " + outer);
          System.out.println("x:          " + x);
          System.out.println("y:          " + y);

          final <splitXYType>\<Object, <areaType>\<Object\>\> quadrants =
            <areaOpsType>.splitAlongXY(outer, x, y);

          System.out.println("quadrants: " + quadrants);

          final <areaType>\<Object\> x0y0 = quadrants.x0y0();
          final <areaType>\<Object\> x1y0 = quadrants.x1y0();
          final <areaType>\<Object\> x0y1 = quadrants.x0y1();
          final <areaType>\<Object\> x1y1 = quadrants.x1y1();

          Assert.assertTrue(<opClass>.compare(x0y0.height(), outer.height()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y0.height(), outer.height()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x0y1.height(), outer.height()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y1.height(), outer.height()) \<= 0);

          Assert.assertTrue(<opClass>.compare(x0y0.width(), outer.width()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y0.width(), outer.width()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x0y1.width(), outer.width()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y1.width(), outer.width()) \<= 0);

          <opClass>.checkEquals(x0y0.minimumX(), outer.minimumX());
          <opClass>.checkEquals(x0y0.minimumY(), outer.minimumY());
          <opClass>.checkEquals(x0y0.maximumX(), <opClass>.add(x0y0.minimumX(), x));
          <opClass>.checkEquals(x0y0.maximumY(), <opClass>.add(x0y0.minimumY(), y));

          <opClass>.checkEquals(x1y0.minimumX(), <opClass>.add(x0y0.minimumX(), x));
          <opClass>.checkEquals(x1y0.minimumY(), outer.minimumY());
          <opClass>.checkEquals(x1y0.maximumX(), outer.maximumX());
          <opClass>.checkEquals(x1y0.maximumY(), <opClass>.add(x0y0.minimumY(), y));

          <opClass>.checkEquals(x0y1.minimumX(), outer.minimumX());
          <opClass>.checkEquals(x0y1.minimumY(), <opClass>.add(x0y0.minimumY(), y));
          <opClass>.checkEquals(x0y1.maximumX(), <opClass>.add(x0y0.minimumX(), x));
          <opClass>.checkEquals(x0y1.maximumY(), outer.maximumY());

          <opClass>.checkEquals(x1y1.minimumX(), <opClass>.add(x0y0.minimumX(), x));
          <opClass>.checkEquals(x1y1.minimumY(), <opClass>.add(x0y0.minimumY(), y));
          <opClass>.checkEquals(x1y1.maximumX(), outer.maximumX());
          <opClass>.checkEquals(x1y1.maximumY(), outer.maximumY());

          <opClass>.checkEquals(outer.height(), <opClass>.add(x0y0.height(), x0y1.height()));
          <opClass>.checkEquals(outer.height(), <opClass>.add(x1y0.height(), x1y1.height()));

          <opClass>.checkEquals(outer.width(), <opClass>.add(x0y0.width(), x1y0.width()));
          <opClass>.checkEquals(outer.width(), <opClass>.add(x0y1.width(), x1y1.width()));
        }
      });
  }

  @Test
  public void testSplitAlongXYSpecific()
    throws Exception
  {
    final <areaType>\<Object\> box =
      <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <splitXYType>\<Object, <areaType>\<Object\>\> quadrants =
      <areaOpsType>.splitAlongXY(box, <opClass>.constant("5"), <opClass>.constant("5"));

    final <areaType>\<Object\> x0y0 = quadrants.x0y0();
    final <areaType>\<Object\> x1y0 = quadrants.x1y0();
    final <areaType>\<Object\> x0y1 = quadrants.x0y1();
    final <areaType>\<Object\> x1y1 = quadrants.x1y1();

    <opClass>.checkEquals(<opClass>.ZERO, x0y0.minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y0.maximumX());
    <opClass>.checkEquals(<opClass>.ZERO, x0y0.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y0.maximumY());

    <opClass>.checkEquals(<opClass>.constant("5"), x1y0.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"), x1y0.maximumX());
    <opClass>.checkEquals(<opClass>.ZERO, x1y0.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), x1y0.maximumY());

    <opClass>.checkEquals(<opClass>.ZERO, x0y1.minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y1.maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y1.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"), x0y1.maximumY());

    <opClass>.checkEquals(<opClass>.constant("5"), x1y1.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"), x1y1.maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), x1y1.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"), x1y1.maximumY());
  }

  @Test
  public void testSize()
    throws Exception
  {
    final Generator\<<areaType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <areaType>\<Object\> outer)
          throws Throwable
        {
          final <areaSizeType>\<Object\> s =
            <areaOpsType>.size(outer);

          <opClass>.checkEquals(outer.width(), s.width());
          <opClass>.checkEquals(outer.height(), s.height());
        }
      });
  }
}

>>