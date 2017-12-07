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

import java.util.Objects;
import com.io7m.junreachable.UnreachableCodeException;

final class VolumeLOps
{
  public static final long ZERO = 0L;

  public static final long ONE = 1L;

  private VolumeLOps()
  {
    throw new UnreachableCodeException();
  }

  public static long minimum(
    final long a,
    final long b)
  {
    return Math.min(a, b);
  }

  public static int compare(
    final long a,
    final long b)
  {
    return Long.compare(a, b);
  }

  public static long maximum(
    final long a,
    final long b)
  {
    return Math.max(a, b);
  }

  public static long add(
    final long a,
    final long b)
  {
    return Math.addExact(a, b);
  }

  public static long subtract(
    final long a,
    final long b)
  {
    return Math.subtractExact(a, b);
  }

  public static long divide(
    final long a,
    final long b)
  {
    return a / b;
  }

  public static long constant(
    final long x)
  {
    return x;
  }

  public static void notNullVolume(
    final Object expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }

  public static void notNullScalar(
    final long expression,
    final String name)
  {

  }
}
