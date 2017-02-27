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

package com.io7m.jregions.core;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions over area sizes.
 */

public final class AreaSizesL
{
  private AreaSizesL()
  {
    throw new UnreachableCodeException();
  }

  /**
   * <p>Determine if an area includes another area.</p>
   *
   * <p>Inclusion is reflexive: {@code ∀a. includes(a, a)}</p>
   *
   * <p>Inclusion is transitive: {@code ∀a b c. includes(a, b) ∧ includes(b, c)
   * → includes(a, c)}</p>
   *
   * @param a The containing area
   * @param b The contained area
   *
   * @return {@code true} if {@code a} can contain {@code b}
   */

  public static boolean includes(
    final AreaSizeL a,
    final AreaSizeL b)
  {
    NullCheck.notNull(a, "Area A");
    NullCheck.notNull(b, "Area B");
    return Long.compareUnsigned(b.width(), a.width()) <= 0
      && Long.compareUnsigned(b.height(), a.height()) <= 0;
  }
}
