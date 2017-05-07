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

package com.io7m.jregions.tests.core.unparameterized;

import com.io7m.jregions.core.unparameterized.volumes.VolumeF;
import com.io7m.jregions.generators.VolumeFGenerator;
import com.io7m.junreachable.UnreachableCodeException;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;

final class VolumesFTestOps
{
  public static final float ZERO = 0.0f;
  public static final float ONE = 1.0f;

  private VolumesFTestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final float expected,
    final float actual)
  {
    // Single precision floats are subject to quite serious precision problems
    Assert.assertEquals(expected, actual, 0.1f);
  }

  public static float constant(
    final String text)
  {
    return Float.parseFloat(text);
  }

  public static float absoluteDifference(
    final float m,
    final float n)
  {
    return Math.abs(m - n);
  }

  public static float absolute(
    final float m)
  {
    return Math.abs(m);
  }

  public static float add(
    final float a,
    final float b)
  {
    return a + b;
  }

  public static float maximum(
    final float a,
    final float b)
  {
    return Math.max(a, b);
  }

  public static float minimum(
    final float a,
    final float b)
  {
    return Math.min(a, b);
  }

  public static float subtract(
    final float a,
    final float b)
  {
    return a - b;
  }

  public static int compare(
    final float a,
    final float b)
  {
    return Float.compare(a, b);
  }

  public static float randomBounded(
    final float upper)
  {
    return (float) (Math.random() * upper);
  }

  public static Generator<Float> createWideScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);
    return () -> Float.valueOf(base.next().floatValue());
  }

  public static Generator<Float> createNarrowScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    return () -> Float.valueOf(base.next().floatValue());
  }

  public static Generator<Float> createNarrowNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 400.0);
    return () -> Float.valueOf(base.next().floatValue());
  }

  public static Generator<Float> createWideNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 1_000_000.0);
    return () -> Float.valueOf(base.next().floatValue());
  }

  public static Generator<VolumeF> createGenerator()
  {
    return VolumeFGenerator.create();
  }

  public static Generator<VolumeF> createParameterizedGenerator(
    final Generator<Float> g)
  {
    return new VolumeFGenerator(g);
  }
}
