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
import com.io7m.jregions.core.parameterized.volumes.PVolumeL;
import com.io7m.jregions.core.parameterized.volumes.PVolumesL;
import com.io7m.junreachable.UnreachableCodeException;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.Assertions;

final class PVolumesLTestOps
{
  public static final long ZERO = 0L;
  public static final long ONE = 1L;

  private PVolumesLTestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final long expected,
    final long actual)
  {
    Assertions.assertEquals(expected, actual);
  }

  public static long constant(
    final String text)
  {
    return Long.parseLong(text);
  }

  public static long absoluteDifference(
    final long m,
    final long n)
  {
    return Math.abs(m - n);
  }

  public static long absolute(
    final long m)
  {
    return Math.abs(m);
  }

  public static long add(
    final long a,
    final long b)
  {
    return a + b;
  }

  public static long maximum(
    final long a,
    final long b)
  {
    return Math.max(a, b);
  }

  public static long minimum(
    final long a,
    final long b)
  {
    return Math.min(a, b);
  }

  public static long subtract(
    final long a,
    final long b)
  {
    return a - b;
  }

  public static long compare(
    final long a,
    final long b)
  {
    return (long) Long.compare(a, b);
  }

  public static long randomBetweenZeroAndLessThan(
    final long upper)
  {
    Preconditions.checkPreconditionV(
      upper >= 1L,
      "Upper %s bound must be >= 1",
      Long.valueOf(upper)
    );

    final var sc =
      Math.clamp(Math.random(), 0.0, 0.99);

    return (long) (sc * (double) upper);
  }

  public static long randomBounded(
    final long upper)
  {
    return (long) (Math.random() * (double) upper);
  }

  public static Arbitrary<Long> createWideScalarGenerator()
  {
    return Arbitraries.longs()
      .between(
        -1_000_000L,
        1_000_000L
      );
  }

  public static Arbitrary<Long> createNarrowScalarGenerator()
  {
    return Arbitraries.longs()
      .between(
        -400L,
        400L
      );
  }

  public static Arbitrary<Long> createNarrowNonNegativeScalarGenerator()
  {
    return Arbitraries.longs()
      .between(
        0L,
        400L
      );
  }

  public static Arbitrary<Long> createWideNonNegativeScalarGenerator()
  {
    return Arbitraries.longs()
      .between(
        0L,
        1_000_000L
      );
  }

  public static Arbitrary<Long> createWidePositiveScalarGenerator()
  {
    return Arbitraries.longs()
      .between(
        1L,
        1_000_000L
      );
  }

  @SuppressWarnings("unchecked")
  public static Arbitrary<PVolumeL<Object>> createGenerator()
  {
    return (Arbitrary<PVolumeL<Object>>) (Object) Arbitraries.defaultFor(PVolumeL.class);
  }

  public static Arbitrary<PVolumeL<Object>> createParameterizedGenerator(
    final Arbitrary<Long> g)
  {
    return Combinators.combine(g, g, g, g, g, g)
      .as(PVolumesL::create);
  }
}
