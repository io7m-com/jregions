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

package com.io7m.jregions.core.conversions;

import com.io7m.jnull.NullCheck;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeF;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeL;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeL;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to convert between area types.
 */

public final class AreaSizeConversions
{
  private AreaSizeConversions()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeI toAreaSizeI(final PAreaSizeI<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeI.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeL toAreaSizeL(final PAreaSizeL<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeL.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeD toAreaSizeD(final PAreaSizeD<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeD.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeF toAreaSizeF(final PAreaSizeF<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeF.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeBD toAreaSizeBD(final PAreaSizeBD<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeBD.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> AreaSizeBI toAreaSizeBI(final PAreaSizeBI<S> a)
  {
    NullCheck.notNull(a, "area size");
    return AreaSizeBI.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeI<S> toPAreaSizeI(final AreaSizeI a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeI.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeL<S> toPAreaSizeL(final AreaSizeL a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeL.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeD<S> toPAreaSizeD(final AreaSizeD a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeD.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeF<S> toPAreaSizeF(final AreaSizeF a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeF.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeBD<S> toPAreaSizeBD(final AreaSizeBD a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeBD.of(a.width(), a.height());
  }

  /**
   * Convert an area size.
   *
   * @param a   The area size
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area size
   *
   * @return An area size
   */

  public static <S> PAreaSizeBI<S> toPAreaSizeBI(final AreaSizeBI a)
  {
    NullCheck.notNull(a, "area size");
    return PAreaSizeBI.of(a.width(), a.height());
  }
}
