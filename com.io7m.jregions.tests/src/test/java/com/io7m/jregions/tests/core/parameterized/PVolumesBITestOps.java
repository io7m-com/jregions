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

import com.io7m.jregions.core.parameterized.volumes.PVolumeBI;
import com.io7m.jregions.generators.PVolumeBIGenerator;
import com.io7m.junreachable.UnreachableCodeException;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import org.junit.Assert;

import java.math.BigInteger;

final class PVolumesBITestOps
{
  public static final BigInteger ZERO = BigInteger.ZERO;
  public static final BigInteger ONE = BigInteger.ONE;

  private PVolumesBITestOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final BigInteger expected,
    final BigInteger actual)
  {
    Assert.assertTrue(
      String.format("expected %s == actual %s", expected, actual),
      expected.compareTo(actual) == 0);
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

  public static BigInteger randomBounded(
    final BigInteger upper)
  {
    return BigInteger.valueOf((long) (Math.random() * upper.doubleValue()));
  }

  public static Generator<BigInteger> createWideScalarGenerator()
  {
    final Generator<Long> base =
      PrimitiveGenerators.longs(-1_000_000L, 1_000_000L);
    return () -> BigInteger.valueOf(base.next().longValue());
  }

  public static Generator<BigInteger> createNarrowScalarGenerator()
  {
    final Generator<Long> base =
      PrimitiveGenerators.longs(-400L, 400L);
    return () -> BigInteger.valueOf(base.next().longValue());
  }

  public static Generator<BigInteger> createNarrowNonNegativeScalarGenerator()
  {
    final Generator<Long> base =
      PrimitiveGenerators.longs(0L, 400L);
    return () -> BigInteger.valueOf(base.next().longValue());
  }

  public static Generator<BigInteger> createWideNonNegativeScalarGenerator()
  {
    final Generator<Long> base =
      PrimitiveGenerators.longs(0L, 1_000_000L);
    return () -> BigInteger.valueOf(base.next().longValue());
  }

  public static Generator<PVolumeBI<Object>> createGenerator()
  {
    return PVolumeBIGenerator.create();
  }

  public static Generator<PVolumeBI<Object>> createParameterizedGenerator(
    final Generator<BigInteger> g)
  {
    return new PVolumeBIGenerator<>(g);
  }
}
