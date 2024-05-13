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

import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumesD;
import com.io7m.junreachable.UnreachableCodeException;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.Assertions;

final class VolumesDTestOps
{
  public static final double ZERO = 0.0;
  public static final double ONE = 1.0;

  private VolumesDTestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final double expected,
    final double actual)
  {
    Assertions.assertEquals(expected, actual, 0.0000001);
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

  public static double randomBetweenZeroAndLessThan(
    final double upper)
  {
    final var sc =
      Math.clamp(Math.random(), 0.0, 0.99);

    return sc * Math.abs(upper);
  }

  public static double randomBounded(
    final double upper)
  {
    return Math.random() * upper;
  }

  public static Arbitrary<Double> createWideScalarGenerator()
  {
    return Arbitraries.doubles()
      .between(
        -1_000_000.0,
        1_000_000.0
      );
  }

  public static Arbitrary<Double> createNarrowScalarGenerator()
  {
    return Arbitraries.doubles()
      .between(
        -400.0,
        400.0
      );
  }

  public static Arbitrary<Double> createNarrowNonNegativeScalarGenerator()
  {
    return Arbitraries.doubles()
      .between(
        0.0,
        400.0
      );
  }

  public static Arbitrary<Double> createWideNonNegativeScalarGenerator()
  {
    return Arbitraries.doubles()
      .between(
        0.0,
        1_000_000.0
      );
  }

  public static Arbitrary<Double> createWidePositiveScalarGenerator()
  {
    return Arbitraries.doubles()
      .between(
        1.0,
        1_000_000.0
      );
  }

  public static Arbitrary<VolumeD> createGenerator()
  {
    return Arbitraries.defaultFor(VolumeD.class);
  }

  public static Arbitrary<VolumeD> createParameterizedGenerator(
    final Arbitrary<Double> g)
  {
    return Combinators.combine(g, g, g, g, g, g)
      .as(VolumesD::create);
  }
}
