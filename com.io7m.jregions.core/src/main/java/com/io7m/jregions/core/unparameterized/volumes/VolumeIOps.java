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

final class VolumeIOps
{
  public static final int ZERO = 0;

  public static final int ONE = 1;

  private VolumeIOps()
  {
    throw new UnreachableCodeException();
  }

  public static int minimum(
    final int a,
    final int b)
  {
    return Math.min(a, b);
  }

  public static int compare(
    final int a,
    final int b)
  {
    return Integer.compare(a, b);
  }

  public static int maximum(
    final int a,
    final int b)
  {
    return Math.max(a, b);
  }

  public static int add(
    final int a,
    final int b)
  {
    return Math.addExact(a, b);
  }

  public static int subtract(
    final int a,
    final int b)
  {
    return Math.subtractExact(a, b);
  }

  public static int divide(
    final int a,
    final int b)
  {
    return a / b;
  }

  public static int constant(
    final long x)
  {
    return Math.toIntExact(x);
  }

  public static void notNullVolume(
    final Object expression,
    final String name)
  {
    Objects.requireNonNull(expression, name);
  }

  public static void notNullScalar(
    final int expression,
    final String name)
  {

  }
}
