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

public final class PAreasL
{
  private PAreasL()
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

  public static <S> PAreaL<S> create(
    final long x,
    final long y,
    final long width,
    final long height)
  {
    return PAreaL.of(
      x,
      Math.addExact(x, width),
      y,
      Math.addExact(y, height));
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
    final PAreaL<S> a,
    final PAreaL<S> b)
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

  public static <S> PAreaL<S> moveRelative(
    final PAreaL<S> area,
    final long x,
    final long y)
  {
    NullCheck.notNull(area, "Area");

    final long x_min = Math.addExact(area.minimumX(), x);
    final long x_max = Math.addExact(area.maximumX(), x);
    final long y_min = Math.addExact(area.minimumY(), y);
    final long y_max = Math.addExact(area.maximumY(), y);
    return PAreaL.of(x_min, x_max, y_min, y_max);
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

  public static <S> PAreaL<S> moveAbsolute(
    final PAreaL<S> area,
    final long x,
    final long y)
  {
    NullCheck.notNull(area, "Area");
    return PAreasL.create(x, y, area.width(), area.height());
  }

  /**
   * Move the given area to {@code (0, 0)}.
   *
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static <S> PAreaL<S> moveToOrigin(
    final PAreaL<S> area)
  {
    NullCheck.notNull(area, "Area");
    return PAreasL.create(0L, 0L, area.width(), area.height());
  }


  private static long clamp(
    final long x,
    final long minimum,
    final long maximum)
  {
    Preconditions.checkPrecondition(maximum >= minimum, "Maximum >= minimum");
    return Math.max(Math.min(x, maximum), minimum);
  }

  /**
   * Brand a given area as belonging to a different coordinate space. Mixing up
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
  public static <S, T> PAreaL<T> cast(
    final PAreaL<S> area)
  {
    NullCheck.notNull(area);
    return (PAreaL<T>) area;
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

  public static <S> PAreaL<S> alignHorizontallyCenter(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long outer_width = outer.width();
    final long inner_width = inner.width();
    final long xm0 = Math.addExact(outer.minimumX(), outer_width / 2L);
    final long xm1 = Math.subtractExact(xm0, inner_width / 2L);
    return PAreasL.create(xm1, inner.minimumY(), inner_width, inner.height());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMinXOffset(PAreaL, PAreaL,
   * long)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignHorizontallyMinX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignHorizontallyMinXOffset(outer, inner, 0L);
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

  public static <S> PAreaL<S> alignHorizontallyMinXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_min = Math.addExact(outer.minimumX(), offset);
    final long x_max = Math.addExact(x_min, inner.width());
    return PAreaL.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMaxXOffset(PAreaL,
   * PAreaL, long)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignHorizontallyMaxX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignHorizontallyMaxXOffset(outer, inner, 0L);
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

  public static <S> PAreaL<S> alignHorizontallyMaxXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_max = Math.subtractExact(outer.maximumX(), offset);
    final long x_min = Math.subtractExact(x_max, inner.width());
    return PAreaL.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMinYOffset(PAreaL, PAreaL,
   * long)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignVerticallyMinY(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignVerticallyMinYOffset(outer, inner, 0L);
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

  public static <S> PAreaL<S> alignVerticallyMinYOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long y_min = Math.addExact(outer.minimumY(), offset);
    final long y_max = Math.addExact(y_min, inner.height());
    return PAreaL.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMaxYOffset(PAreaL, PAreaL,
   * long)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignVerticallyMaxY(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignVerticallyMaxYOffset(outer, inner, 0L);
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

  public static <S> PAreaL<S> alignVerticallyMaxYOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long y_max = Math.subtractExact(outer.maximumY(), offset);
    final long y_min = Math.subtractExact(y_max, inner.height());
    return PAreaL.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
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

  public static <S> PAreaL<S> alignVerticallyCenter(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long outer_height = outer.height();
    final long inner_height = inner.height();

    final long ym0 = Math.addExact(outer.minimumY(), outer_height / 2L);
    final long ym1 = Math.subtractExact(ym0, inner_height / 2L);
    return PAreasL.create(inner.minimumX(), ym1, inner.width(), inner_height);
  }

  /**
   * Equivalent to calling {@link #alignMinYMinXOffset(PAreaL, PAreaL, long,
   * long)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignMinYMinX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignMinYMinXOffset(outer, inner, 0L, 0L);
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

  public static <S> PAreaL<S> alignMinYMinXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset_x,
    final long offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_min = Math.addExact(outer.minimumX(), offset_x);
    final long y_min = Math.addExact(outer.minimumY(), offset_y);
    final long y_max = Math.addExact(y_min, inner.height());
    final long x_max = Math.addExact(x_min, inner.width());
    return PAreaL.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMinYMaxXOffset(PAreaL, PAreaL, long,
   * long)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignMinYMaxX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignMinYMaxXOffset(outer, inner, 0L, 0L);
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

  public static <S> PAreaL<S> alignMinYMaxXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset_x,
    final long offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_max = Math.subtractExact(outer.maximumX(), offset_x);
    final long y_min = Math.addExact(outer.minimumY(), offset_y);
    final long y_max = Math.addExact(y_min, inner.height());
    final long x_min = Math.subtractExact(x_max, inner.width());
    return PAreaL.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMinXOffset(PAreaL, PAreaL, long,
   * long)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignMaxYMinX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignMaxYMinXOffset(outer, inner, 0L, 0L);
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

  public static <S> PAreaL<S> alignMaxYMinXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset_x,
    final long offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_min = Math.addExact(outer.minimumX(), offset_x);
    final long y_max = Math.subtractExact(outer.maximumY(), offset_y);
    final long y_min = Math.subtractExact(y_max, inner.height());
    final long x_max = Math.addExact(x_min, inner.width());
    return PAreaL.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMaxXOffset(PAreaL, PAreaL, long,
   * long)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaL<S> alignMaxYMaxX(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    return PAreasL.alignMaxYMaxXOffset(outer, inner, 0L, 0L);
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

  public static <S> PAreaL<S> alignMaxYMaxXOffset(
    final PAreaL<S> outer,
    final PAreaL<S> inner,
    final long offset_x,
    final long offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final long x_max = Math.subtractExact(outer.maximumX(), offset_x);
    final long y_max = Math.subtractExact(outer.maximumY(), offset_y);
    final long y_min = Math.subtractExact(y_max, inner.height());
    final long x_min = Math.subtractExact(x_max, inner.width());
    return PAreaL.of(x_min, x_max, y_min, y_max);
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

  public static <S> PAreaL<S> alignCenter(
    final PAreaL<S> outer,
    final PAreaL<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    return PAreasL.alignVerticallyCenter(
      outer, PAreasL.alignHorizontallyCenter(outer, inner));
  }

  /**
   * Construct a new area that fits inside {@code outer} based on the given
   * offsets from each edge.
   *
   * @param outer         The containing area
   * @param min_x_offset   The offset from the minimum-x edge (must be
   *                      non-negative)
   * @param max_x_offset  The offset from the maximum-x edge (must be
   *                      non-negative)
   * @param min_y_offset    The offset from the minimum-y edge (must be
   *                      non-negative)
   * @param max_y_offset The offset from the maximum-y edge (must be
   *                      non-negative)
   * @param <S>           The coordinate space of the areas
   *
   * @return A new area
   */

  public static <S> PAreaL<S> hollowOut(
    final PAreaL<S> outer,
    final long min_x_offset,
    final long max_x_offset,
    final long min_y_offset,
    final long max_y_offset)
  {
    NullCheck.notNull(outer);

    final long x_min =
      PAreasL.clamp(
        Math.addExact(outer.minimumX(), min_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final long x_max =
      PAreasL.clamp(
        Math.subtractExact(outer.maximumX(), max_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final long y_min =
      PAreasL.clamp(
        Math.addExact(outer.minimumY(), min_y_offset),
        outer.minimumY(),
        outer.maximumY());
    final long y_max =
      PAreasL.clamp(
        Math.subtractExact(outer.maximumY(), max_y_offset),
        outer.minimumY(),
        outer.maximumY());

    final long out_x_max = Math.max(x_min, x_max);
    final long out_y_max = Math.max(y_min, y_max);

    return PAreaL.of(x_min, out_x_max, y_min, out_y_max);
  }

  /**
   * Equivalent to calling {@link #hollowOut(PAreaL, long, long, long, long)}
   * with {@code offset} for all offset parameters.
   *
   * @param outer  The containing area
   * @param offset The offset from each edge (must be non-negative)
   * @param <S>    The coordinate space of the areas
   *
   * @return A new area
   */

  public static <S> PAreaL<S> hollowOutEvenly(
    final PAreaL<S> outer,
    final long offset)
  {
    return PAreasL.hollowOut(outer, offset, offset, offset, offset);
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

  public static <S> PAreaL<S> setSizeFromCenter(
    final PAreaL<S> area,
    final long width,
    final long height)
  {
    NullCheck.notNull(area);

    return PAreasL.alignCenter(area, PAreaL.of(
      area.minimumX(),
      Math.addExact(area.minimumX(), width),
      area.minimumY(),
      Math.addExact(area.minimumY(), height)));
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

  public static <S> PAreaL<S> setSizeFromMinYMinX(
    final PAreaL<S> area,
    final long width,
    final long height)
  {
    NullCheck.notNull(area);

    return PAreasL.alignMaxYMaxX(area, PAreaL.of(
      area.minimumX(),
      Math.addExact(area.minimumX(), width),
      area.minimumY(),
      Math.addExact(area.minimumY(), height)));
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

  public static <S> PAreaL<S> setSizeFromMinYMaxX(
    final PAreaL<S> area,
    final long width,
    final long height)
  {
    NullCheck.notNull(area);

    return PAreasL.alignMaxYMinX(area, PAreaL.of(
      area.minimumX(),
      Math.addExact(area.minimumX(), width),
      area.minimumY(),
      Math.addExact(area.minimumY(), height)));
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

  public static <S> PAreaL<S> setSizeFromMaxYMaxX(
    final PAreaL<S> area,
    final long width,
    final long height)
  {
    NullCheck.notNull(area);

    return PAreasL.alignMinYMinX(area, PAreaL.of(
      area.minimumX(),
      Math.addExact(area.minimumX(), width),
      area.minimumY(),
      Math.addExact(area.minimumY(), height)));
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

  public static <S> PAreaL<S> setSizeFromMaxYMinX(
    final PAreaL<S> area,
    final long width,
    final long height)
  {
    NullCheck.notNull(area);

    return PAreasL.alignMinYMaxX(area, PAreaL.of(
      area.minimumX(),
      Math.addExact(area.minimumX(), width),
      area.minimumY(),
      Math.addExact(area.minimumY(), height)));
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

  public static <S> PAreaL<S> scaleFromMinYMinX(
    final PAreaL<S> area,
    final long x_diff,
    final long y_diff)
  {
    NullCheck.notNull(area);

    final long width = Math.max(0L, Math.addExact(area.width(), x_diff));
    final long height = Math.max(0L, Math.addExact(area.height(), y_diff));
    return PAreasL.setSizeFromMinYMinX(area, width, height);
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

  public static <S> PAreaL<S> scaleFromMinYMaxX(
    final PAreaL<S> area,
    final long x_diff,
    final long y_diff)
  {
    NullCheck.notNull(area);

    final long width = Math.max(0L, Math.addExact(area.width(), x_diff));
    final long height = Math.max(0L, Math.addExact(area.height(), y_diff));
    return PAreasL.setSizeFromMinYMaxX(area, width, height);
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

  public static <S> PAreaL<S> scaleFromMaxYMinX(
    final PAreaL<S> area,
    final long x_diff,
    final long y_diff)
  {
    NullCheck.notNull(area);

    final long width = Math.max(0L, Math.addExact(area.width(), x_diff));
    final long height = Math.max(0L, Math.addExact(area.height(), y_diff));
    return PAreasL.setSizeFromMaxYMinX(area, width, height);
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

  public static <S> PAreaL<S> scaleFromMaxYMaxX(
    final PAreaL<S> area,
    final long x_diff,
    final long y_diff)
  {
    NullCheck.notNull(area);

    final long width = Math.max(0L, Math.addExact(area.width(), x_diff));
    final long height = Math.max(0L, Math.addExact(area.height(), y_diff));
    return PAreasL.setSizeFromMaxYMaxX(area, width, height);
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

  public static <S> PAreaL<S> scaleFromCenter(
    final PAreaL<S> area,
    final long x_diff,
    final long y_diff)
  {
    NullCheck.notNull(area);

    final long width = Math.max(0L, Math.addExact(area.width(), x_diff));
    final long height = Math.max(0L, Math.addExact(area.height(), y_diff));
    return PAreasL.setSizeFromCenter(area, width, height);
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
    final PAreaL<S> a,
    final PAreaL<S> b)
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
    final PAreaL<S> a,
    final PAreaL<S> b)
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
   *
   * @since 0.2.0
   */

  public static <S> PAreaL<S> containing(
    final PAreaL<S> a,
    final PAreaL<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    return PAreaL.of(
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
    final PAreaL<S> a,
    final long x,
    final long y)
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

  public static <S> PAreaL<S> fitBetweenHorizontal(
    final PAreaL<S> fit,
    final PAreaL<S> a,
    final PAreaL<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final long x_min = Math.min(a.maximumX(), b.maximumX());
    final long x_max = Math.max(a.minimumX(), b.minimumX());
    final long out_x_min = Math.min(x_min, x_max);
    final long out_x_max = Math.max(x_min, x_max);
    return PAreaL.of(out_x_min, out_x_max, fit.minimumY(), fit.maximumY());
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

  public static <S> PAreaL<S> fitBetweenVertical(
    final PAreaL<S> fit,
    final PAreaL<S> a,
    final PAreaL<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final long y_min = Math.min(a.maximumY(), b.maximumY());
    final long y_max = Math.max(a.minimumY(), b.minimumY());
    final long out_y_min = Math.min(y_min, y_max);
    final long out_y_max = Math.max(y_min, y_max);
    return PAreaL.of(fit.minimumX(), fit.maximumX(), out_y_min, out_y_max);
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

  public static <S> PAreaXSplitL<S, PAreaL<S>> splitAlongParallelToX(
    final PAreaL<S> area,
    final long y)
  {
    NullCheck.notNull(area);

    final long clamped_height = Math.min(area.height(), y);
    final long upper_y_min = area.minimumY();
    final long upper_y_max = Math.addExact(area.minimumY(), clamped_height);
    final long lower_y_min = upper_y_max;
    final long lower_y_max = area.maximumY();

    final PAreaL<S> lower = PAreaL.of(
      area.minimumX(), area.maximumX(), lower_y_min, lower_y_max);
    final PAreaL<S> upper = PAreaL.of(
      area.minimumX(), area.maximumX(), upper_y_min, upper_y_max);

    return PAreaXSplitL.of(lower, upper);
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

  public static <S> PAreaYSplitL<S, PAreaL<S>> splitAlongParallelToY(
    final PAreaL<S> area,
    final long x)
  {
    NullCheck.notNull(area);

    final long clamped_width = Math.min(area.width(), x);
    final long lower_x_min = area.minimumX();
    final long lower_x_max = Math.addExact(area.minimumX(), clamped_width);
    final long upper_x_min = lower_x_max;
    final long upper_x_max = area.maximumX();

    final PAreaL<S> lower = PAreaL.of(
      lower_x_min, lower_x_max, area.minimumY(), area.maximumY());
    final PAreaL<S> upper = PAreaL.of(
      upper_x_min, upper_x_max, area.minimumY(), area.maximumY());

    return PAreaYSplitL.of(lower, upper);
  }

  /**
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static <S> String show(
    final PAreaL<S> area)
  {
    NullCheck.notNull(area);

    final StringBuilder sb = new StringBuilder(128);
    return PAreasL.showToBuilder(area, sb);
  }

  /**
   * @param area The area
   * @param sb   A string builder
   * @param <S>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static <S> String showToBuilder(
    final PAreaL<S> area,
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
