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

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jregions.core.parameterized.areas.PAreaBI;
import com.io7m.jregions.core.parameterized.areas.PAreasBI;
import com.io7m.junreachable.UnreachableCodeException;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.Assertions;

import java.math.BigInteger;

final class PAreasBITestOps
{
  public static final BigInteger ZERO = BigInteger.ZERO;
  public static final BigInteger ONE = BigInteger.ONE;

  private PAreasBITestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final BigInteger expected,
    final BigInteger actual)
  {
    Assertions.assertEquals(
      expected.compareTo(actual),
      0,
      String.format(
        "expected %s == actual %s",
        expected,
        actual));
  }

  public static BigInteger constant(
    final String text)
  {
    return new BigInteger(text);
  }

  public static BigInteger absoluteDifference(
    final BigInteger m,
    final BigInteger n)
  {
    return (m.subtract(n)).abs();
  }

  public static BigInteger absolute(
    final BigInteger m)
  {
    return m.abs();
  }

  public static BigInteger add(
    final BigInteger a,
    final BigInteger b)
  {
    return a.add(b);
  }

  public static BigInteger maximum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.max(b);
  }

  public static BigInteger minimum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.min(b);
  }

  public static BigInteger subtract(
    final BigInteger a,
    final BigInteger b)
  {
    return a.subtract(b);
  }

  public static int compare(
    final BigInteger a,
    final BigInteger b)
  {
    return a.compareTo(b);
  }

  public static BigInteger randomBetweenZeroAndLessThan(
    final BigInteger upper)
  {
    Preconditions.checkPreconditionV(
      upper.compareTo(BigInteger.ONE) >= 0,
      "Upper %s bound must be >= 1",
      upper
    );

    final var sc =
      Math.clamp(Math.random(), 0.0, 0.99);

    return BigInteger.valueOf((long) (sc * upper.doubleValue()));
  }

  public static BigInteger randomBounded(
    final BigInteger upper)
  {
    return BigInteger.valueOf((long) (Math.random() * upper.doubleValue()));
  }

  public static Arbitrary<BigInteger> createWideScalarGenerator()
  {
    return Arbitraries.bigIntegers()
      .between(
        BigInteger.valueOf(-1_000_000L),
        BigInteger.valueOf(1_000_000L)
      );
  }

  public static Arbitrary<BigInteger> createNarrowScalarGenerator()
  {
    return Arbitraries.bigIntegers()
      .between(
        BigInteger.valueOf(-400L),
        BigInteger.valueOf(400L)
      );
  }

  public static Arbitrary<BigInteger> createNarrowNonNegativeScalarGenerator()
  {
    return Arbitraries.bigIntegers()
      .between(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(400L)
      );
  }

  public static Arbitrary<BigInteger> createWideNonNegativeScalarGenerator()
  {
    return Arbitraries.bigIntegers()
      .between(
        BigInteger.valueOf(0L),
        BigInteger.valueOf(1_000_000L)
      );
  }

  public static Arbitrary<BigInteger> createWidePositiveScalarGenerator()
  {
    return Arbitraries.bigIntegers()
      .between(
        BigInteger.valueOf(1L),
        BigInteger.valueOf(1_000_000L)
      );
  }

  @SuppressWarnings("unchecked")
  public static Arbitrary<PAreaBI<Object>> createGenerator()
  {
    return (Arbitrary<PAreaBI<Object>>) (Object) Arbitraries.defaultFor(PAreaBI.class);
  }

  public static Arbitrary<PAreaBI<Object>> createParameterizedGenerator(
    final Arbitrary<BigInteger> g)
  {
    return Combinators.combine(g, g, g, g)
      .as(PAreasBI::create);
  }
}
