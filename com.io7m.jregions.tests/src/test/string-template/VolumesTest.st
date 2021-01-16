VolumesTest(
  scalarType,
  scalarGeneratorType,
  volumeType,
  volumeSizeType,
  volumeOpsType,
  volumeXYZSplitType,
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

package com.io7m.jregions.tests.core.unparameterized;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jaffirm.core.PreconditionViolationException;
import java.util.Objects;
import com.io7m.junreachable.UnreachableCodeException;

import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;

import com.io7m.jregions.tests.percentage_pass.PercentPassing;
import com.io7m.jregions.tests.percentage_pass.PercentPassExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Objects;

@ExtendWith(PercentPassExtension.class)
public final class <className>
{
  @Test
  public void testCouldFitInsideFalseSpecificX0()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.constant("-1"), <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificX1()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("11"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificY0()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-1"), <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificY1()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("11"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificZ0()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-1"), <opClass>.constant("10"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @Test
  public void testCouldFitInsideFalseSpecificZ1()
    throws Exception
  {
    final <volumeType> v0 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("11"));

    final <volumeType> v1 =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.couldFitInside(v0, v1));
  }

  @PercentPassing
  public void testCreateAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    final <volumeType> c =
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

  @PercentPassing
  public void testContainsSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();

    QuickCheck.forAll(
      generator,
      new AbstractCharacteristic\<<volumeType>\>()
      {
        @Override
        protected void doSpecify(final <volumeType> outer)
          throws Throwable
        {
          Assertions.assertTrue(<volumeOpsType>.contains(outer, outer));
        }
      });
  }

  @Test
  public void testContainsFalseSpecific()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <volumeType> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"));

    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_x_lo));
    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_x_hi));
    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_y_lo));
    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_y_hi));
    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_z_lo));
    Assertions.assertFalse(<volumeOpsType>.contains(volume, out_z_hi));

    Assertions.assertFalse(<volumeOpsType>.contains(out_x_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.contains(out_x_hi, volume));
    Assertions.assertFalse(<volumeOpsType>.contains(out_y_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.contains(out_y_hi, volume));
    Assertions.assertFalse(<volumeOpsType>.contains(out_z_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.contains(out_z_hi, volume));
  }

  @PercentPassing
  public void testMoveRelative()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();

    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    final <scalarType> x = gen.next();
    final <scalarType> y = gen.next();
    final <scalarType> z = gen.next();

    final <volumeType> moved = <volumeOpsType>.moveRelative(outer, x, y, z);

    <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
    <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
    <opClass>.checkEquals(outer.sizeZ(), moved.sizeZ());
    <opClass>.checkEquals(<opClass>.absolute(x), <opClass>.absoluteDifference(outer.minimumX(), moved.minimumX()));
    <opClass>.checkEquals(<opClass>.absolute(y), <opClass>.absoluteDifference(outer.minimumY(), moved.minimumY()));
    <opClass>.checkEquals(<opClass>.absolute(z), <opClass>.absoluteDifference(outer.minimumZ(), moved.minimumZ()));
  }

  @PercentPassing
  public void testMoveRelativeClampedX()
    throws Exception
  {
    final var sGen =
      <opClass>.createWideScalarGenerator();
    final var gen =
      <opClass>.createWidePositiveScalarGenerator();
    final var generator =
      <opClass>.createGenerator();
    final var inner =
      generator.next();

    final var x0off = gen.next();
    final var x1off = gen.next();
    final var y0off = gen.next();
    final var y1off = gen.next();
    final var z0off = gen.next();
    final var z1off = gen.next();

    final var outer =
      <volumeType>.of(
        <opClass>.subtract(inner.minimumX(), x0off),
        <opClass>.add(inner.maximumX(), x1off),
        <opClass>.subtract(inner.minimumY(), y0off),
        <opClass>.add(inner.maximumY(), y1off),
        <opClass>.subtract(inner.minimumZ(), z0off),
        <opClass>.add(inner.maximumZ(), z1off)
      );

    System.out.println("outer " + outer);
    System.out.println("inner " + inner);
    Assertions.assertTrue(<volumeOpsType>.contains(outer, inner));

    final var x = sGen.next();
    final var moved = <volumeOpsType>.moveRelativeClampedX(outer, inner, x);

    <opClass>.checkEquals(inner.sizeX(), moved.sizeX());
    <opClass>.checkEquals(inner.sizeY(), moved.sizeY());
    <opClass>.checkEquals(inner.sizeZ(), moved.sizeZ());

    Assertions.assertTrue(<volumeOpsType>.contains(outer, moved));
  }

  @PercentPassing
  public void testMoveRelativeClampedY()
    throws Exception
  {
    final var sGen =
      <opClass>.createWideScalarGenerator();
    final var gen =
      <opClass>.createWidePositiveScalarGenerator();
    final var generator =
      <opClass>.createGenerator();
    final var inner =
      generator.next();

    final var x0off = gen.next();
    final var x1off = gen.next();
    final var y0off = gen.next();
    final var y1off = gen.next();
    final var z0off = gen.next();
    final var z1off = gen.next();

    final var outer =
      <volumeType>.of(
        <opClass>.subtract(inner.minimumX(), x0off),
        <opClass>.add(inner.maximumX(), x1off),
        <opClass>.subtract(inner.minimumY(), y0off),
        <opClass>.add(inner.maximumY(), y1off),
        <opClass>.subtract(inner.minimumZ(), z0off),
        <opClass>.add(inner.maximumZ(), z1off)
      );

    System.out.println("outer " + outer);
    System.out.println("inner " + inner);
    Assertions.assertTrue(<volumeOpsType>.contains(outer, inner));

    final var y = sGen.next();
    final var moved = <volumeOpsType>.moveRelativeClampedY(outer, inner, y);

    <opClass>.checkEquals(inner.sizeX(), moved.sizeX());
    <opClass>.checkEquals(inner.sizeY(), moved.sizeY());
    <opClass>.checkEquals(inner.sizeZ(), moved.sizeZ());

    Assertions.assertTrue(<volumeOpsType>.contains(outer, moved));
  }

  @PercentPassing
  public void testMoveRelativeClampedZ()
    throws Exception
  {
    final var sGen =
      <opClass>.createWideScalarGenerator();
    final var gen =
      <opClass>.createWidePositiveScalarGenerator();
    final var generator =
      <opClass>.createGenerator();
    final var inner =
      generator.next();

    final var x0off = gen.next();
    final var x1off = gen.next();
    final var y0off = gen.next();
    final var y1off = gen.next();
    final var z0off = gen.next();
    final var z1off = gen.next();

    final var outer =
      <volumeType>.of(
        <opClass>.subtract(inner.minimumX(), x0off),
        <opClass>.add(inner.maximumX(), x1off),
        <opClass>.subtract(inner.minimumY(), y0off),
        <opClass>.add(inner.maximumY(), y1off),
        <opClass>.subtract(inner.minimumZ(), z0off),
        <opClass>.add(inner.maximumZ(), z1off)
      );

    System.out.println("outer " + outer);
    System.out.println("inner " + inner);
    Assertions.assertTrue(<volumeOpsType>.contains(outer, inner));

    final var z = sGen.next();
    final var moved = <volumeOpsType>.moveRelativeClampedZ(outer, inner, z);

    <opClass>.checkEquals(inner.sizeX(), moved.sizeX());
    <opClass>.checkEquals(inner.sizeY(), moved.sizeY());
    <opClass>.checkEquals(inner.sizeZ(), moved.sizeZ());

    Assertions.assertTrue(<volumeOpsType>.contains(outer, moved));
  }

  @PercentPassing
  public void testMoveRelativeClamped()
    throws Exception
  {
    final var sGen =
      <opClass>.createWideScalarGenerator();
    final var gen =
      <opClass>.createWidePositiveScalarGenerator();
    final var generator =
      <opClass>.createGenerator();
    final var inner =
      generator.next();

    final var x0off = gen.next();
    final var x1off = gen.next();
    final var y0off = gen.next();
    final var y1off = gen.next();
    final var z0off = gen.next();
    final var z1off = gen.next();

    final var outer =
      <volumeType>.of(
        <opClass>.subtract(inner.minimumX(), x0off),
        <opClass>.add(inner.maximumX(), x1off),
        <opClass>.subtract(inner.minimumY(), y0off),
        <opClass>.add(inner.maximumY(), y1off),
        <opClass>.subtract(inner.minimumZ(), z0off),
        <opClass>.add(inner.maximumZ(), z1off)
      );

    System.out.println("outer " + outer);
    System.out.println("inner " + inner);
    Assertions.assertTrue(<volumeOpsType>.contains(outer, inner));

    final var x = sGen.next();
    final var y = sGen.next();
    final var z = sGen.next();
    final var moved = <volumeOpsType>.moveRelativeClamped(outer, inner, x, y, z);

    <opClass>.checkEquals(inner.sizeX(), moved.sizeX());
    <opClass>.checkEquals(inner.sizeY(), moved.sizeY());
    <opClass>.checkEquals(inner.sizeZ(), moved.sizeZ());

    Assertions.assertTrue(<volumeOpsType>.contains(outer, moved));
  }

  @PercentPassing
  public void testMoveAbsolute()
    throws Exception
  {
    final <scalarGeneratorType> gen = <opClass>.createWideScalarGenerator();
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    final <scalarType> x = gen.next();
    final <scalarType> y = gen.next();
    final <scalarType> z = gen.next();
    final <volumeType> moved = <volumeOpsType>.moveAbsolute(outer, x, y, z);

    <opClass>.checkEquals(outer.sizeX(), moved.sizeX());
    <opClass>.checkEquals(outer.sizeY(), moved.sizeY());
    <opClass>.checkEquals(outer.sizeZ(), moved.sizeZ());
    <opClass>.checkEquals(x, moved.minimumX());
    <opClass>.checkEquals(y, moved.minimumY());
    <opClass>.checkEquals(z, moved.minimumZ());
  }

  @PercentPassing
  public void testMoveToOrigin()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    final <volumeType> moved = <volumeOpsType>.moveToOrigin(outer);

    <opClass>.checkEquals(outer.sizeX(),  moved.sizeX());
    <opClass>.checkEquals(outer.sizeY(),  moved.sizeY());
    <opClass>.checkEquals(outer.sizeZ(),  moved.sizeZ());
    <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumX());
    <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumY());
    <opClass>.checkEquals(<opClass>.ZERO,  moved.minimumZ());
  }

  @PercentPassing
  public void testEqualsHashcode()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> volume = generator.next();

    final <volumeType> other =
      <volumeType>.of(
        volume.minimumX(),
        volume.maximumX(),
        volume.minimumY(),
        volume.maximumY(),
        volume.minimumZ(),
        volume.maximumZ());
    Assertions.assertEquals(volume, volume);
    Assertions.assertEquals(volume, other);
    Assertions.assertEquals(other, volume);
    Assertions.assertEquals((long) volume.hashCode(), (long) other.hashCode());

    Assertions.assertNotEquals(volume, null);
    Assertions.assertNotEquals(volume, Integer.valueOf(23));
  }

  @PercentPassing
  public void testShowAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> any = generator.next();

    Assertions.assertEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(any));

    final <volumeType> next = generator.next();
    if (!Objects.equals(next, any)) {
      Assertions.assertNotEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(next));
    } else {
      Assertions.assertEquals(<volumeOpsType>.show(any), <volumeOpsType>.show(next));
    }
  }

  @Test
  public void testZero()
    throws Exception
  {
    final <volumeType> volume = <volumeType>.of(
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
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("-1"));
    final <volumeType> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("11"), <opClass>.constant("21"));

    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_x_lo));
    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_x_hi));
    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_y_lo));
    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_y_hi));
    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_z_lo));
    Assertions.assertFalse(<volumeOpsType>.overlaps(volume, out_z_hi));

    Assertions.assertFalse(<volumeOpsType>.overlaps(out_x_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.overlaps(out_x_hi, volume));
    Assertions.assertFalse(<volumeOpsType>.overlaps(out_y_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.overlaps(out_y_hi, volume));
    Assertions.assertFalse(<volumeOpsType>.overlaps(out_z_lo, volume));
    Assertions.assertFalse(<volumeOpsType>.overlaps(out_z_hi, volume));
  }

  @Test
  public void testOverlapsTrueSpecific()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_x_lo =
      <volumeType>.of(
        <opClass>.constant("-10"), <opClass>.constant("1"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_x_hi =
      <volumeType>.of(
        <opClass>.constant("9"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_y_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("1"),
        <opClass>.ZERO, <opClass>.constant("10"));
    final <volumeType> out_y_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("9"), <opClass>.constant("21"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <volumeType> out_z_lo =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("-10"), <opClass>.constant("1"));
    final <volumeType> out_z_hi =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.constant("9"), <opClass>.constant("21"));

    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_x_lo));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_x_hi));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_y_lo));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_y_hi));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_z_lo));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, out_z_hi));

    Assertions.assertTrue(<volumeOpsType>.overlaps(out_x_lo, volume));
    Assertions.assertTrue(<volumeOpsType>.overlaps(out_x_hi, volume));
    Assertions.assertTrue(<volumeOpsType>.overlaps(out_y_lo, volume));
    Assertions.assertTrue(<volumeOpsType>.overlaps(out_y_hi, volume));
    Assertions.assertTrue(<volumeOpsType>.overlaps(out_z_lo, volume));
    Assertions.assertTrue(<volumeOpsType>.overlaps(out_z_hi, volume));
  }

  @PercentPassing
  public void testOverlapsSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, outer));
  }

  @PercentPassing
  public void testCouldFitInsideSelfAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

    Assertions.assertTrue(<volumeOpsType>.couldFitInside(outer, outer));
  }

  @PercentPassing
  public void testContainsPointAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

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

    boolean sizeY_over = <opClass>.compare(outer.sizeY(), <opClass>.ZERO) > 0;
    boolean sizeX_over = <opClass>.compare(outer.sizeX(), <opClass>.ZERO) > 0;
    boolean sizeZ_over = <opClass>.compare(outer.sizeZ(), <opClass>.ZERO) > 0;

    if (sizeX_over && sizeY_over && sizeZ_over) {
      Assertions.assertTrue(<volumeOpsType>.containsPoint(outer, ppx, ppy, ppz));
    } else {
      Assertions.assertFalse(<volumeOpsType>.containsPoint(outer, ppx, ppy, ppz));
    }
  }

  @PercentPassing
  public void testContainingAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> a = generator.next();
    final <volumeType> b = generator.next();
    final <volumeType> c = <volumeOpsType>.containing(a, b);
    Assertions.assertTrue(<opClass>.compare(c.sizeX(), a.sizeX()) >= 0);
    Assertions.assertTrue(<opClass>.compare(c.sizeY(), a.sizeY()) >= 0);
    Assertions.assertTrue(<opClass>.compare(c.sizeZ(), a.sizeZ()) >= 0);
    Assertions.assertTrue(<opClass>.compare(c.sizeX(), b.sizeX()) >= 0);
    Assertions.assertTrue(<opClass>.compare(c.sizeY(), b.sizeY()) >= 0);
    Assertions.assertTrue(<opClass>.compare(c.sizeZ(), b.sizeZ()) >= 0);
    Assertions.assertTrue(<volumeOpsType>.contains(c, a));
    Assertions.assertTrue(<volumeOpsType>.contains(c, b));
  }

  @PercentPassing
  public void testContainsZeroSizeX()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

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

    final <volumeType> scaled = <volumeOpsType>.create(
      outer.minimumX(),
      outer.minimumY(),
      outer.minimumZ(),
      <opClass>.ZERO,
      outer.sizeY(),
      outer.sizeZ());

    System.out.println(ppx + " " + ppy);

    Assertions.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
  }

  @PercentPassing
  public void testContainsZeroSizeY()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

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

    final <volumeType> scaled = <volumeOpsType>.create(
      outer.minimumX(),
      outer.minimumY(),
      outer.minimumZ(),
      outer.sizeX(),
      <opClass>.ZERO,
      outer.sizeZ());

    System.out.println(ppx + " " + ppy + " " + ppz);

    Assertions.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
  }

  @PercentPassing
  public void testContainsZeroSizeZ()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();

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

    final <volumeType> scaled = <volumeOpsType>.create(
      outer.minimumX(),
      outer.minimumY(),
      outer.minimumZ(),
      outer.sizeX(),
      outer.sizeY(),
      <opClass>.ZERO);

    System.out.println(ppx + " " + ppy + " " + ppz);

    Assertions.assertFalse(<volumeOpsType>.containsPoint(scaled, ppx, ppy, ppz));
  }

  @Test
  public void testContainsFalseSpecificX0()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("-1"), <opClass>.constant("5"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificX1()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("10"), <opClass>.constant("5"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificY0()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("-1"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificY1()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("10"), <opClass>.constant("5")));
  }

  @Test
  public void testContainsFalseSpecificZ0()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("5"), <opClass>.constant("-1")));
  }

  @Test
  public void testContainsFalseSpecificZ1()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    Assertions.assertFalse(<volumeOpsType>.containsPoint(volume, <opClass>.constant("5"), <opClass>.constant("5"), <opClass>.constant("10")));
  }

  @PercentPassing
  public void testSize()
    throws Exception
  {
    final Generator\<<volumeType>\> generator = <opClass>.createGenerator();
    final <volumeType> outer = generator.next();
    final <volumeSizeType> s = <volumeOpsType>.size(outer);

    <opClass>.checkEquals(outer.sizeX(), s.sizeX());
    <opClass>.checkEquals(outer.sizeY(), s.sizeY());
    <opClass>.checkEquals(outer.sizeZ(), s.sizeZ());
  }

  @PercentPassing
  public void testSplitAtXYZAll()
    throws Exception
  {
    final Generator\<<volumeType>\> generator =
      <opClass>.createParameterizedGenerator(<opClass>.createWideNonNegativeScalarGenerator());

    final <volumeType> outer = generator.next();
    final <scalarType> x_bound = <opClass>.add(outer.sizeX(), <opClass>.constant("1"));
    final <scalarType> y_bound = <opClass>.add(outer.sizeY(), <opClass>.constant("1"));
    final <scalarType> z_bound = <opClass>.add(outer.sizeZ(), <opClass>.constant("1"));
    final <scalarType> x = <opClass>.randomBounded(x_bound);
    final <scalarType> y = <opClass>.randomBounded(y_bound);
    final <scalarType> z = <opClass>.randomBounded(z_bound);

    System.out.println("outer: " + outer);
    System.out.println("x:     " + x);
    System.out.println("y:     " + y);
    System.out.println("z:     " + z);

    final <volumeXYZSplitType>\<<volumeType>\> s =
      <volumeOpsType>.splitAtXYZ(outer, x, y, z);

    System.out.println("octants x0y0z0: " + s.x0y0z0());
    System.out.println("octants x1y0z0: " + s.x1y0z0());
    System.out.println("octants x0y1z0: " + s.x0y1z0());
    System.out.println("octants x1y1z0: " + s.x1y1z0());
    System.out.println("octants x0y0z1: " + s.x0y0z1());
    System.out.println("octants x1y0z1: " + s.x1y0z1());
    System.out.println("octants x0y1z1: " + s.x0y1z1());
    System.out.println("octants x1y1z1: " + s.x1y1z1());

    Assertions.assertTrue(<opClass>.compare(s.x0y0z0().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z0().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z0().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z0().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y0z1().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z1().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z1().sizeX(), outer.sizeX()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z1().sizeX(), outer.sizeX()) \<= 0);

    Assertions.assertTrue(<opClass>.compare(s.x0y0z0().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z0().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z0().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z0().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y0z1().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z1().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z1().sizeY(), outer.sizeY()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z1().sizeY(), outer.sizeY()) \<= 0);

    Assertions.assertTrue(<opClass>.compare(s.x0y0z0().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z0().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z0().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z0().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y0z1().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y0z1().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x0y1z1().sizeZ(), outer.sizeZ()) \<= 0);
    Assertions.assertTrue(<opClass>.compare(s.x1y1z1().sizeZ(), outer.sizeZ()) \<= 0);

    <opClass>.checkEquals(s.x0y0z0().minimumX(), outer.minimumX());
    <opClass>.checkEquals(s.x0y0z0().minimumY(), outer.minimumY());
    <opClass>.checkEquals(s.x0y0z0().minimumZ(), outer.minimumZ());
    <opClass>.checkEquals(s.x0y0z0().maximumX(), <opClass>.add(s.x0y0z0().minimumX(), x));
    <opClass>.checkEquals(s.x0y0z0().maximumY(), <opClass>.add(s.x0y0z0().minimumY(), y));
    <opClass>.checkEquals(s.x0y0z0().maximumZ(), <opClass>.add(s.x0y0z0().minimumZ(), z));

    <opClass>.checkEquals(s.x1y0z0().minimumX(), <opClass>.add(s.x0y0z0().minimumX(), x));
    <opClass>.checkEquals(s.x1y0z0().minimumY(), outer.minimumY());
    <opClass>.checkEquals(s.x1y0z0().minimumZ(), outer.minimumZ());
    <opClass>.checkEquals(s.x1y0z0().maximumX(), outer.maximumX());
    <opClass>.checkEquals(s.x1y0z0().maximumY(), <opClass>.add(s.x0y0z0().minimumY(), y));
    <opClass>.checkEquals(s.x1y0z0().maximumZ(), <opClass>.add(s.x0y0z0().minimumZ(), z));

    <opClass>.checkEquals(s.x0y1z0().minimumX(), outer.minimumX());
    <opClass>.checkEquals(s.x0y1z0().minimumY(), <opClass>.add(s.x0y0z0().minimumY(), y));
    <opClass>.checkEquals(s.x0y1z0().minimumZ(), outer.minimumZ());
    <opClass>.checkEquals(s.x0y1z0().maximumX(), <opClass>.add(s.x0y0z0().minimumX(), x));
    <opClass>.checkEquals(s.x0y1z0().maximumY(), outer.maximumY());
    <opClass>.checkEquals(s.x0y1z0().maximumZ(), <opClass>.add(s.x0y0z0().minimumZ(), z));

    <opClass>.checkEquals(s.x1y1z0().minimumX(), <opClass>.add(s.x0y0z0().minimumX(), x));
    <opClass>.checkEquals(s.x1y1z0().minimumY(), <opClass>.add(s.x0y0z0().minimumY(), y));
    <opClass>.checkEquals(s.x1y1z0().minimumZ(), outer.minimumZ());
    <opClass>.checkEquals(s.x1y1z0().maximumX(), outer.maximumX());
    <opClass>.checkEquals(s.x1y1z0().maximumY(), outer.maximumY());
    <opClass>.checkEquals(s.x1y1z0().maximumZ(), <opClass>.add(s.x0y0z0().minimumZ(), z));

    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x0y0z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x1y0z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x0y1z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x1y1z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x0y0z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x1y0z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x0y1z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(outer, s.x1y1z1()));

    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x0y0z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x1y0z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x0y1z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x1y1z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x0y0z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x1y0z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x0y1z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(outer, s.x1y1z1()));

    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y0z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y1z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y1z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y0z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y0z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y1z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y1z1()));
  }

  @Test
  public void testSplitAtXYZSpecific()
    throws Exception
  {
    final <volumeType> volume =
      <volumeType>.of(
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"),
        <opClass>.ZERO, <opClass>.constant("10"));

    final <scalarType> x = <opClass>.constant("5");
    final <scalarType> y = <opClass>.constant("5");
    final <scalarType> z = <opClass>.constant("5");

    final <volumeXYZSplitType>\<<volumeType>\> s =
      <volumeOpsType>.splitAtXYZ(volume, x, y, z);

    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y0z0().minimumX());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y0z0().minimumY());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y0z0().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().maximumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().minimumX());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x1y0z0().minimumY());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x1y0z0().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y0z0().maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().maximumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y1z0().minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().minimumY());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y1z0().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x0y1z0().maximumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().minimumY());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x1y1z0().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y1z0().maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y1z0().maximumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().maximumZ());


    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y0z1().minimumX());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y0z1().minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().maximumY());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x0y0z1().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().minimumX());
    <opClass>.checkEquals(<opClass>.constant("0"), s.x1y0z1().minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y0z1().maximumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().maximumY());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y0z1().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("0"), s.x0y1z1().minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x0y1z1().maximumY());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x0y1z1().maximumZ());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().minimumX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().minimumY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().minimumZ());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y1z1().maximumX());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y1z1().maximumY());
    <opClass>.checkEquals(<opClass>.constant("10"), s.x1y1z1().maximumZ());


    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().sizeX());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().sizeX());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().sizeY());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().sizeY());

    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z0().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z0().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z0().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z0().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y0z1().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y0z1().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x0y1z1().sizeZ());
    <opClass>.checkEquals(<opClass>.constant("5"), s.x1y1z1().sizeZ());

    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x0y0z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x1y0z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x0y1z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x1y1z0()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x0y0z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x1y0z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x0y1z1()));
    Assertions.assertTrue(<volumeOpsType>.overlaps(volume, s.x1y1z1()));

    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x0y0z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x1y0z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x0y1z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x1y1z0()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x0y0z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x1y0z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x0y1z1()));
    Assertions.assertTrue(<volumeOpsType>.contains(volume, s.x1y1z1()));

    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y0z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y1z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y1z0()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y0z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y0z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x0y1z1()));
    Assertions.assertFalse(<volumeOpsType>.overlaps(s.x0y0z0(), s.x1y1z1()));
  }
}

>>