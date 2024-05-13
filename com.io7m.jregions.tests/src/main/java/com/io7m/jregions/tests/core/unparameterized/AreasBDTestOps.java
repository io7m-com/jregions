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

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.areas.AreasBD;
import com.io7m.junreachable.UnreachableCodeException;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.Assertions;

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
    Assertions.assertEquals(
      expected.compareTo(actual),
      0,
      String.format(
        "expected %s == actual %s",
        expected,
        actual));
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

  public static Arbitrary<BigDecimal> createWideScalarGenerator()
  {
    return Arbitraries.bigDecimals()
      .between(
        BigDecimal.valueOf(-1_000_000.0),
        BigDecimal.valueOf(1_000_000.0)
      );
  }

  public static Arbitrary<BigDecimal> createNarrowScalarGenerator()
  {
    return Arbitraries.bigDecimals()
      .between(
        BigDecimal.valueOf(-400.0),
        BigDecimal.valueOf(400.0)
      );
  }

  public static Arbitrary<BigDecimal> createNarrowNonNegativeScalarGenerator()
  {
    return Arbitraries.bigDecimals()
      .between(
        BigDecimal.valueOf(0.0),
        BigDecimal.valueOf(400.0)
      );
  }

  public static Arbitrary<BigDecimal> createWideNonNegativeScalarGenerator()
  {
    return Arbitraries.bigDecimals()
      .between(
        BigDecimal.valueOf(0.0),
        BigDecimal.valueOf(1_000_000.0)
      );
  }

  public static Arbitrary<BigDecimal> createWidePositiveScalarGenerator()
  {
    return Arbitraries.bigDecimals()
      .between(
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1_000_000.0)
      );
  }

  public static Arbitrary<AreaBD> createGenerator()
  {
    return Arbitraries.defaultFor(AreaBD.class);
  }

  public static Arbitrary<AreaBD> createParameterizedGenerator(
    final Arbitrary<BigDecimal> g)
  {
    return Combinators.combine(g, g, g, g)
      .as(AreasBD::create);
  }

  public static BigDecimal randomBetweenZeroAndLessThan(
    final BigDecimal upper)
  {
    Preconditions.checkPreconditionV(
      upper.compareTo(BigDecimal.ONE) >= 0,
      "Upper %s bound must be >= 1",
      upper
    );

    final var sc =
      Math.clamp(Math.random(), 0.0, 0.99);

    return BigDecimal.valueOf((long) (sc * upper.doubleValue()));
  }
}
