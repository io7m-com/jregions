AreasTest(
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

package com.io7m.jregions.tests.core.unparameterized;

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> c =
            <areaOpsType>.create(
              outer.minimumX(),
              outer.minimumY(),
              outer.sizeX(),
              outer.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
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
    final <areaType> area = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    final <areaType> left = <areaType>.of(<opClass>.constant("-10"), <opClass>.constant("-1"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType> right = <areaType>.of(<opClass>.constant("11"), <opClass>.constant("20"), <opClass>.ZERO, <opClass>.constant("10"));
    final <areaType> top = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <areaType> bottom = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.constant("11"), <opClass>.constant("20"));

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

    final Generator\<<areaType>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();

          final <areaType> moved = <areaOpsType>.moveRelative(outer, x, y);

          <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();
          final <areaType> moved = <areaOpsType>.moveAbsolute(outer, x, y);

          <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
          <opClass>.checkEquals(x, moved.minimumX());
          <opClass>.checkEquals(y, moved.minimumY());
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> moved = <areaOpsType>.moveToOrigin(outer);

          <opClass>.checkEquals(outer.sizeX(),  moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(),  moved.sizeY());
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
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> area)
          throws Throwable
        {
          final <areaType> other =
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> any)
          throws Throwable
        {
          Assert.assertEquals(<areaOpsType>.show(any), <areaOpsType>.show(any));

          final <areaType> next = generator.next();
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
    final <areaType> area = <areaType>.of(<opClass>.ZERO, <opClass>.ZERO, <opClass>.ZERO, <opClass>.ZERO);
    <opClass>.checkEquals(<opClass>.ZERO,  area.sizeX());
    <opClass>.checkEquals(<opClass>.ZERO,  area.sizeY());
  }

  @Test
  public void testHollowOut()
    throws Exception
  {
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("99"), <opClass>.ZERO, <opClass>.constant("99"));
    final <areaType> inner = <areaOpsType>.hollowOut(
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
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> any)
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
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> any)
          throws Throwable
        {
          final <scalarType> offset = gen.next();
          final <areaType> hollow0 =
            <areaOpsType>.hollowOut(any, offset, offset, offset, offset);
          final <areaType> hollow1 =
            <areaOpsType>.hollowOutEvenly(any, offset);
          Assert.assertEquals(hollow0, hollow1);
        }
      });
  }

  @Test
  public void testHollowOutTooLargeMinX()
    throws Exception
  {
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaOpsType>.hollowOut(
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
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaOpsType>.hollowOut(
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
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaOpsType>.hollowOut(
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
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaOpsType>.hollowOut(
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
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    <opClass>.checkEquals(<opClass>.constant("100"),  outer.sizeX());
    <opClass>.checkEquals(<opClass>.constant("100"),  outer.sizeY());

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeY());

    final <areaType> aligned =
      <areaOpsType>.alignHorizontallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeY());
    <opClass>.checkEquals(<opClass>.constant("45"),  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("55"),  aligned.maximumX());
  }

  @Test
  public void testAlignHorizontallyMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned = <areaOpsType>.alignHorizontallyMinX(
            outer,
            inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

          <opClass>.checkEquals(aligned.minimumX(), outer.minimumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMinXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignHorizontallyMinXOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

          <opClass>.checkEquals(aligned.minimumX(), <opClass>.add(outer.minimumX(), offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignHorizontallyMaxX(outer, inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

          <opClass>.checkEquals(aligned.maximumX(), outer.maximumX());
        }
      });
  }

  @Test
  public void testAlignHorizontallyMaxXOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignHorizontallyMaxXOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

          <opClass>.checkEquals(aligned.maximumX(), <opClass>.subtract(outer.maximumX(), offset));
        }
      });
  }

  @Test
  public void testAlignHorizontallyCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignHorizontallyCenter(outer, inner);

          <opClass>.checkEquals(inner.minimumY(), aligned.minimumY());
          <opClass>.checkEquals(inner.maximumY(), aligned.maximumY());
          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

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
    final <areaType> outer = <areaType>.of(<opClass>.ZERO, <opClass>.constant("100"), <opClass>.ZERO, <opClass>.constant("100"));
    final <areaType> inner = <areaType>.of(<opClass>.ZERO, <opClass>.constant("10"), <opClass>.ZERO, <opClass>.constant("10"));

    <opClass>.checkEquals(<opClass>.constant("100"),  outer.sizeX());
    <opClass>.checkEquals(<opClass>.constant("100"),  outer.sizeY());

    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeY());

    final <areaType> aligned =
      <areaOpsType>.alignVerticallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeY());
    <opClass>.checkEquals(<opClass>.ZERO,  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.maximumX());
    <opClass>.checkEquals(<opClass>.constant("45"),  aligned.minimumY());
    <opClass>.checkEquals(<opClass>.constant("55"),  aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterSpecific2()
    throws Exception
  {
    final <areaType> outer = <areaType>.of(<opClass>.constant("6"), <opClass>.constant("634"), <opClass>.ZERO, <opClass>.constant("16"));
    final <areaType> inner = <areaType>.of(<opClass>.constant("3"), <opClass>.constant("13"), <opClass>.constant("6"), <opClass>.constant("16"));

    <opClass>.checkEquals(<opClass>.constant("628"),  outer.sizeX());
    <opClass>.checkEquals(<opClass>.constant("16"),  outer.sizeY());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  inner.sizeY());

    final <areaType> aligned =
      <areaOpsType>.alignVerticallyCenter(outer, inner);

    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeX());
    <opClass>.checkEquals(<opClass>.constant("10"),  aligned.sizeY());

    <opClass>.checkEquals(<opClass>.constant("3"),  aligned.minimumX());
    <opClass>.checkEquals(<opClass>.constant("13"),  aligned.maximumX());
    <opClass>.checkEquals(<opClass>.constant("3"),  aligned.minimumY());
    <opClass>.checkEquals(<opClass>.constant("13"),  aligned.maximumY());
  }

  @Test
  public void testAlignVerticallyCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignVerticallyCenter(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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
          Assert.assertTrue(<opClass>.compare(diff_diff, <opClass>.constant("1")) \<= 0);
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignVerticallyMinY(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(aligned.minimumY(),  outer.minimumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMinYOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignVerticallyMinYOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(aligned.minimumY(), <opClass>.add(outer.minimumY(), offset));
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignVerticallyMaxY(outer, inner);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(aligned.maximumY(),  outer.maximumY());
        }
      });
  }

  @Test
  public void testAlignVerticallyMaxYOffsetAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset = gen.next();
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignVerticallyMaxYOffset(outer, inner, offset);

          <opClass>.checkEquals(inner.minimumX(),  aligned.minimumX());
          <opClass>.checkEquals(inner.maximumX(),  aligned.maximumX());
          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(aligned.maximumY(), <opClass>.subtract(outer.maximumY(), offset));
        }
      });
  }

  @Test
  public void testAlignCenterAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignCenter(outer, inner);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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

          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_top, diff_bottom), <opClass>.constant("1")) \<= 0);
          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_right, diff_left), <opClass>.constant("1")) \<= 0);
        }
      });
  }

  @Test
  public void testAlignMinYMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMinYMinX(outer, inner);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset_top = gen.next();
          final <scalarType> offset_left = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMinYMinXOffset(outer, inner, offset_left, offset_top);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(<opClass>.add(outer.minimumX(), offset_left), aligned.minimumX());
          <opClass>.checkEquals(<opClass>.add(outer.minimumY(), offset_top), aligned.minimumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMinXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMaxYMinX(outer, inner);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset_bottom = gen.next();
          final <scalarType> offset_left = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMaxYMinXOffset(
              outer,
              inner,
              offset_left,
              offset_bottom);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(<opClass>.add(outer.minimumX(), offset_left), aligned.minimumX());
          <opClass>.checkEquals(<opClass>.subtract(outer.maximumY(), offset_bottom), aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMaxYMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMaxYMaxX(outer, inner);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset_bottom = gen.next();
          final <scalarType> offset_right = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMaxYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_bottom);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

          <opClass>.checkEquals(<opClass>.subtract(outer.maximumX(), offset_right), aligned.maximumX());
          <opClass>.checkEquals(<opClass>.subtract(outer.maximumY(), offset_bottom), aligned.maximumY());
        }
      });
  }

  @Test
  public void testAlignMinYMaxXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMinYMaxX(outer, inner);

          <opClass>.checkEquals(inner.sizeX(), aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(), aligned.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> offset_top = gen.next();
          final <scalarType> offset_right = gen.next();

          final <areaType> inner = generator.next();
          final <areaType> aligned =
            <areaOpsType>.alignMinYMaxXOffset(
              outer,
              inner,
              offset_right,
              offset_top);

          <opClass>.checkEquals(inner.sizeX(),  aligned.sizeX());
          <opClass>.checkEquals(inner.sizeY(),  aligned.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> expected_sizeX = gen.next();
          final <scalarType> expected_sizeY = gen.next();

          final <areaType> resized =
            <areaOpsType>.setSizeFromMinYMinX(
              outer,
              expected_sizeX,
              expected_sizeY);

          <opClass>.checkEquals(outer.maximumX(), resized.maximumX());
          <opClass>.checkEquals(outer.maximumY(), resized.maximumY());
          <opClass>.checkEquals(expected_sizeX,  resized.sizeX());
          <opClass>.checkEquals(expected_sizeY,  resized.sizeY());
        }
      });
  }

  @Test
  public void testSetSizeFromMinYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> expected_sizeX = gen.next();
          final <scalarType> expected_sizeY = gen.next();

          final <areaType> resized =
            <areaOpsType>.setSizeFromMinYMaxX(
              outer,
              expected_sizeX,
              expected_sizeY);

          <opClass>.checkEquals(outer.minimumX(), resized.minimumX());
          <opClass>.checkEquals(outer.maximumY(), resized.maximumY());
          <opClass>.checkEquals(expected_sizeX,  resized.sizeX());
          <opClass>.checkEquals(expected_sizeY,  resized.sizeY());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMinXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> expected_sizeX = gen.next();
          final <scalarType> expected_sizeY = gen.next();

          final <areaType> resized =
            <areaOpsType>.setSizeFromMaxYMinX(
              outer,
              expected_sizeX,
              expected_sizeY);

          <opClass>.checkEquals(outer.maximumX(), resized.maximumX());
          <opClass>.checkEquals(outer.minimumY(), resized.minimumY());
          <opClass>.checkEquals(expected_sizeX,  resized.sizeX());
          <opClass>.checkEquals(expected_sizeY,  resized.sizeY());
        }
      });
  }

  @Test
  public void testSetSizeFromMaxYMaxXAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> expected_sizeX = gen.next();
          final <scalarType> expected_sizeY = gen.next();

          final <areaType> resized =
            <areaOpsType>.setSizeFromMaxYMaxX(
              outer,
              expected_sizeX,
              expected_sizeY);

          <opClass>.checkEquals(outer.minimumX(), resized.minimumX());
          <opClass>.checkEquals(outer.minimumY(), resized.minimumY());
          <opClass>.checkEquals(expected_sizeX,  resized.sizeX());
          <opClass>.checkEquals(expected_sizeY,  resized.sizeY());
        }
      });
  }

  @Test
  public void testSetSizeFromCenterAll()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createNarrowNonNegativeScalarGenerator();
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> expected_sizeX = gen.next();
          final <scalarType> expected_sizeY = gen.next();

          final <areaType> resized =
            <areaOpsType>.setSizeFromCenter(outer, expected_sizeX, expected_sizeY);

          <opClass>.checkEquals(expected_sizeX,  resized.sizeX());
          <opClass>.checkEquals(expected_sizeY,  resized.sizeY());

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

          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_top, diff_bottom), <opClass>.constant("1")) \<= 0);
          Assert.assertTrue(<opClass>.compare(<opClass>.absoluteDifference(diff_right, diff_left), <opClass>.constant("1")) \<= 0);
        }
      });
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final <areaType> area = <areaType>.of(
      <opClass>.constant("0"), <opClass>.constant("10"),
      <opClass>.constant("0"), <opClass>.constant("10"));

    final <areaType> left =
      <areaType>.of(
        <opClass>.constant("-10"),
        <opClass>.constant("-1"),
        <opClass>.constant("0"),
        <opClass>.constant("10"));
    final <areaType> right =
      <areaType>.of(
        <opClass>.constant("11"),
        <opClass>.constant("20"),
        <opClass>.constant("0"),
        <opClass>.constant("10"));
    final <areaType> top =
      <areaType>.of(
        <opClass>.constant("0"),
        <opClass>.constant("10"),
        <opClass>.constant("-10"),
        <opClass>.constant("-1"));
    final <areaType> bottom =
      <areaType>.of(
        <opClass>.constant("0"),
        <opClass>.constant("10"),
        <opClass>.constant("11"),
        <opClass>.constant("20"));

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
    final <areaType> area = <areaType>.of(
      <opClass>.constant("0"),
      <opClass>.constant("10"),
      <opClass>.constant("0"),
      <opClass>.constant("10"));

    final <areaType> left =
      <areaType>.of(
        <opClass>.constant("-10"),
        <opClass>.constant("1"),
        <opClass>.constant("0"),
        <opClass>.constant("10"));
    final <areaType> right =
      <areaType>.of(
        <opClass>.constant("9"),
        <opClass>.constant("20"),
        <opClass>.constant("0"),
        <opClass>.constant("10"));
    final <areaType> top =
      <areaType>.of(
        <opClass>.constant("0"),
        <opClass>.constant("10"),
        <opClass>.constant("-10"),
        <opClass>.constant("1"));
    final <areaType> bottom =
      <areaType>.of(
        <opClass>.constant("0"),
        <opClass>.constant("10"),
        <opClass>.constant("9"),
        <opClass>.constant("20"));

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.constant("0")) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.constant("0")) \> 0;

          if (sizeY_over && sizeX_over) {
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> left_offset = gen.next();
          final <scalarType> right_offset = gen.next();
          final <scalarType> top_offset = gen.next();
          final <scalarType> bottom_offset = gen.next();
          final <areaType> inner =
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
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

    final Generator\<<areaType>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> inner =
          <areaOpsType>.hollowOutEvenly(outer, gen.next());
          Assert.assertTrue(<areaOpsType>.couldFitInside(inner, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.constant("1"), outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.constant("1"), outer.sizeY());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          System.out.println(outer);
          System.out.println(ppx + " " + ppy);

          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.constant("0")) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.constant("0")) \> 0;

          if (sizeX_over && sizeY_over) {
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> a)
          throws Throwable
        {
          final <areaType> b = generator.next();
          final <areaType> c = <areaOpsType>.containing(a, b);
          Assert.assertTrue(<opClass>.compare(c.sizeX(), a.sizeX()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeY(), a.sizeY()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeX(), b.sizeX()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeY(), b.sizeY()) >= 0);
          Assert.assertTrue(<areaOpsType>.contains(c, a));
          Assert.assertTrue(<areaOpsType>.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroWidth()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.constant("1"), outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.constant("1"), outer.sizeY());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          final <areaType> scaled = <areaOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            <opClass>.constant("0"),
            outer.sizeY());

          System.out.println(ppx + " " + ppy);

          Assert.assertFalse(<areaOpsType>.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testContainsZeroHeight()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.constant("1"), outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.constant("1"), outer.sizeY());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);

          final <areaType> scaled = <areaOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.sizeX(),
            <opClass>.constant("0"));

          System.out.println(ppx + " " + ppy);

          Assert.assertFalse(<areaOpsType>.containsPoint(scaled, ppx, ppy));
        }
      });
  }

  @Test
  public void testFitBetweenHorizontalAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> left = generator.next();
          final <areaType> right = generator.next();

          final <scalarType> leftmost = <opClass>.minimum(
            <opClass>.minimum(left.minimumX(), left.maximumX()),
            <opClass>.minimum(right.minimumX(), right.maximumX()));
          final <scalarType> rightmost = <opClass>.maximum(
            <opClass>.maximum(left.minimumX(), left.maximumX()),
            <opClass>.maximum(right.minimumX(), right.maximumX()));

          final <areaType> fitted =
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaType> top = generator.next();
          final <areaType> bottom = generator.next();

          final <scalarType> topmost = <opClass>.minimum(
            <opClass>.minimum(top.minimumY(), top.maximumY()),
            <opClass>.minimum(bottom.minimumY(), bottom.maximumY()));
          final <scalarType> bottommost = <opClass>.maximum(
            <opClass>.maximum(top.minimumY(), top.maximumY()),
            <opClass>.maximum(bottom.minimumY(), bottom.maximumY()));

          final <areaType> fitted =
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType> resized =
            <areaOpsType>.scaleFromMinYMinX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeX(), x_diff)), resized.sizeX());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeY(), y_diff)), resized.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType> resized =
            <areaOpsType>.scaleFromMinYMaxX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeX(), x_diff)), resized.sizeX());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeY(), y_diff)), resized.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType> resized =
            <areaOpsType>.scaleFromMaxYMaxX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeX(), x_diff)), resized.sizeX());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeY(), y_diff)), resized.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType> resized =
            <areaOpsType>.scaleFromMaxYMinX(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeX(), x_diff)), resized.sizeX());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeY(), y_diff)), resized.sizeY());

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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_diff = gen.next();
          final <scalarType> y_diff = gen.next();

          final <areaType> resized =
            <areaOpsType>.scaleFromCenter(outer, x_diff, y_diff);

          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeX(), x_diff)), resized.sizeX());
          <opClass>.checkEquals(<opClass>.maximum(<opClass>.constant("0"), <opClass>.add(outer.sizeY(), y_diff)), resized.sizeY());
        }
      });
  }

  @Test
  public void testSplitAlongXAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> bound = <opClass>.add(outer.sizeY(), <opClass>.constant("1"));
          final <scalarType> sizeY = <opClass>.randomBounded(bound);

          final <splitXType>\<<areaType>\> pair =
            <areaOpsType>.splitAlongParallelToX(outer, sizeY);

          final <areaType> lower = pair.lower();
          final <areaType> upper = pair.upper();

          System.out.println("sizeY: " + sizeY);
          System.out.println("lower:  " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(<opClass>.compare(lower.sizeY(), outer.sizeY()) \<= 0);
          Assert.assertTrue(<opClass>.compare(upper.sizeY(), outer.sizeY()) \<= 0);
          <opClass>.checkEquals(outer.sizeY(), <opClass>.add(lower.sizeY(), upper.sizeY()));
          <opClass>.checkEquals(outer.sizeX(),  lower.sizeX());
          <opClass>.checkEquals(outer.sizeX(),  upper.sizeX());

          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.constant("0")) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.constant("0")) \> 0;

          if (sizeX_over && sizeY_over) {
            final boolean lower_ok = <opClass>.compare(lower.sizeX(), <opClass>.constant("0")) \> 0 && <opClass>.compare(lower.sizeY(), <opClass>.constant("0")) \> 0;
            final boolean upper_ok = <opClass>.compare(upper.sizeX(), <opClass>.constant("0")) \> 0 && <opClass>.compare(upper.sizeY(), <opClass>.constant("0")) \> 0;

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
    final <areaType> box =
      <areaType>.of(<opClass>.constant("0"), <opClass>.constant("10"), <opClass>.constant("0"), <opClass>.constant("10"));

    final <splitXType>\<<areaType>\> pair =
      <areaOpsType>.splitAlongParallelToX(box, <opClass>.constant("5"));

    final <areaType> lower = pair.lower();
    final <areaType> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.sizeY());

    Assert.assertFalse(<areaOpsType>.overlaps(upper, lower));
    Assert.assertTrue(<areaOpsType>.overlaps(box, lower));
    Assert.assertTrue(<areaOpsType>.overlaps(box, upper));
  }

  @Test
  public void testSplitAlongXTiny()
    throws Exception
  {
    final <areaType> box =
      <areaType>.of(<opClass>.constant("0"), <opClass>.constant("10"), <opClass>.constant("0"), <opClass>.constant("10"));
    final <splitXType>\<<areaType>\> pair =
      <areaOpsType>.splitAlongParallelToX(box, <opClass>.constant("20"));

    final <areaType> lower = pair.lower();
    final <areaType> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("10"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.sizeY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.sizeY());
  }

  @Test
  public void testSplitAlongYSpecific()
    throws Exception
  {
    final <areaType> box =
      <areaType>.of(<opClass>.constant("0"), <opClass>.constant("10"), <opClass>.constant("0"), <opClass>.constant("10"));
    final <splitYType>\<<areaType>\> pair =
      <areaOpsType>.splitAlongParallelToY(box, <opClass>.constant("5"));

    final <areaType> lower = pair.lower();
    final <areaType> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("5"),  lower.sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"),  upper.sizeX());

    Assert.assertFalse(<areaOpsType>.overlaps(upper, lower));
    Assert.assertTrue(<areaOpsType>.overlaps(box, lower));
    Assert.assertTrue(<areaOpsType>.overlaps(box, upper));
  }

  @Test
  public void testSplitAlongYTiny()
    throws Exception
  {
    final <areaType> box =
      <areaType>.of(<opClass>.constant("0"), <opClass>.constant("10"), <opClass>.constant("0"), <opClass>.constant("10"));
    final <splitYType>\<<areaType>\> pair =
      <areaOpsType>.splitAlongParallelToY(box, <opClass>.constant("20"));

    final <areaType> lower = pair.lower();
    final <areaType> upper = pair.upper();

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumY());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.minimumY());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumY());

    <opClass>.checkEquals(<opClass>.constant("0"),  lower.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  lower.maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"),  upper.maximumX());

    <opClass>.checkEquals(<opClass>.constant("10"),  lower.sizeX());
    <opClass>.checkEquals(<opClass>.constant("0"),  upper.sizeX());
  }

  @Test
  public void testSplitAlongYAll()
    throws Exception
  {
    final Generator\<<areaType>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> bound = <opClass>.add(outer.sizeX(), <opClass>.constant("1"));
          final <scalarType> sizeX = <opClass>.randomBounded(bound);
          final <splitYType>\<<areaType>\> pair =
            <areaOpsType>.splitAlongParallelToY(outer, sizeX);

          final <areaType> lower = pair.lower();
          final <areaType> upper = pair.upper();

          System.out.println("sizeY: " + sizeX);
          System.out.println("lower:   " + lower);
          System.out.println("upper:  " + upper);

          Assert.assertTrue(<opClass>.compare(lower.sizeX(), outer.sizeX()) \<= 0);
          Assert.assertTrue(<opClass>.compare(upper.sizeX(), outer.sizeX()) \<= 0);
          <opClass>.checkEquals(outer.sizeX(), <opClass>.add(lower.sizeX(), upper.sizeX()));
          <opClass>.checkEquals(outer.sizeY(), lower.sizeY());
          <opClass>.checkEquals(outer.sizeY(), upper.sizeY());

          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.constant("0")) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.constant("0")) \> 0;

          if (sizeX_over && sizeY_over) {
            final boolean lower_ok = <opClass>.compare(lower.sizeX(), <opClass>.constant("0")) \> 0 && <opClass>.compare(lower.sizeY(), <opClass>.constant("0")) \> 0;
            final boolean upper_ok = <opClass>.compare(upper.sizeX(), <opClass>.constant("0")) \> 0 && <opClass>.compare(upper.sizeY(), <opClass>.constant("0")) \> 0;

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
    final Generator\<<areaType>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <scalarType> x_bound = <opClass>.add(outer.sizeX(), <opClass>.constant("1"));
          final <scalarType> y_bound = <opClass>.add(outer.sizeY(), <opClass>.constant("1"));
          final <scalarType> x = <opClass>.randomBounded(x_bound);
          final <scalarType> y = <opClass>.randomBounded(y_bound);

          System.out.println("outer:      " + outer);
          System.out.println("x:          " + x);
          System.out.println("y:          " + y);

          final <splitXYType>\<<areaType>\> quadrants =
            <areaOpsType>.splitAlongXY(outer, x, y);

          System.out.println("quadrants: " + quadrants);

          final <areaType> x0y0 = quadrants.x0y0();
          final <areaType> x1y0 = quadrants.x1y0();
          final <areaType> x0y1 = quadrants.x0y1();
          final <areaType> x1y1 = quadrants.x1y1();

          Assert.assertTrue(<opClass>.compare(x0y0.sizeY(), outer.sizeY()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y0.sizeY(), outer.sizeY()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x0y1.sizeY(), outer.sizeY()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y1.sizeY(), outer.sizeY()) \<= 0);

          Assert.assertTrue(<opClass>.compare(x0y0.sizeX(), outer.sizeX()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y0.sizeX(), outer.sizeX()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x0y1.sizeX(), outer.sizeX()) \<= 0);
          Assert.assertTrue(<opClass>.compare(x1y1.sizeX(), outer.sizeX()) \<= 0);

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

          <opClass>.checkEquals(outer.sizeY(), <opClass>.add(x0y0.sizeY(), x0y1.sizeY()));
          <opClass>.checkEquals(outer.sizeY(), <opClass>.add(x1y0.sizeY(), x1y1.sizeY()));

          <opClass>.checkEquals(outer.sizeX(), <opClass>.add(x0y0.sizeX(), x1y0.sizeX()));
          <opClass>.checkEquals(outer.sizeX(), <opClass>.add(x0y1.sizeX(), x1y1.sizeX()));
        }
      });
  }

  @Test
  public void testSplitAlongXYSpecific()
    throws Exception
  {
    final <areaType> box =
      <areaType>.of(<opClass>.constant("0"), <opClass>.constant("10"), <opClass>.constant("0"), <opClass>.constant("10"));

    final <splitXYType>\<<areaType>\> quadrants =
      <areaOpsType>.splitAlongXY(box, <opClass>.constant("5"), <opClass>.constant("5"));

    final <areaType> x0y0 = quadrants.x0y0();
    final <areaType> x1y0 = quadrants.x1y0();
    final <areaType> x0y1 = quadrants.x0y1();
    final <areaType> x1y1 = quadrants.x1y1();

    <opClass>.checkEquals(<opClass>.constant("0"), x0y0.minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y0.maximumX());
    <opClass>.checkEquals(<opClass>.constant("0"), x0y0.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), x0y0.maximumY());

    <opClass>.checkEquals(<opClass>.constant("5"), x1y0.minimumX());
    <opClass>.checkEquals(<opClass>.constant("10"), x1y0.maximumX());
    <opClass>.checkEquals(<opClass>.constant("0"), x1y0.minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), x1y0.maximumY());

    <opClass>.checkEquals(<opClass>.constant("0"), x0y1.minimumX());
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
    final Generator\<<areaType>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<areaType>\>()
      {
        @Override
        protected void doSpecify(final <areaType> outer)
          throws Throwable
        {
          final <areaSizeType> s =
            <areaOpsType>.size(outer);

          <opClass>.checkEquals(outer.sizeX(), s.sizeX());
          <opClass>.checkEquals(outer.sizeY(), s.sizeY());
        }
      });
  }
}

>>