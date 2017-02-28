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

import java.math.BigInteger;

/**
 * <p>Functions over areas.</p>
 *
 * <p>These functions operate using the concepts of <i>minimum-x</i>,
 * <i>maximum-x</i>, <i>minimum-y</i>, and <i>maximum-y</i> edges. It is up to
 * individual applications to assign meaning to these edges such as "left" for
 * <i>minimum-x</i> and "top" for <i>minimum-y</i>.</p>
 */

public final class PAreasB
{
  private PAreasB()
  {
    throw new UnreachableCodeException();
  }

  private static BigInteger maximum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.max(b);
  }

  private static BigInteger minimum(
    final BigInteger a,
    final BigInteger b)
  {
    return a.min(b);
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

  public static <S> PAreaB<S> create(
    final BigInteger x,
    final BigInteger y,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(x, "x");
    NullCheck.notNull(y, "y");
    NullCheck.notNull(width, "width");
    NullCheck.notNull(height, "height");

    return PAreaB.of(
      x,
      x.add(width),
      y,
      y.add(height));
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
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(a, "Area A");
    NullCheck.notNull(b, "Area B");

    final boolean contain_x =
      b.minimumX().compareTo(a.minimumX()) >= 0
        && b.maximumX().compareTo(a.maximumX()) <= 0;
    final boolean contain_y =
      b.minimumY().compareTo(a.minimumY()) >= 0
        && b.maximumY().compareTo(a.maximumY()) <= 0;

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

  public static <S> PAreaB<S> moveRelative(
    final PAreaB<S> area,
    final BigInteger x,
    final BigInteger y)
  {
    NullCheck.notNull(area, "Area");

    final BigInteger x_min = area.minimumX().add(x);
    final BigInteger x_max = area.maximumX().add(x);
    final BigInteger y_min = area.minimumY().add(y);
    final BigInteger y_max = area.maximumY().add(y);
    return PAreaB.of(x_min, x_max, y_min, y_max);
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

  public static <S> PAreaB<S> moveAbsolute(
    final PAreaB<S> area,
    final BigInteger x,
    final BigInteger y)
  {
    NullCheck.notNull(area, "Area");
    return create(x, y, area.width(), area.height());
  }

  /**
   * Move the given area to {@code (0, 0)}.
   *
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static <S> PAreaB<S> moveToOrigin(
    final PAreaB<S> area)
  {
    NullCheck.notNull(area, "Area");
    return create(
      BigInteger.ZERO,
      BigInteger.ZERO,
      area.width(),
      area.height());
  }

  private static BigInteger clamp(
    final BigInteger x,
    final BigInteger minimum,
    final BigInteger maximum)
  {
    Preconditions.checkPrecondition(
      maximum.compareTo(minimum) >= 0,
      "Maximum >= minimum");
    return maximum(minimum(x, maximum), minimum);
  }

  /**
   * Brand a given area as beBigIntegering to a different coordinate space.
   * Mixing up coordinate spaces is a common source of difficult-to-locate bugs.
   * Use at your own risk.
   *
   * @param area An area
   * @param <S>  The starting coordinate space
   * @param <T>  The resulting coordinate space
   *
   * @return {@code area}
   */

  @SuppressWarnings("unchecked")
  public static <S, T> PAreaB<T> cast(
    final PAreaB<S> area)
  {
    NullCheck.notNull(area);
    return (PAreaB<T>) area;
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

  public static <S> PAreaB<S> alignHorizontallyCenter(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger outer_width = outer.width();
    final BigInteger inner_width = inner.width();
    final BigInteger xm0 = outer.minimumX().add(outer_width.divide(BigInteger.valueOf(
      2L)));
    final BigInteger xm1 = xm0.subtract(inner_width.divide(BigInteger.valueOf(2L)));
    return create(xm1, inner.minimumY(), inner_width, inner.height());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMinXOffset(PAreaB, PAreaB,
   * BigInteger)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignHorizontallyMinX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignHorizontallyMinXOffset(outer, inner, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignHorizontallyMinXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_min = outer.minimumX().add(offset);
    final BigInteger x_max = x_min.add(inner.width());
    return PAreaB.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMaxXOffset(PAreaB,
   * PAreaB, BigInteger)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignHorizontallyMaxX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignHorizontallyMaxXOffset(outer, inner, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignHorizontallyMaxXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_max = outer.maximumX().subtract(offset);
    final BigInteger x_min = x_max.subtract(inner.width());
    return PAreaB.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMinYOffset(PAreaB, PAreaB,
   * BigInteger)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignVerticallyMinY(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignVerticallyMinYOffset(outer, inner, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignVerticallyMinYOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger y_min = outer.minimumY().add(offset);
    final BigInteger y_max = y_min.add(inner.height());
    return PAreaB.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMaxYOffset(PAreaB, PAreaB,
   * BigInteger)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignVerticallyMaxY(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignVerticallyMaxYOffset(outer, inner, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignVerticallyMaxYOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger y_max = outer.maximumY().subtract(offset);
    final BigInteger y_min = y_max.subtract(inner.height());
    return PAreaB.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
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

  public static <S> PAreaB<S> alignVerticallyCenter(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger outer_height = outer.height();
    final BigInteger inner_height = inner.height();

    final BigInteger ym0 = outer.minimumY().add(outer_height.divide(BigInteger.valueOf(
      2L)));
    final BigInteger ym1 = ym0.subtract(inner_height.divide(BigInteger.valueOf(
      2L)));
    return create(inner.minimumX(), ym1, inner.width(), inner_height);
  }

  /**
   * Equivalent to calling {@link #alignMinYMinXOffset(PAreaB, PAreaB,
   * BigInteger, BigInteger)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignMinYMinX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignMinYMinXOffset(outer, inner, BigInteger.ZERO, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignMinYMinXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset_x,
    final BigInteger offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_min = outer.minimumX().add(offset_x);
    final BigInteger y_min = outer.minimumY().add(offset_y);
    final BigInteger y_max = y_min.add(inner.height());
    final BigInteger x_max = x_min.add(inner.width());
    return PAreaB.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMinYMaxXOffset(PAreaB, PAreaB,
   * BigInteger, BigInteger)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignMinYMaxX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignMinYMaxXOffset(outer, inner, BigInteger.ZERO, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignMinYMaxXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset_x,
    final BigInteger offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_max = outer.maximumX().subtract(offset_x);
    final BigInteger y_min = outer.minimumY().add(offset_y);
    final BigInteger y_max = y_min.add(inner.height());
    final BigInteger x_min = x_max.subtract(inner.width());
    return PAreaB.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMinXOffset(PAreaB, PAreaB,
   * BigInteger, BigInteger)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignMaxYMinX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignMaxYMinXOffset(outer, inner, BigInteger.ZERO, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignMaxYMinXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset_x,
    final BigInteger offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_min = outer.minimumX().add(offset_x);
    final BigInteger y_max = outer.maximumY().subtract(offset_y);
    final BigInteger y_min = y_max.subtract(inner.height());
    final BigInteger x_max = x_min.add(inner.width());
    return PAreaB.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMaxXOffset(PAreaB, PAreaB,
   * BigInteger, BigInteger)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param <S>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static <S> PAreaB<S> alignMaxYMaxX(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
  {
    return alignMaxYMaxXOffset(outer, inner, BigInteger.ZERO, BigInteger.ZERO);
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

  public static <S> PAreaB<S> alignMaxYMaxXOffset(
    final PAreaB<S> outer,
    final PAreaB<S> inner,
    final BigInteger offset_x,
    final BigInteger offset_y)
  {
    NullCheck.notNull(outer);
    NullCheck.notNull(inner);

    final BigInteger x_max = outer.maximumX().subtract(offset_x);
    final BigInteger y_max = outer.maximumY().subtract(offset_y);
    final BigInteger y_min = y_max.subtract(inner.height());
    final BigInteger x_min = x_max.subtract(inner.width());
    return PAreaB.of(x_min, x_max, y_min, y_max);
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

  public static <S> PAreaB<S> alignCenter(
    final PAreaB<S> outer,
    final PAreaB<S> inner)
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

  public static <S> PAreaB<S> hollowOut(
    final PAreaB<S> outer,
    final BigInteger min_x_offset,
    final BigInteger max_x_offset,
    final BigInteger min_y_offset,
    final BigInteger max_y_offset)
  {
    NullCheck.notNull(outer);

    final BigInteger x_min =
      clamp(
        outer.minimumX().add(min_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final BigInteger x_max =
      clamp(
        outer.maximumX().subtract(max_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final BigInteger y_min =
      clamp(
        outer.minimumY().add(min_y_offset),
        outer.minimumY(),
        outer.maximumY());
    final BigInteger y_max =
      clamp(
        outer.maximumY().subtract(max_y_offset),
        outer.minimumY(),
        outer.maximumY());

    final BigInteger out_x_max = maximum(x_min, x_max);
    final BigInteger out_y_max = maximum(y_min, y_max);

    return PAreaB.of(x_min, out_x_max, y_min, out_y_max);
  }

  /**
   * Equivalent to calling {@link #hollowOut(PAreaB, BigInteger, BigInteger,
   * BigInteger, BigInteger)} with {@code offset} for all offset parameters.
   *
   * @param outer  The containing area
   * @param offset The offset from each edge (must be non-negative)
   * @param <S>    The coordinate space of the areas
   *
   * @return A new area
   */

  public static <S> PAreaB<S> hollowOutEvenly(
    final PAreaB<S> outer,
    final BigInteger offset)
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

  public static <S> PAreaB<S> setSizeFromCenter(
    final PAreaB<S> area,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(area);

    return alignCenter(area, PAreaB.of(
      area.minimumX(),
      area.minimumX().add(width),
      area.minimumY(),
      area.minimumY().add(height)));
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

  public static <S> PAreaB<S> setSizeFromMinYMinX(
    final PAreaB<S> area,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(area);

    return alignMaxYMaxX(area, PAreaB.of(
      area.minimumX(),
      area.minimumX().add(width),
      area.minimumY(),
      area.minimumY().add(height)));
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

  public static <S> PAreaB<S> setSizeFromMinYMaxX(
    final PAreaB<S> area,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(area);

    return alignMaxYMinX(area, PAreaB.of(
      area.minimumX(),
      area.minimumX().add(width),
      area.minimumY(),
      area.minimumY().add(height)));
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

  public static <S> PAreaB<S> setSizeFromMaxYMaxX(
    final PAreaB<S> area,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(area);

    return alignMinYMinX(area, PAreaB.of(
      area.minimumX(),
      area.minimumX().add(width),
      area.minimumY(),
      area.minimumY().add(height)));
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

  public static <S> PAreaB<S> setSizeFromMaxYMinX(
    final PAreaB<S> area,
    final BigInteger width,
    final BigInteger height)
  {
    NullCheck.notNull(area);

    return alignMinYMaxX(area, PAreaB.of(
      area.minimumX(),
      area.minimumX().add(width),
      area.minimumY(),
      area.minimumY().add(height)));
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

  public static <S> PAreaB<S> scaleFromMinYMinX(
    final PAreaB<S> area,
    final BigInteger x_diff,
    final BigInteger y_diff)
  {
    NullCheck.notNull(area);

    final BigInteger width = maximum(BigInteger.ZERO, area.width().add(x_diff));
    final BigInteger height = maximum(
      BigInteger.ZERO,
      area.height().add(y_diff));
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

  public static <S> PAreaB<S> scaleFromMinYMaxX(
    final PAreaB<S> area,
    final BigInteger x_diff,
    final BigInteger y_diff)
  {
    NullCheck.notNull(area);

    final BigInteger width = maximum(BigInteger.ZERO, area.width().add(x_diff));
    final BigInteger height = maximum(
      BigInteger.ZERO,
      area.height().add(y_diff));
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

  public static <S> PAreaB<S> scaleFromMaxYMinX(
    final PAreaB<S> area,
    final BigInteger x_diff,
    final BigInteger y_diff)
  {
    NullCheck.notNull(area);

    final BigInteger width = maximum(BigInteger.ZERO, area.width().add(x_diff));
    final BigInteger height = maximum(
      BigInteger.ZERO,
      area.height().add(y_diff));
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

  public static <S> PAreaB<S> scaleFromMaxYMaxX(
    final PAreaB<S> area,
    final BigInteger x_diff,
    final BigInteger y_diff)
  {
    NullCheck.notNull(area);

    final BigInteger width = maximum(BigInteger.ZERO, area.width().add(x_diff));
    final BigInteger height = maximum(
      BigInteger.ZERO,
      area.height().add(y_diff));
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

  public static <S> PAreaB<S> scaleFromCenter(
    final PAreaB<S> area,
    final BigInteger x_diff,
    final BigInteger y_diff)
  {
    NullCheck.notNull(area);

    final BigInteger width = maximum(BigInteger.ZERO, area.width().add(x_diff));
    final BigInteger height = maximum(
      BigInteger.ZERO,
      area.height().add(y_diff));
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
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    if (a.minimumX().compareTo(b.maximumX()) >= 0 || a.maximumX().compareTo(b.minimumX()) < 0) {
      return false;
    }
    if (a.minimumY().compareTo(b.maximumY()) >= 0 || a.maximumY().compareTo(b.minimumY()) < 0) {
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
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);
    final boolean width_ok = a.width().compareTo(b.width()) <= 0;
    final boolean height_ok = a.height().compareTo(b.height()) <= 0;
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

  public static <S> PAreaB<S> containing(
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    return PAreaB.of(
      minimum(a.minimumX(), b.minimumX()),
      maximum(a.maximumX(), b.maximumX()),
      minimum(a.minimumY(), b.minimumY()),
      maximum(a.maximumY(), b.maximumY()));
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
    final PAreaB<S> a,
    final BigInteger x,
    final BigInteger y)
  {
    NullCheck.notNull(a);

    final boolean contain_x =
      x.compareTo(a.minimumX()) >= 0 && x.compareTo(a.maximumX()) < 0;
    final boolean contain_y =
      y.compareTo(a.minimumY()) >= 0 && y.compareTo(a.maximumY()) < 0;
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

  public static <S> PAreaB<S> fitBetweenHorizontal(
    final PAreaB<S> fit,
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final BigInteger x_min = minimum(a.maximumX(), b.maximumX());
    final BigInteger x_max = maximum(a.minimumX(), b.minimumX());
    final BigInteger out_x_min = minimum(x_min, x_max);
    final BigInteger out_x_max = maximum(x_min, x_max);
    return PAreaB.of(out_x_min, out_x_max, fit.minimumY(), fit.maximumY());
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

  public static <S> PAreaB<S> fitBetweenVertical(
    final PAreaB<S> fit,
    final PAreaB<S> a,
    final PAreaB<S> b)
  {
    NullCheck.notNull(fit);
    NullCheck.notNull(a);
    NullCheck.notNull(b);

    final BigInteger y_min = minimum(a.maximumY(), b.maximumY());
    final BigInteger y_max = maximum(a.minimumY(), b.minimumY());
    final BigInteger out_y_min = minimum(y_min, y_max);
    final BigInteger out_y_max = maximum(y_min, y_max);
    return PAreaB.of(fit.minimumX(), fit.maximumX(), out_y_min, out_y_max);
  }

  /**
   * Split {@code area} aBigInteger a line parallel to the X axis placed at
   * {@code y} units from its own minimum-y edge.
   *
   * @param area The area to be split
   * @param y    The relative Y coordinate of the splitting edge
   * @param <S>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static <S> PAreaXSplitB<S, PAreaB<S>> splitABigIntegerParallelToX(
    final PAreaB<S> area,
    final BigInteger y)
  {
    NullCheck.notNull(area);

    final BigInteger clamped_height = minimum(area.height(), y);
    final BigInteger upper_y_min = area.minimumY();
    final BigInteger upper_y_max = area.minimumY().add(clamped_height);
    final BigInteger lower_y_min = upper_y_max;
    final BigInteger lower_y_max = area.maximumY();

    final PAreaB<S> lower = PAreaB.of(
      area.minimumX(), area.maximumX(), lower_y_min, lower_y_max);
    final PAreaB<S> upper = PAreaB.of(
      area.minimumX(), area.maximumX(), upper_y_min, upper_y_max);

    return PAreaXSplitB.of(lower, upper);
  }

  /**
   * Split {@code area} aBigInteger a line parallel to the Y axis placed at
   * {@code x} units from its own minimum-x edge.
   *
   * @param area The area to be split
   * @param x    The relative X coordinate of the splitting edge
   * @param <S>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static <S> PAreaYSplitB<S, PAreaB<S>> splitABigIntegerParallelToY(
    final PAreaB<S> area,
    final BigInteger x)
  {
    NullCheck.notNull(area);

    final BigInteger clamped_width = minimum(area.width(), x);
    final BigInteger lower_x_min = area.minimumX();
    final BigInteger lower_x_max = area.minimumX().add(clamped_width);
    final BigInteger upper_x_min = lower_x_max;
    final BigInteger upper_x_max = area.maximumX();

    final PAreaB<S> lower = PAreaB.of(
      lower_x_min, lower_x_max, area.minimumY(), area.maximumY());
    final PAreaB<S> upper = PAreaB.of(
      upper_x_min, upper_x_max, area.minimumY(), area.maximumY());

    return PAreaYSplitB.of(lower, upper);
  }

  /**
   * @param area The area
   * @param <S>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static <S> String show(
    final PAreaB<S> area)
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
    final PAreaB<S> area,
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
