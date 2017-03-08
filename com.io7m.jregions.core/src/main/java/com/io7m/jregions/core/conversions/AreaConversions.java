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
import com.io7m.jregions.core.parameterized.areas.PAreaBD;
import com.io7m.jregions.core.parameterized.areas.PAreaBI;
import com.io7m.jregions.core.parameterized.areas.PAreaD;
import com.io7m.jregions.core.parameterized.areas.PAreaF;
import com.io7m.jregions.core.parameterized.areas.PAreaI;
import com.io7m.jregions.core.parameterized.areas.PAreaL;
import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaF;
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to convert between area types.
 */

public final class AreaConversions
{
  private AreaConversions()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaI toAreaI(final PAreaI<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaI.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaL toAreaL(final PAreaL<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaL.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaD toAreaD(final PAreaD<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaD.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaF toAreaF(final PAreaF<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaF.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaBD toAreaBD(final PAreaBD<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaBD.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> AreaBI toAreaBI(final PAreaBI<S> a)
  {
    NullCheck.notNull(a, "area");
    return AreaBI.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaI<S> toPAreaI(final AreaI a)
  {
    NullCheck.notNull(a, "area");
    return PAreaI.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaL<S> toPAreaL(final AreaL a)
  {
    NullCheck.notNull(a, "area");
    return PAreaL.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaD<S> toPAreaD(final AreaD a)
  {
    NullCheck.notNull(a, "area");
    return PAreaD.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaF<S> toPAreaF(final AreaF a)
  {
    NullCheck.notNull(a, "area");
    return PAreaF.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaBD<S> toPAreaBD(final AreaBD a)
  {
    NullCheck.notNull(a, "area");
    return PAreaBD.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }

  /**
   * Convert an area.
   *
   * @param a   The area
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return An area
   */

  public static <S> PAreaBI<S> toPAreaBI(final AreaBI a)
  {
    NullCheck.notNull(a, "area");
    return PAreaBI.of(a.minimumX(), a.maximumX(), a.minimumY(), a.maximumY());
  }
}
