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

package com.io7m.jregions.core.unparameterized.volumes;

import com.io7m.junreachable.UnreachableCodeException;

import java.util.Objects;

final class VolumeDOps
{
  public static final double ZERO = 0.0;

  public static final double ONE = 1.0;

  private VolumeDOps()
  {
    throw new UnreachableCodeException();
  }

  public static double minimum(
    final double a,
    final double b)
  {
    return Math.min(a, b);
  }

  public static int compare(
    final double a,
    final double b)
  {
    return Double.compare(a, b);
  }

  public static double maximum(
    final double a,
    final double b)
  {
    return Math.max(a, b);
  }

  public static double add(
    final double a,
    final double b)
  {
    return a + b;
  }

  public static double subtract(
    final double a,
    final double b)
  {
    return a - b;
  }

  public static double constant(
    final long x)
  {
    return (double) x;
  }

  public static void notNullVolume(
    final Object expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }

  public static void notNullScalar(
    final double expression,
    final String name)
  {

  }
}
