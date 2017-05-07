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

import com.io7m.jregions.core.parameterized.volumes.PVolumeD;
import com.io7m.jregions.generators.PVolumeDGenerator;
import com.io7m.junreachable.UnreachableCodeException;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;

final class PVolumesDTestOps
{
  public static final double ZERO = 0.0;
  public static final double ONE = 1.0;

  private PVolumesDTestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final double expected,
    final double actual)
  {
    Assert.assertEquals(expected, actual, 0.0000001);
  }

  public static double constant(
    final String text)
  {
    return Double.parseDouble(text);
  }

  public static double absoluteDifference(
    final double m,
    final double n)
  {
    return Math.abs(m - n);
  }

  public static double absolute(
    final double m)
  {
    return Math.abs(m);
  }

  public static double add(
    final double a,
    final double b)
  {
    return a + b;
  }

  public static double maximum(
    final double a,
    final double b)
  {
    return Math.max(a, b);
  }

  public static double minimum(
    final double a,
    final double b)
  {
    return Math.min(a, b);
  }

  public static double subtract(
    final double a,
    final double b)
  {
    return a - b;
  }

  public static int compare(
    final double a,
    final double b)
  {
    return Double.compare(a, b);
  }

  public static double randomBounded(
    final double upper)
  {
    return Math.random() * upper;
  }

  public static Generator<Double> createWideScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);
    return () -> Double.valueOf(base.next().doubleValue());
  }

  public static Generator<Double> createNarrowScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    return () -> Double.valueOf(base.next().doubleValue());
  }

  public static Generator<Double> createNarrowNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 400.0);
    return () -> Double.valueOf(base.next().doubleValue());
  }

  public static Generator<Double> createWideNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 1_000_000.0);
    return () -> Double.valueOf(base.next().doubleValue());
  }

  public static Generator<PVolumeD<Object>> createGenerator()
  {
    return PVolumeDGenerator.create();
  }

  public static Generator<PVolumeD<Object>> createParameterizedGenerator(
    final Generator<Double> g)
  {
    return new PVolumeDGenerator<>(g);
  }
}
