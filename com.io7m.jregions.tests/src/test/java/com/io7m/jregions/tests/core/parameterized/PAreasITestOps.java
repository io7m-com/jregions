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

import com.io7m.jregions.core.parameterized.areas.PAreaI;
import com.io7m.jregions.generators.PAreaIGenerator;
import com.io7m.junreachable.UnreachableCodeException;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;

final class PAreasITestOps
{
  public static final int ZERO = 0;
  public static final int ONE = 1;

  private PAreasITestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final int expected,
    final int actual)
  {
    Assert.assertEquals(expected, actual);
  }

  public static int constant(
    final String text)
  {
    return Integer.parseInt(text);
  }

  public static int absoluteDifference(
    final int m,
    final int n)
  {
    return Math.abs(m - n);
  }

  public static int absolute(
    final int m)
  {
    return Math.abs(m);
  }

  public static int add(
    final int a,
    final int b)
  {
    return a + b;
  }

  public static int maximum(
    final int a,
    final int b)
  {
    return Math.max(a, b);
  }

  public static int minimum(
    final int a,
    final int b)
  {
    return Math.min(a, b);
  }

  public static int subtract(
    final int a,
    final int b)
  {
    return a - b;
  }

  public static int compare(
    final int a,
    final int b)
  {
    return Integer.compare(a, b);
  }

  public static int randomBounded(
    final int upper)
  {
    return (int) (Math.random() * upper);
  }

  public static Generator<Integer> createWideScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);
    return () -> Integer.valueOf(base.next().intValue());
  }

  public static Generator<Integer> createNarrowScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    return () -> Integer.valueOf(base.next().intValue());
  }

  public static Generator<Integer> createNarrowNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 400.0);
    return () -> Integer.valueOf(base.next().intValue());
  }

  public static Generator<Integer> createWideNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 1_000_000.0);
    return () -> Integer.valueOf(base.next().intValue());
  }

  public static Generator<Integer> createWidePositiveScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(1.0, 1_000_000.0);
    return () -> Integer.valueOf(base.next().intValue());
  }

  public static Generator<PAreaI<Object>> createGenerator()
  {
    return PAreaIGenerator.create();
  }

  public static Generator<PAreaI<Object>> createParameterizedGenerator(
    final Generator<Integer> g)
  {
    return new PAreaIGenerator<>(g);
  }
}
