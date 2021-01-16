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

package com.io7m.jregions.core.unparameterized.areas;

import com.io7m.junreachable.UnreachableCodeException;

import java.math.BigInteger;
import java.util.Objects;

final class AreaBIOps
{
  public static final BigInteger ZERO = BigInteger.ZERO;

  public static final BigInteger ONE = BigInteger.ONE;

  private AreaBIOps()
  {
    throw new UnreachableCodeException();
  }

  public static BigInteger minimum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.min(b);
  }

  public static int compare(
    final BigInteger a,
    final BigInteger b)
  {
    return a.compareTo(b);
  }

  public static BigInteger maximum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.max(b);
  }

  public static BigInteger add(
    final BigInteger a,
    final BigInteger b)
  {
    return a.add(b);
  }

  public static BigInteger subtract(
    final BigInteger a,
    final BigInteger b)
  {
    return a.subtract(b);
  }

  public static BigInteger divide(
    final BigInteger a,
    final BigInteger b)
  {
    return a.divide(b);
  }

  public static BigInteger constant(
    final long x)
  {
    return BigInteger.valueOf(x);
  }

  public static void notNullArea(
    final Object expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }

  public static void notNullScalar(
    final BigInteger expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }
}
