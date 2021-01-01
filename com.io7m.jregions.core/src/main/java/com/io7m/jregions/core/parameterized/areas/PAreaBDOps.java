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

package com.io7m.jregions.core.parameterized.areas;

import com.io7m.junreachable.UnreachableCodeException;

import java.math.BigDecimal;
import java.util.Objects;

final class PAreaBDOps
{
  public static final BigDecimal ZERO = BigDecimal.ZERO;

  public static final BigDecimal ONE = BigDecimal.ONE;

  private PAreaBDOps()
  {
    throw new UnreachableCodeException();
  }

  public static BigDecimal minimum(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.min(b);
  }

  public static int compare(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.compareTo(b);
  }

  public static BigDecimal maximum(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.max(b);
  }

  public static BigDecimal add(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.add(b);
  }

  public static BigDecimal subtract(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.subtract(b);
  }

  public static BigDecimal divide(
    final BigDecimal a,
    final BigDecimal b)
  {
    return a.divide(b);
  }

  public static BigDecimal constant(
    final long x)
  {
    return BigDecimal.valueOf(x);
  }

  public static void notNullArea(
    final Object expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }

  public static void notNullScalar(
    final BigDecimal expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }
}
