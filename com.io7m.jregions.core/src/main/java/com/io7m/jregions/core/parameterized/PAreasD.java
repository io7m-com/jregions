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

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p>Functions over areas.</p>
 *
 * <p>These functions operate using the concepts of <i>minimum-x</i>,
 * <i>maximum-x</i>, <i>minimum-y</i>, and <i>maximum-y</i> edges. It is up to
 * individual applications to assign meaning to these edges such as "left" for
 * <i>minimum-x</i> and "top" for <i>minimum-y</i>.</p>
 */

public final class PAreasD
{
  private PAreasD()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Create an area of width {@code width} and height {@code height}, placing
   * the minimum corner at {@code (x, y)}.
   *
   * @param x      The X value of the minimum corner
   * @param y      The Y value of the minimum corner
   * @param width  The width of the area
   * @param height The height of the area
   * @param <S>    The coordinate space of the area
   *
   * @return An area
   */

  public static <S> PAreaD<S> create(
    final double x,
    final double y,
    final double width,
    final double height)
  {
    return PAreaD.of(
      x,
      x + width,
      y,
      y + height);
  }

  /**
   * <p>Determine whether or not one area contains another.</p>
   *
   * <p>Containing is reflexive: {@code contains(a, a) == true}.</p>
   *
   * <p>Containing is transitive: {@code contains(a, b) → contains(b, c) →
   * contains(a, c)}.</p>
   *
   * <p>Containing is not necessarily symmetric.</p>
   *
   * @param a   Area A
   * @param b   Area B
   * @param <S> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} contains {@code b}
   */

  public static <S> boolean contains(
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(a, "Area A");
    NullCheck.notNull(b, "Area B");

    final boolean contain_x = b.minimumX() >= a.minimumX() && b.maximumX() <= a.maximumX();
    final boolean contain_y = b.minimumY() >= a.minimumY() && b.maximumY() <= a.maximumY();
    return contain_x && contain_y;
  }

  /**
   * Move the given area by {@code (x, y)}.
   *
   * @param area The area
   * @param x    The amount to move on the X axis
   * @param y    The amount to move on the Y axis
   * @param <S>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static <S> PAreaD<S> moveRelative(
    final PAreaD<S> area,
    final double x,
    final double y)
  {
    NullCheck.notNull(area, "Area");

    final double x_min = area.minimumX() + x;
    final double x_max = area.maximumX() + x;
    final double y_min = area.minimumY() + y;
    final double y_max = area.maximumY() + y;
    return PAreaD.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Move the given area to {@code (x, y)}.
   *
   * @param area The area
   * @param x    The position to which to move on the X axis
   * @param y    The position to which to move on the Y axis
   * @param <S>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static <S> PAreaD<S> moveAbsolute(
    final PAreaD<S> area,
    final double x,
    final double y)
  {
    NullCheck.notNull(area, "Area");
    return create(x, y, area.width(), area.height());
  }

  /**
   * Move the given area to {@code (0.0, 0.0)}.
   *
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static <S> PAreaD<S> moveToOrigin(
    final PAreaD<S> area)
  {
    NullCheck.notNull(area, "Area");
    return create(0.0, 0.0, area.width(), area.height());
  }


  private static double clamp(
    final double x,
    final double minimum,
    final double maximum)
  {
    Preconditions.checkPrecondition(maximum >= minimum, "Maximum >= minimum");
    return Math.max(Math.min(x, maximum), minimum);
  }

  /**
   * Brand a given area as beinting to a different coordinate space. Mixing up
   * coordinate spaces is a common source of difficult-to-locate bugs. Use at
   * your own risk.
   *
   * @param area An area
   * @param <S>  The starting coordinate space
   * @param <T>  The resulting coordinate space
   *
   * @return {@code area}
   */

  @SuppressWarnings("unchecked")
  public static <S, T> PAreaD<T> cast(
    final PAreaD<S> area)
  {
    NullCheck.notNull(area);
    return (PAreaD<T>) area;
  }


  /**
   * Align the area {@code inner} horizontally in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignHorizontallyCenter(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double outer_width = outer.width();
    final double inner_width = inner.width();
    final double xm0 = outer.minimumX() + outer_width / 2;
    final double xm1 = xm0 - inner_width / 2;
    return create(xm1, inner.minimumY(), inner_width, inner.height());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMinXOffset(PAreaD, PAreaD,
   * double)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignHorizontallyMinX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignHorizontallyMinXOffset(outer, inner, 0.0);
  }

  /**
   * Align the area {@code inner} horizontally against the inside minimum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param <S>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignHorizontallyMinXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_min = outer.minimumX() + offset;
    final double x_max = x_min + inner.width();
    return PAreaD.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMaxXOffset(PAreaD,
   * PAreaD, double)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignHorizontallyMaxX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignHorizontallyMaxXOffset(outer, inner, 0.0);
  }

  /**
   * Align the area {@code inner} horizontally against the inside maximum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param <S>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignHorizontallyMaxXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_max = outer.maximumX() - offset;
    final double x_min = x_max - inner.width();
    return PAreaD.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMinYOffset(PAreaD, PAreaD,
   * double)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignVerticallyMinY(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignVerticallyMinYOffset(outer, inner, 0.0);
  }

  /**
   * Align the area {@code inner} vertically against the inside minimum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param <S>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignVerticallyMinYOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double y_min = outer.minimumY() + offset;
    final double y_max = y_min + inner.height();
    return PAreaD.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMaxYOffset(PAreaD, PAreaD,
   * double)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignVerticallyMaxY(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignVerticallyMaxYOffset(outer, inner, 0.0);
  }

  /**
   * Align the area {@code inner} vertically against the inside maximum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param <S>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignVerticallyMaxYOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double y_max = outer.maximumY() - offset;
    final double y_min = y_max - inner.height();
    return PAreaD.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Align the area {@code inner} vertically in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignVerticallyCenter(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double outer_height = outer.height();
    final double inner_height = inner.height();

    final double ym0 = outer.minimumY() + outer_height / 2;
    final double ym1 = ym0 - inner_height / 2;
    return create(inner.minimumX(), ym1, inner.width(), inner_height);
  }

  /**
   * Equivalent to calling {@link #alignMinYMinXOffset(PAreaD, PAreaD, double,
   * double)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMinYMinX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignMinYMinXOffset(outer, inner, 0.0, 0.0);
  }

  /**
   * Align the area {@code inner} such that the minimum-y edge is at least
   * {@code offset_y} from the inside minimum-y edge of {@code outer} and the
   * minimum-x edge is at least {@code offset_x} from the inside minimum-x edge
   * of {@code outer}.
   *
   * @param outer    The outer area
   * @param inner    The inner area
   * @param offset_x The offset from the minimum-x edge
   * @param offset_y The offset from the minimum-y edge
   * @param <S>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMinYMinXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset_x,
    final double offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_min = outer.minimumX() + offset_x;
    final double y_min = outer.minimumY() + offset_y;
    final double y_max = y_min + inner.height();
    final double x_max = x_min + inner.width();
    return PAreaD.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMinYMaxXOffset(PAreaD, PAreaD, double,
   * double)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMinYMaxX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignMinYMaxXOffset(outer, inner, 0.0, 0.0);
  }

  /**
   * Align the area {@code inner} such that the minimum-y edge is at least
   * {@code offset_y} from the inside minimum-y edge of {@code outer} and the
   * maximum-x edge is at least {@code offset_x} from the inside maximum-x edge
   * of {@code outer}.
   *
   * @param outer    The outer area
   * @param inner    The inner area
   * @param offset_x The offset from the maximum-x edge
   * @param offset_y The offset from the minimum-y edge
   * @param <S>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMinYMaxXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset_x,
    final double offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_max = outer.maximumX() - offset_x;
    final double y_min = outer.minimumY() + offset_y;
    final double y_max = y_min + inner.height();
    final double x_min = x_max - inner.width();
    return PAreaD.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMinXOffset(PAreaD, PAreaD, double,
   * double)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMaxYMinX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignMaxYMinXOffset(outer, inner, 0.0, 0.0);
  }

  /**
   * Align the area {@code inner} such that the maximum-y edge is at least
   * {@code offset_y} from the inside maximum-y edge of {@code outer} and the
   * minimum-x edge is at least {@code offset_x} from the inside minimum-x edge
   * of {@code outer}.
   *
   * @param outer    The outer area
   * @param inner    The inner area
   * @param offset_x The offset from the minimum-x edge
   * @param offset_y The offset from the maximum-y edge
   * @param <S>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMaxYMinXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset_x,
    final double offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_min = outer.minimumX() + offset_x;
    final double y_max = outer.maximumY() - offset_y;
    final double y_min = y_max - inner.height();
    final double x_max = x_min + inner.width();
    return PAreaD.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMaxXOffset(PAreaD, PAreaD, double,
   * double)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMaxYMaxX(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    return alignMaxYMaxXOffset(outer, inner, 0.0, 0.0);
  }

  /**
   * Align the area {@code inner} such that the maximum-y edge is at least
   * {@code offset_y} from the inside maximum-y edge of {@code outer} and the
   * maximum-x edge is at least {@code offset_x} from the inside maximum-x edge
   * of {@code outer}.
   *
   * @param outer    The outer area
   * @param inner    The inner area
   * @param offset_x The offset from the maximum-x edge
   * @param offset_y The offset from the maximum-y edge
   * @param <S>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignMaxYMaxXOffset(
    final PAreaD<S> outer,
    final PAreaD<S> inner,
    final double offset_x,
    final double offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final double x_max = outer.maximumX() - offset_x;
    final double y_max = outer.maximumY() - offset_y;
    final double y_min = y_max - inner.height();
    final double x_min = x_max - inner.width();
    return PAreaD.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Align the area {@code inner} such that the center of the area is equal to
   * the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaD<S> alignCenter(
    final PAreaD<S> outer,
    final PAreaD<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    return alignVerticallyCenter(
      outer, alignHorizontallyCenter(outer, inner));
  }

  /**
   * Construct a new area that fits inside {@code outer} based on the given
   * offsets from each edge.
   *
   * @param outer        The containing area
   * @param min_x_offset The offset from the minimum-x edge (must be
   *                     non-negative)
   * @param max_x_offset The offset from the maximum-x edge (must be
   *                     non-negative)
   * @param min_y_offset The offset from the minimum-y edge (must be
   *                     non-negative)
   * @param max_y_offset The offset from the maximum-y edge (must be
   *                     non-negative)
   * @param <S>          The coordinate space of the areas
   *
   * @return A new area
   */

  public static <S> PAreaD<S> hollowOut(
    final PAreaD<S> outer,
    final double min_x_offset,
    final double max_x_offset,
    final double min_y_offset,
    final double max_y_offset)
  {
    NullCheck.notNull(outer);

    final double x_min =
      clamp(
        outer.minimumX() + min_x_offset,
        outer.minimumX(),
        outer.maximumX());
    final double x_max =
      clamp(
        outer.maximumX() - max_x_offset,
        outer.minimumX(),
        outer.maximumX());
    final double y_min =
      clamp(
        outer.minimumY() + min_y_offset,
        outer.minimumY(),
        outer.maximumY());
    final double y_max =
      clamp(
        outer.maximumY() - max_y_offset,
        outer.minimumY(),
        outer.maximumY());

    final double out_x_max = Math.max(x_min, x_max);
    final double out_y_max = Math.max(y_min, y_max);

    return PAreaD.of(x_min, out_x_max, y_min, out_y_max);
  }

  /**
   * Equivalent to calling {@link #hollowOut(PAreaD, double, double, double,
   * double)} with {@code offset} for all offset parameters.
   *
   * @param outer  The containing area
   * @param offset The offset from each edge (must be non-negative)
   * @param <S>    The coordinate space of the areas
   *
   * @return A new area
   */

  public static <S> PAreaD<S> hollowOutEvenly(
    final PAreaD<S> outer,
    final double offset)
  {
    return hollowOut(outer, offset, offset, offset, offset);
  }

  /**
   * <p>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.</p>
   *
   * <p>The area is resized from its own center.</p>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> setSizeFromCenter(
    final PAreaD<S> area,
    final double width,
    final double height)
  {
    NullCheck.notNull(area);

    return alignCenter(area, PAreaD.of(
      area.minimumX(),
      area.minimumX() + width,
      area.minimumY(),
      area.minimumY() + height));
  }

  /**
   * <p>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.</p>
   *
   * <p>The area is resized by moving its minimum-y-minimum-x corner.</p>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> setSizeFromMinYMinX(
    final PAreaD<S> area,
    final double width,
    final double height)
  {
    NullCheck.notNull(area);

    return alignMaxYMaxX(area, PAreaD.of(
      area.minimumX(),
      area.minimumX() + width,
      area.minimumY(),
      area.minimumY() + height));
  }

  /**
   * <p>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.</p>
   *
   * <p>The area is resized by moving its minimum-y-maximum-x corner.</p>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> setSizeFromMinYMaxX(
    final PAreaD<S> area,
    final double width,
    final double height)
  {
    NullCheck.notNull(area);

    return alignMaxYMinX(area, PAreaD.of(
      area.minimumX(),
      area.minimumX() + width,
      area.minimumY(),
      area.minimumY() + height));
  }

  /**
   * <p>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.</p>
   *
   * <p>The area is resized by moving its maximum-y-maximum-x corner.</p>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> setSizeFromMaxYMaxX(
    final PAreaD<S> area,
    final double width,
    final double height)
  {
    NullCheck.notNull(area);

    return alignMinYMinX(area, PAreaD.of(
      area.minimumX(),
      area.minimumX() + width,
      area.minimumY(),
      area.minimumY() + height));
  }

  /**
   * <p>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.</p>
   *
   * <p>The area is resized by moving its maximum-y-minimum-x corner.</p>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> setSizeFromMaxYMinX(
    final PAreaD<S> area,
    final double width,
    final double height)
  {
    NullCheck.notNull(area);

    return alignMinYMaxX(area, PAreaD.of(
      area.minimumX(),
      area.minimumX() + width,
      area.minimumY(),
      area.minimumY() + height));
  }

  /**
   * <p>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.</p>
   *
   * <p>The area is resized by moving its minimum-y-minimum-x corner.</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> scaleFromMinYMinX(
    final PAreaD<S> area,
    final double x_diff,
    final double y_diff)
  {
    NullCheck.notNull(area);

    final double width = Math.max(0.0, area.width() + x_diff);
    final double height = Math.max(0.0, area.height() + y_diff);
    return setSizeFromMinYMinX(area, width, height);
  }

  /**
   * <p>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.</p>
   *
   * <p>The area is resized by moving its minimum-y-maximum-x corner.</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> scaleFromMinYMaxX(
    final PAreaD<S> area,
    final double x_diff,
    final double y_diff)
  {
    NullCheck.notNull(area);

    final double width = Math.max(0.0, area.width() + x_diff);
    final double height = Math.max(0.0, area.height() + y_diff);
    return setSizeFromMinYMaxX(area, width, height);
  }

  /**
   * <p>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.</p>
   *
   * <p>The area is resized by moving its maximum-y-minimum-x corner.</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> scaleFromMaxYMinX(
    final PAreaD<S> area,
    final double x_diff,
    final double y_diff)
  {
    NullCheck.notNull(area);

    final double width = Math.max(0.0, area.width() + x_diff);
    final double height = Math.max(0.0, area.height() + y_diff);
    return setSizeFromMaxYMinX(area, width, height);
  }

  /**
   * <p>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.</p>
   *
   * <p>The area is resized by moving its maximum-y-maximum-x corner.</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> scaleFromMaxYMaxX(
    final PAreaD<S> area,
    final double x_diff,
    final double y_diff)
  {
    NullCheck.notNull(area);

    final double width = Math.max(0.0, area.width() + x_diff);
    final double height = Math.max(0.0, area.height() + y_diff);
    return setSizeFromMaxYMaxX(area, width, height);
  }

  /**
   * <p>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.</p>
   *
   * <p>The area is resized from its own center.</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param <S>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static <S> PAreaD<S> scaleFromCenter(
    final PAreaD<S> area,
    final double x_diff,
    final double y_diff)
  {
    NullCheck.notNull(area);

    final double width = Math.max(0.0, area.width() + x_diff);
    final double height = Math.max(0.0, area.height() + y_diff);
    return setSizeFromCenter(area, width, height);
  }

  /**
   * <p>Determine whether or not two areas overlap.</p>
   *
   * <p>Overlapping is reflexive: {@code overlaps(a, a) == true}.</p>
   *
   * <p>Overlapping is symmetric: {@code overlaps(a, b) == contains(b, a)}.</p>
   *
   * <p>Overlapping is not necessarily transitive.</p>
   *
   * @param a   An area
   * @param b   An area
   * @param <S> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} overlaps {@code b}
   */

  public static <S> boolean overlaps(
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    if (a.minimumX() >= b.maximumX() || a.maximumX() < b.minimumX()) {
      return false;
    }
    if (a.minimumY() >= b.maximumY() || a.maximumY() < b.minimumY()) {
      return false;
    }
    return true;
  }

  /**
   * <p>Determine whether or not one area could fit inside another.</p>
   *
   * <p>Fitting is reflexive: {@code couldFitInside(a, a) == true}.</p>
   *
   * <p>Fitting is transitive: {@code couldFitInside(a, b) → couldFitInside(b,
   * c) → couldFitInside(a, c)}.</p>
   *
   * <p>Fitting is not necessarily symmetric.</p>
   *
   * @param a   An area
   * @param b   An area
   * @param <S> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} could fit inside {@code b}
   */

  public static <S> boolean couldFitInside(
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);
    final boolean width_ok = a.width() <= b.width();
    final boolean height_ok = a.height() <= b.height();
    return width_ok && height_ok;
  }

  /**
   * Construct a area that will contain both {@code a} and {@code b}.
   *
   * @param a   An area
   * @param b   An area
   * @param <S> The coordinate space of the areas
   *
   * @return An area containing {@code a} and {@code b}
   */

  public static <S> PAreaD<S> containing(
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    return PAreaD.of(
      Math.min(a.minimumX(), b.minimumX()),
      Math.max(a.maximumX(), b.maximumX()),
      Math.min(a.minimumY(), b.minimumY()),
      Math.max(a.maximumY(), b.maximumY()));
  }

  /**
   * <p>Determine whether or not a area contains a given point.</p>
   *
   * @param a   An area
   * @param x   The X coordinate of the point
   * @param y   The Y coordinate of the point
   * @param <S> The coordinate space of the area
   *
   * @return {@code true} iff {@code a} contains {@code (x, y)}
   */

  public static <S> boolean containsPoint(
    final PAreaD<S> a,
    final double x,
    final double y)
  {
    NullCheck.notNull(a);

    final boolean contain_x = x >= a.minimumX() && x < a.maximumX();
    final boolean contain_y = y >= a.minimumY() && y < a.maximumY();
    return contain_x && contain_y;
  }

  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, horizontally.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * @param <S> The coordinate space of the areas
   *
   * @return A fitted area
   */

  public static <S> PAreaD<S> fitBetweenHorizontal(
    final PAreaD<S> fit,
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final double x_min = Math.min(a.maximumX(), b.maximumX());
    final double x_max = Math.max(a.minimumX(), b.minimumX());
    final double out_x_min = Math.min(x_min, x_max);
    final double out_x_max = Math.max(x_min, x_max);
    return PAreaD.of(out_x_min, out_x_max, fit.minimumY(), fit.maximumY());
  }

  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, vertically.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * @param <S> The coordinate space of the areas
   *
   * @return A fitted area
   */

  public static <S> PAreaD<S> fitBetweenVertical(
    final PAreaD<S> fit,
    final PAreaD<S> a,
    final PAreaD<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final double y_min = Math.min(a.maximumY(), b.maximumY());
    final double y_max = Math.max(a.minimumY(), b.minimumY());
    final double out_y_min = Math.min(y_min, y_max);
    final double out_y_max = Math.max(y_min, y_max);
    return PAreaD.of(fit.minimumX(), fit.maximumX(), out_y_min, out_y_max);
  }

  /**
   * Split {@code area} along a line parallel to the X axis placed at
   * {@code y} units from its own minimum-y edge.
   *
   * @param area The area to be split
   * @param y    The relative Y coordinate of the splitting edge
   * @param <S>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static <S> PAreaXSplitD<S, PAreaD<S>> splitAlongParallelToX(
    final PAreaD<S> area,
    final double y)
  {
    NullCheck.notNull(area);

    final double clamped_height = Math.min(area.height(), y);
    final double upper_y_min = area.minimumY();
    final double upper_y_max = area.minimumY() + clamped_height;
    final double lower_y_min = upper_y_max;
    final double lower_y_max = area.maximumY();

    final PAreaD<S> lower = PAreaD.of(
      area.minimumX(), area.maximumX(), lower_y_min, lower_y_max);
    final PAreaD<S> upper = PAreaD.of(
      area.minimumX(), area.maximumX(), upper_y_min, upper_y_max);

    return PAreaXSplitD.of(lower, upper);
  }

  /**
   * Split {@code area} along a line parallel to the Y axis placed at {@code x}
   * units from its own minimum-x edge.
   *
   * @param area The area to be split
   * @param x    The relative X coordinate of the splitting edge
   * @param <S>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static <S> PAreaYSplitD<S, PAreaD<S>> splitAlongParallelToY(
    final PAreaD<S> area,
    final double x)
  {
    NullCheck.notNull(area);

    final double clamped_width = Math.min(area.width(), x);
    final double lower_x_min = area.minimumX();
    final double lower_x_max = area.minimumX() + clamped_width;
    final double upper_x_min = lower_x_max;
    final double upper_x_max = area.maximumX();

    final PAreaD<S> lower = PAreaD.of(
      lower_x_min, lower_x_max, area.minimumY(), area.maximumY());
    final PAreaD<S> upper = PAreaD.of(
      upper_x_min, upper_x_max, area.minimumY(), area.maximumY());

    return PAreaYSplitD.of(lower, upper);
  }

  /**
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static <S> String show(
    final PAreaD<S> area)
  {
    NullCheck.notNull(area);

    final StringBuilder sb = new StringBuilder(128);
    return showToBuilder(area, sb);
  }

  /**
   * @param area The area
   * @param sb   A string builder
   * @param <S>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static <S> String showToBuilder(
    final PAreaD<S> area,
    final StringBuilder sb)
  {
    NullCheck.notNull(area);
    NullCheck.notNull(sb);

    sb.append(area.width());
    sb.append("x");
    sb.append(area.height());
    sb.append(" ");
    sb.append(area.minimumX());
    sb.append("+");
    sb.append(area.minimumY());
    return sb.toString();
  }
}
