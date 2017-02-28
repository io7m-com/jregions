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

package com.io7m.jregions.core.parameterized;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

final class PAreaFOps
{
  public static final float ZERO = 0.0f;

  private PAreaFOps()
  {
    throw new UnreachableCodeException();
  }

  public static float minimum(
    final float a,
    final float b)
  {
    return Math.min(a, b);
  }

  public static int compare(
    final float a,
    final float b)
  {
    return Float.compare(a, b);
  }

  public static float maximum(
    final float a,
    final float b)
  {
    return Math.max(a, b);
  }

  public static float add(
    final float a,
    final float b)
  {
    return a + b;
  }

  public static float subtract(
    final float a,
    final float b)
  {
    return a - b;
  }

  public static float divide(
    final float a,
    final float b)
  {
    return a / b;
  }

  public static float constant(
    final long x)
  {
    return (float) x;
  }

  public static void notNullArea(
    final Object expression,
    final String name)
  {
    NullCheck.notNull(expression, name);
  }

  public static void notNullScalar(
    final float expression,
    final String name)
  {

  }
}
