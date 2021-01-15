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

import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.generators.AreaBDGenerator;
import com.io7m.junreachable.UnreachableCodeException;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.MathContext;

final class AreasBDTestOps
{
  public static final BigDecimal ZERO = BigDecimal.ZERO;
  public static final BigDecimal ONE = BigDecimal.ONE;

  private AreasBDTestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final BigDecimal expected,
    final BigDecimal actual)
  {
    Assert.assertTrue(
      String.format("expected %s == actual %s", expected, actual),
      expected.compareTo(actual) == 0);
  }

  public static BigDecimal constant(
    final String text)
  {
    return new BigDecimal(text, MathContext.UNLIMITED);
  }

  public static BigDecimal absoluteDifference(
    final BigDecimal m,
    final BigDecimal n)
  {
    return (m.subtract(n)).abs();
  }

  public static BigDecimal absolute(
    final BigDecimal m)
  {
    return m.abs();
  }

  public static BigDecimal add(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.add(b);
  }

  public static BigDecimal maximum(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.max(b);
  }

  public static BigDecimal minimum(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.min(b);
  }

  public static BigDecimal subtract(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.subtract(b);
  }

  public static int compare(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.compareTo(b);
  }

  public static BigDecimal randomBounded(
    final BigDecimal upper)
  {
    return BigDecimal.valueOf(Math.random() * upper.doubleValue());
  }

  public static Generator<BigDecimal> createWideScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-1_000_000.0, 1_000_000.0);
    return () -> BigDecimal.valueOf(base.next().doubleValue());
  }

  public static Generator<BigDecimal> createNarrowScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(-400.0, 400.0);
    return () -> BigDecimal.valueOf(base.next().doubleValue());
  }

  public static Generator<BigDecimal> createNarrowNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 400.0);
    return () -> BigDecimal.valueOf(base.next().doubleValue());
  }

  public static Generator<BigDecimal> createWideNonNegativeScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(0.0, 1_000_000.0);
    return () -> BigDecimal.valueOf(base.next().doubleValue());
  }

  public static Generator<BigDecimal> createWidePositiveScalarGenerator()
  {
    final Generator<Double> base =
      PrimitiveGenerators.doubles(1.0, 1_000_000.0);
    return () -> BigDecimal.valueOf(base.next().doubleValue());
  }

  public static Generator<AreaBD> createGenerator()
  {
    return AreaBDGenerator.create();
  }

  public static Generator<AreaBD> createParameterizedGenerator(
    final Generator<BigDecimal> g)
  {
    return new AreaBDGenerator(g);
  }
}
