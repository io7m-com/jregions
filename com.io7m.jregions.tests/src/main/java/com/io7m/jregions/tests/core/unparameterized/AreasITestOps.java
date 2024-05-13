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
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreasI;
import com.io7m.junreachable.UnreachableCodeException;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.Assertions;

final class AreasITestOps
{
  public static final int ZERO = 0;
  public static final int ONE = 1;

  private AreasITestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final int expected,
    final int actual)
  {
    Assertions.assertEquals(expected, actual);
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

  public static int randomBetweenZeroAndLessThan(
    final int upper)
  {
    Preconditions.checkPreconditionV(
      upper >= 1,
      "Upper %s bound must be >= 1",
      Integer.valueOf(upper)
    );

    final var sc =
      Math.clamp(Math.random(), 0.0, 0.99);

    return (int) (sc * (double) upper);
  }

  public static int randomBounded(
    final int upper)
  {
    return (int) (Math.random() * upper);
  }

  public static Arbitrary<Integer> createWideScalarGenerator()
  {
    return Arbitraries.integers()
      .between(
        -1_000_000,
        1_000_000
      );
  }

  public static Arbitrary<Integer> createNarrowScalarGenerator()
  {
    return Arbitraries.integers()
      .between(
        -400,
        400
      );
  }

  public static Arbitrary<Integer> createNarrowNonNegativeScalarGenerator()
  {
    return Arbitraries.integers()
      .between(
        0,
        400
      );
  }

  public static Arbitrary<Integer> createWideNonNegativeScalarGenerator()
  {
    return Arbitraries.integers()
      .between(
        0,
        1_000_000
      );
  }

  public static Arbitrary<Integer> createWidePositiveScalarGenerator()
  {
    return Arbitraries.integers()
      .between(
        1,
        1_000_000
      );
  }

  public static Arbitrary<AreaI> createGenerator()
  {
    return Arbitraries.defaultFor(AreaI.class);
  }

  public static Arbitrary<AreaI> createParameterizedGenerator(
    final Arbitrary<Integer> g)
  {
    return Combinators.combine(g, g, g, g)
      .as(AreasI::create);
  }
}
