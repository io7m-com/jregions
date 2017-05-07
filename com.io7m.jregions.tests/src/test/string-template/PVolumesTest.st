PVolumesTest(
  scalarType,
  scalarGeneratorType,
  volumeType,
  volumeSizeType,
  volumeOpsType,
  className,
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
  public void testCouldFitInsideFalseSpecificX0()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.constant("-1"), <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificX1()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("11"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificY0()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-1"), <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificY1()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("11"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificZ0()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-1"), <opClass>.constant("10"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificZ1()
    throws Exception
  {
    final <volumeType>\<Object\> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("11"));

    final <volumeType>\<Object\> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCreateAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <volumeType>\<Object\> c =
            <volumeOpsType>.create(
              outer.minimumX(),
              outer.minimumY(),
              outer.minimumZ(),
              outer.sizeX(),
              outer.sizeY(),
              outer.sizeZ());

          <opClass>.checkEquals(outer.minimumX(), c.minimumX());
          <opClass>.checkEquals(outer.minimumY(), c.minimumY());
          <opClass>.checkEquals(outer.minimumZ(), c.minimumZ());
          <opClass>.checkEquals(outer.maximumX(), c.maximumX());
          <opClass>.checkEquals(outer.maximumY(), c.maximumY());
          <opClass>.checkEquals(outer.maximumZ(), c.maximumZ());
        }
      });
  }

  @Test
  public void testContainsSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          Assert.assertTrue(<volumeOpsType>.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <volumeType>\<Object\> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"));

    Assert.assertFalse(<volumeOpsType>.contains(volume, out_x_lo));
    Assert.assertFalse(<volumeOpsType>.contains(volume, out_x_hi));
    Assert.assertFalse(<volumeOpsType>.contains(volume, out_y_lo));
    Assert.assertFalse(<volumeOpsType>.contains(volume, out_y_hi));
    Assert.assertFalse(<volumeOpsType>.contains(volume, out_z_lo));
    Assert.assertFalse(<volumeOpsType>.contains(volume, out_z_hi));

    Assert.assertFalse(<volumeOpsType>.contains(out_x_lo, volume));
    Assert.assertFalse(<volumeOpsType>.contains(out_x_hi, volume));
    Assert.assertFalse(<volumeOpsType>.contains(out_y_lo, volume));
    Assert.assertFalse(<volumeOpsType>.contains(out_y_hi, volume));
    Assert.assertFalse(<volumeOpsType>.contains(out_z_lo, volume));
    Assert.assertFalse(<volumeOpsType>.contains(out_z_hi, volume));
  }

  @Test
  public void testMoveRelative()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();

    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();
          final <scalarType> z = gen.next();

          final <volumeType>\<Object\> moved = <volumeOpsType>.moveRelative(outer, x, y, z);

          <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
          <opClass>.checkEquals(outer.sizeZ(), moved.sizeZ());
          <opClass>.checkEquals(<opClass>.absolute(x), <opClass>.absoluteDifference(outer.minimumX(), moved.minimumX()));
          <opClass>.checkEquals(<opClass>.absolute(y), <opClass>.absoluteDifference(outer.minimumY(), moved.minimumY()));
          <opClass>.checkEquals(<opClass>.absolute(z), <opClass>.absoluteDifference(outer.minimumZ(), moved.minimumZ()));
        }
      });
  }

  @Test
  public void testMoveAbsolute()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> x = gen.next();
          final <scalarType> y = gen.next();
          final <scalarType> z = gen.next();
          final <volumeType>\<Object\> moved = <volumeOpsType>.moveAbsolute(outer, x, y, z);

          <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
          <opClass>.checkEquals(outer.sizeZ(), moved.sizeZ());
          <opClass>.checkEquals(x, moved.minimumX());
          <opClass>.checkEquals(y, moved.minimumY());
          <opClass>.checkEquals(z, moved.minimumZ());
        }
      });
  }

  @Test
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <volumeType>\<Object\> moved = <volumeOpsType>.moveToOrigin(outer);

          <opClass>.checkEquals(outer.sizeX(),  moved.sizeX());
          <opClass>.checkEquals(outer.sizeY(),  moved.sizeY());
          <opClass>.checkEquals(outer.sizeZ(),  moved.sizeZ());
          <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumX());
          <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumY());
          <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumZ());
        }
      });
  }

  @Test
  public void testEqualsHashcode()
    throws Exception
  {
    QuickCheck.forAll(
      <opClass>.createGenerator(),
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> volume)
          throws Throwable
        {
          final <volumeType>\<Object\> other =
            <volumeType>.of(
              volume.minimumX(),
              volume.maximumX(),
              volume.minimumY(),
              volume.maximumY(),
              volume.minimumZ(),
              volume.maximumZ());
          Assert.assertEquals(volume, volume);
          Assert.assertEquals(volume, other);
          Assert.assertEquals(other, volume);
          Assert.assertEquals((long) volume.hashCode(), (long) other.hashCode());

          Assert.assertNotEquals(volume, null);
          Assert.assertNotEquals(volume, Integer.valueOf(23));
        }
      });
  }

  @Test
  public void testShowAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> any)
          throws Throwable
        {
          Assert.assertEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(any));

          final <volumeType>\<Object\> next = generator.next();
          if (!Objects.equals(next, any)) {
            Assert.assertNotEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(next));
          } else {
            Assert.assertEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(next));
          }
        }
      });
  }

  @Test
  public void testZero()
    throws Exception
  {
    final <volumeType>\<Object\> volume = <volumeType>.of(
      <opClass>.ZERO, <opClass>.ZERO,
      <opClass>.ZERO, <opClass>.ZERO,
      <opClass>.ZERO, <opClass>.ZERO);
    <opClass>.checkEquals(<opClass>.ZERO, volume.sizeX());
    <opClass>.checkEquals(<opClass>.ZERO, volume.sizeY());
    <opClass>.checkEquals(<opClass>.ZERO, volume.sizeZ());
  }

  @Test
  public void testOverlapsFalseSpecific()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <volumeType>\<Object\> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"));

    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_x_lo));
    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_x_hi));
    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_y_lo));
    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_y_hi));
    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_z_lo));
    Assert.assertFalse(<volumeOpsType>.overlaps(volume, out_z_hi));

    Assert.assertFalse(<volumeOpsType>.overlaps(out_x_lo, volume));
    Assert.assertFalse(<volumeOpsType>.overlaps(out_x_hi, volume));
    Assert.assertFalse(<volumeOpsType>.overlaps(out_y_lo, volume));
    Assert.assertFalse(<volumeOpsType>.overlaps(out_y_hi, volume));
    Assert.assertFalse(<volumeOpsType>.overlaps(out_z_lo, volume));
    Assert.assertFalse(<volumeOpsType>.overlaps(out_z_hi, volume));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("9"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType>\<Object\> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("9"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType>\<Object\> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("1"));
    final <volumeType>\<Object\> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("9"), <opClass>.constant("21"));

    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_x_lo));
    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_x_hi));
    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_y_lo));
    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_y_hi));
    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_z_lo));
    Assert.assertTrue(<volumeOpsType>.overlaps(volume, out_z_hi));

    Assert.assertTrue(<volumeOpsType>.overlaps(out_x_lo, volume));
    Assert.assertTrue(<volumeOpsType>.overlaps(out_x_hi, volume));
    Assert.assertTrue(<volumeOpsType>.overlaps(out_y_lo, volume));
    Assert.assertTrue(<volumeOpsType>.overlaps(out_y_hi, volume));
    Assert.assertTrue(<volumeOpsType>.overlaps(out_z_lo, volume));
    Assert.assertTrue(<volumeOpsType>.overlaps(out_z_hi, volume));
  }

  @Test
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.ZERO) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.ZERO) \> 0;
          boolean sizeZ_over = <opClass>.compare(outer.sizeX(), <opClass>.ZERO) \> 0;

          if (sizeY_over && sizeX_over && sizeZ_over) {
            Assert.assertTrue(<volumeOpsType>.overlaps(outer, outer));
          } else {
            Assert.assertFalse(<volumeOpsType>.overlaps(outer, outer));
          }
        }
      });
  }

  @Test
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          Assert.assertTrue(<volumeOpsType>.couldFitInside(outer, outer));
        }
      });
  }

  @Test
  public void testContainsPointAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.sizeY());
          final <scalarType> d = <opClass>.maximum(<opClass>.ONE, outer.sizeZ());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> pz = <opClass>.randomBounded(d);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);
          final <scalarType> ppz = <opClass>.add(outer.minimumZ(), pz);

          System.out.println(ppx + " " + ppy + " " + ppz);

          boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.ZERO) \> 0;
          boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.ZERO) \> 0;
          boolean sizeZ_over = <opClass>.compare(outer.sizeZ(), <opClass>.ZERO) \> 0;

          if (sizeX_over && sizeY_over && sizeZ_over) {
            Assert.assertTrue(<volumeOpsType>.containsPoint(outer, ppx, ppy, ppz));
          } else {
            Assert.assertFalse(<volumeOpsType>.containsPoint(outer, ppx, ppy, ppz));
          }
        }
      });
  }

  @Test
  public void testContainingAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> a)
          throws Throwable
        {
          final <volumeType>\<Object\> b = generator.next();
          final <volumeType>\<Object\> c = <volumeOpsType>.containing(a, b);
          Assert.assertTrue(<opClass>.compare(c.sizeX(), a.sizeX()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeY(), a.sizeY()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeZ(), a.sizeZ()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeX(), b.sizeX()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeY(), b.sizeY()) >= 0);
          Assert.assertTrue(<opClass>.compare(c.sizeZ(), b.sizeZ()) >= 0);
          Assert.assertTrue(<volumeOpsType>.contains(c, a));
          Assert.assertTrue(<volumeOpsType>.contains(c, b));
        }
      });
  }

  @Test
  public void testContainsZeroSizeX()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.sizeY());
          final <scalarType> d = <opClass>.maximum(<opClass>.ONE, outer.sizeZ());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> pz = <opClass>.randomBounded(d);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);
          final <scalarType> ppz = <opClass>.add(outer.minimumZ(), pz);

          System.out.println(ppx + " " + ppy + " " + ppz);

          final <volumeType>\<Object\> scaled = <volumeOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.minimumZ(),
            <opClass>.ZERO,
            outer.sizeY(),
            outer.sizeZ());

          System.out.println(ppx + " " + ppy);

          Assert.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
        }
      });
  }

  @Test
  public void testContainsZeroSizeY()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.sizeY());
          final <scalarType> d = <opClass>.maximum(<opClass>.ONE, outer.sizeZ());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> pz = <opClass>.randomBounded(d);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);
          final <scalarType> ppz = <opClass>.add(outer.minimumZ(), pz);

          System.out.println(ppx + " " + ppy + " " + ppz);

          final <volumeType>\<Object\> scaled = <volumeOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.minimumZ(),
            outer.sizeX(),
            <opClass>.ZERO,
            outer.sizeZ());

          System.out.println(ppx + " " + ppy + " " + ppz);

          Assert.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
        }
      });
  }

  @Test
  public void testContainsZeroSizeZ()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <scalarType> w = <opClass>.maximum(<opClass>.ONE, outer.sizeX());
          final <scalarType> h = <opClass>.maximum(<opClass>.ONE, outer.sizeY());
          final <scalarType> d = <opClass>.maximum(<opClass>.ONE, outer.sizeZ());
          final <scalarType> px = <opClass>.randomBounded(w);
          final <scalarType> py = <opClass>.randomBounded(h);
          final <scalarType> pz = <opClass>.randomBounded(d);
          final <scalarType> ppx = <opClass>.add(outer.minimumX(), px);
          final <scalarType> ppy = <opClass>.add(outer.minimumY(), py);
          final <scalarType> ppz = <opClass>.add(outer.minimumZ(), pz);

          System.out.println(ppx + " " + ppy + " " + ppz);

          final <volumeType>\<Object\> scaled = <volumeOpsType>.create(
            outer.minimumX(),
            outer.minimumY(),
            outer.minimumZ(),
            outer.sizeX(),
            outer.sizeY(),
            <opClass>.ZERO);

          System.out.println(ppx + " " + ppy + " " + ppz);

          Assert.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
        }
      });
  }

  @Test
  public void testContainsFalseSpecificX0()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("-1"), <opClass>.constant("5"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificX1()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("10"), <opClass>.constant("5"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificY0()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("-1"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificY1()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("10"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificZ0()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("5"), <opClass>.constant("-1")));
  }

  @Test
  public void testContainsFalseSpecificZ1()
    throws Exception
  {
    final <volumeType>\<Object\> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assert.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("5"), <opClass>.constant("10")));
  }

  @Test
  public void testCastAll()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <volumeType>\<Integer\> other = <volumeOpsType>.cast(outer);
          Assert.assertSame(outer, other);
        }
      });
  }

  @Test
  public void testSize()
    throws Exception
  {
    final Generator\<<volumeType>\<Object\>\> generator = <opClass>.createGenerator();
    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\<Object\>\>()
      {
        @Override
        protected void doSpecify(final <volumeType>\<Object\> outer)
          throws Throwable
        {
          final <volumeSizeType>\<Object\> s = <volumeOpsType>.size(outer);

          <opClass>.checkEquals(outer.sizeX(), s.sizeX());
          <opClass>.checkEquals(outer.sizeY(), s.sizeY());
          <opClass>.checkEquals(outer.sizeZ(), s.sizeZ());
        }
      });
  }
}

>>