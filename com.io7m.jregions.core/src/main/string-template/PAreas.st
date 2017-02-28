PAreas(scalarType,areaType,className,splitXType,splitYType,opClass) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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
 * \<p\>Functions over areas.\</p\>
 *
 * \<p\>These functions operate using the concepts of \<i\>minimum-x\</i\>,
 * \<i\>maximum-x\</i\>, \<i\>minimum-y\</i\>, and \<i\>maximum-y\</i\> edges. It is up to
 * individual applications to assign meaning to these edges such as "left" for
 * \<i\>minimum-x\</i\> and "top" for \<i\>minimum-y\</i\>.\</p\>
 */

public final class <className>
{
  private <className>()
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
   * @param \<S\>    The coordinate space of the area
   *
   * @return An area
   */

  public static \<S\> <areaType>\<S\> create(
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(height, "height");

    return <areaType>.of(
      x,
      <opClass>.add(x, width),
      y,
      <opClass>.add(y, height));
  }

  /**
   * \<p\>Determine whether or not one area contains another.\</p\>
   *
   * \<p\>Containing is reflexive: {@code contains(a, a) == true}.\</p\>
   *
   * \<p\>Containing is transitive: {@code contains(a, b) → contains(b, c) →
   * contains(a, c)}.\</p\>
   *
   * \<p\>Containing is not necessarily symmetric.\</p\>
   *
   * @param a   Area A
   * @param b   Area B
   * @param \<S\> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} contains {@code b}
   */

  public static \<S\> boolean contains(
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(a, "Area A");
    <opClass>.notNullArea(b, "Area B");

    final boolean contain_x =
      <opClass>.compare(b.minimumX(), a.minimumX()) \>= 0
        && <opClass>.compare(b.maximumX(), a.maximumX()) \<= 0;

    final boolean contain_y =
      <opClass>.compare(b.minimumY(), a.minimumY()) \>= 0
        && <opClass>.compare(b.maximumY(), a.maximumY()) \<= 0;

    return contain_x && contain_y;
  }

  /**
   * Move the given area by {@code (x, y)}.
   *
   * @param area The area
   * @param x    The amount to move on the X axis
   * @param y    The amount to move on the Y axis
   * @param \<S\>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static \<S\> <areaType>\<S\> moveRelative(
    final <areaType>\<S\> area,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "Area");

    final <scalarType> x_min = <opClass>.add(area.minimumX(), x);
    final <scalarType> x_max = <opClass>.add(area.maximumX(), x);
    final <scalarType> y_min = <opClass>.add(area.minimumY(), y);
    final <scalarType> y_max = <opClass>.add(area.maximumY(), y);
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Move the given area to {@code (x, y)}.
   *
   * @param area The area
   * @param x    The position to which to move on the X axis
   * @param y    The position to which to move on the Y axis
   * @param \<S\>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static \<S\> <areaType>\<S\> moveAbsolute(
    final <areaType>\<S\> area,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "Area");
    return create(x, y, area.width(), area.height());
  }

  /**
   * Move the given area to {@code (0, 0)}.
   *
   * @param area The area
   * @param \<S\>  The coordinate space of the area
   *
   * @return A moved area
   */

  public static \<S\> <areaType>\<S\> moveToOrigin(
    final <areaType>\<S\> area)
  {
    <opClass>.notNullArea(area, "Area");
    return create(
      <opClass>.ZERO,
      <opClass>.ZERO,
      area.width(),
      area.height());
  }

  private static <scalarType> clamp(
    final <scalarType> x,
    final <scalarType> minimum,
    final <scalarType> maximum)
  {
    Preconditions.checkPrecondition(
      <opClass>.compare(maximum, minimum) \>= 0,
      "Maximum \>= minimum");
    return <opClass>.maximum(<opClass>.minimum(x, maximum), minimum);
  }

  /**
   * Brand a given area as be<scalarType>ing to a different coordinate space.
   * Mixing up coordinate spaces is a common source of difficult-to-locate bugs.
   * Use at your own risk.
   *
   * @param area An area
   * @param \<S\>  The starting coordinate space
   * @param \<T\>  The resulting coordinate space
   *
   * @return {@code area}
   */

  @SuppressWarnings("unchecked")
  public static \<S, T\> <areaType>\<T\> cast(
    final <areaType>\<S\> area)
  {
    <opClass>.notNullArea(area, "area");
    return (<areaType>\<T\>) area;
  }


  /**
   * Align the area {@code inner} horizontally in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignHorizontallyCenter(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> outer_width = outer.width();
    final <scalarType> inner_width = inner.width();
    final <scalarType> xm0 =
      <opClass>.add(outer.minimumX(), <opClass>.divide(outer_width, <opClass>.constant(2L)));
    final <scalarType> xm1 =
    <opClass>.subtract(xm0, <opClass>.divide(inner_width, <opClass>.constant(2L)));
    return create(xm1, inner.minimumY(), inner_width, inner.height());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMinXOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignHorizontallyMinX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignHorizontallyMinXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} horizontally against the inside minimum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param \<S\>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignHorizontallyMinXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset);
    final <scalarType> x_max = <opClass>.add(x_min, inner.width());
    return <areaType>.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMaxXOffset(<areaType>,
   * <areaType>, <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignHorizontallyMaxX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignHorizontallyMaxXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} horizontally against the inside maximum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param \<S\>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignHorizontallyMaxXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset);
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.width());
    return <areaType>.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMinYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignVerticallyMinY(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignVerticallyMinYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} vertically against the inside minimum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param \<S\>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignVerticallyMinYOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset);
    final <scalarType> y_max = <opClass>.add(y_min, inner.height());
    return <areaType>.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMaxYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignVerticallyMaxY(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignVerticallyMaxYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} vertically against the inside maximum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   * @param \<S\>    The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignVerticallyMaxYOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.height());
    return <areaType>.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Align the area {@code inner} vertically in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignVerticallyCenter(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> outer_height = outer.height();
    final <scalarType> inner_height = inner.height();

    final <scalarType> ym0 =
      <opClass>.add(outer.minimumY(), <opClass>.divide(outer_height, <opClass>.constant(2L)));
    final <scalarType> ym1 =
      <opClass>.subtract(ym0, <opClass>.divide(inner_height, <opClass>.constant(2L)));
    return create(inner.minimumX(), ym1, inner.width(), inner_height);
  }

  /**
   * Equivalent to calling {@link #alignMinYMinXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMinYMinX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignMinYMinXOffset(outer, inner, <opClass>.ZERO, <opClass>.ZERO);
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
   * @param \<S\>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMinYMinXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset_x);
    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset_y);
    final <scalarType> y_max = <opClass>.add(y_min, inner.height());
    final <scalarType> x_max = <opClass>.add(x_min, inner.width());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMinYMaxXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMinYMaxX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignMinYMaxXOffset(outer, inner, <opClass>.ZERO, <opClass>.ZERO);
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
   * @param \<S\>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMinYMaxXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset_x);
    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset_y);
    final <scalarType> y_max = <opClass>.add(y_min, inner.height());
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.width());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMinXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMaxYMinX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignMaxYMinXOffset(outer, inner, <opClass>.ZERO, <opClass>.ZERO);
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
   * @param \<S\>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMaxYMinXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset_x);
    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset_y);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.height());
    final <scalarType> x_max = <opClass>.add(x_min, inner.width());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMaxXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMaxYMaxX(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    return alignMaxYMaxXOffset(outer, inner, <opClass>.ZERO, <opClass>.ZERO);
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
   * @param \<S\>      The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignMaxYMaxXOffset(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset_x);
    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset_y);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.height());
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.width());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Align the area {@code inner} such that the center of the area is equal to
   * the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * @param \<S\>   The coordinate space of the areas
   *
   * @return An aligned area
   */

  public static \<S\> <areaType>\<S\> alignCenter(
    final <areaType>\<S\> outer,
    final <areaType>\<S\> inner)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

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
   * @param \<S\>          The coordinate space of the areas
   *
   * @return A new area
   */

  public static \<S\> <areaType>\<S\> hollowOut(
    final <areaType>\<S\> outer,
    final <scalarType> min_x_offset,
    final <scalarType> max_x_offset,
    final <scalarType> min_y_offset,
    final <scalarType> max_y_offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullScalar(min_x_offset, "offset min-x");
    <opClass>.notNullScalar(max_x_offset, "offset max-x");
    <opClass>.notNullScalar(min_y_offset, "offset min-y");
    <opClass>.notNullScalar(max_y_offset, "offset max-y");

    final <scalarType> x_min =
      clamp(
        <opClass>.add(outer.minimumX(), min_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final <scalarType> x_max =
      clamp(
        <opClass>.subtract(outer.maximumX(), max_x_offset),
        outer.minimumX(),
        outer.maximumX());
    final <scalarType> y_min =
      clamp(
        <opClass>.add(outer.minimumY(), min_y_offset),
        outer.minimumY(),
        outer.maximumY());
    final <scalarType> y_max =
      clamp(
        <opClass>.subtract(outer.maximumY(), max_y_offset),
        outer.minimumY(),
        outer.maximumY());

    final <scalarType> out_x_max = <opClass>.maximum(x_min, x_max);
    final <scalarType> out_y_max = <opClass>.maximum(y_min, y_max);

    return <areaType>.of(x_min, out_x_max, y_min, out_y_max);
  }

  /**
   * Equivalent to calling {@link #hollowOut(<areaType>, <scalarType>, <scalarType>,
   * <scalarType>, <scalarType>)} with {@code offset} for all offset parameters.
   *
   * @param outer  The containing area
   * @param offset The offset from each edge (must be non-negative)
   * @param \<S\>    The coordinate space of the areas
   *
   * @return A new area
   */

  public static \<S\> <areaType>\<S\> hollowOutEvenly(
    final <areaType>\<S\> outer,
    final <scalarType> offset)
  {
    return hollowOut(outer, offset, offset, offset, offset);
  }

  /**
   * \<p\>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.\</p\>
   *
   * \<p\>The area is resized from its own center.\</p\>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> setSizeFromCenter(
    final <areaType>\<S\> area,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(width, "height");

    return alignCenter(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), width),
      area.minimumY(),
      <opClass>.add(area.minimumY(), height)));
  }

  /**
   * \<p\>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.\</p\>
   *
   * \<p\>The area is resized by moving its minimum-y-minimum-x corner.\</p\>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> setSizeFromMinYMinX(
    final <areaType>\<S\> area,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(width, "height");

    return alignMaxYMaxX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), width),
      area.minimumY(),
      <opClass>.add(area.minimumY(), height)));
  }

  /**
   * \<p\>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.\</p\>
   *
   * \<p\>The area is resized by moving its minimum-y-maximum-x corner.\</p\>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> setSizeFromMinYMaxX(
    final <areaType>\<S\> area,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(width, "height");

    return alignMaxYMinX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), width),
      area.minimumY(),
      <opClass>.add(area.minimumY(), height)));
  }

  /**
   * \<p\>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.\</p\>
   *
   * \<p\>The area is resized by moving its maximum-y-maximum-x corner.\</p\>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> setSizeFromMaxYMaxX(
    final <areaType>\<S\> area,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(width, "height");

    return alignMinYMinX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), width),
      area.minimumY(),
      <opClass>.add(area.minimumY(), height)));
  }

  /**
   * \<p\>Set the width and height of {@code area} to {@code width} and {@code
   * height}, respectively.\</p\>
   *
   * \<p\>The area is resized by moving its maximum-y-minimum-x corner.\</p\>
   *
   * @param area   The area
   * @param width  The new width (must be non-negative)
   * @param height The new height (must be non-negative)
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> setSizeFromMaxYMinX(
    final <areaType>\<S\> area,
    final <scalarType> width,
    final <scalarType> height)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(width, "width");
    <opClass>.notNullScalar(width, "height");

    return alignMinYMaxX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), width),
      area.minimumY(),
      <opClass>.add(area.minimumY(), height)));
  }

  /**
   * \<p\>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.\</p\>
   *
   * \<p\>The area is resized by moving its minimum-y-minimum-x corner.\</p\>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> scaleFromMinYMinX(
    final <areaType>\<S\> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> width =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.width(), x_diff));
    final <scalarType> height =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.height(), y_diff));
    return setSizeFromMinYMinX(area, width, height);
  }

  /**
   * \<p\>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.\</p\>
   *
   * \<p\>The area is resized by moving its minimum-y-maximum-x corner.\</p\>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> scaleFromMinYMaxX(
    final <areaType>\<S\> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> width =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.width(), x_diff));
    final <scalarType> height =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.height(), y_diff));
    return setSizeFromMinYMaxX(area, width, height);
  }

  /**
   * \<p\>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.\</p\>
   *
   * \<p\>The area is resized by moving its maximum-y-minimum-x corner.\</p\>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> scaleFromMaxYMinX(
    final <areaType>\<S\> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> width =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.width(), x_diff));
    final <scalarType> height =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.height(), y_diff));
    return setSizeFromMaxYMinX(area, width, height);
  }

  /**
   * \<p\>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.\</p\>
   *
   * \<p\>The area is resized by moving its maximum-y-maximum-x corner.\</p\>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> scaleFromMaxYMaxX(
    final <areaType>\<S\> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> width =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.width(), x_diff));
    final <scalarType> height =
      <opClass>.maximum(<opClass>.ZERO,<opClass>.add(area.height(), y_diff));
    return setSizeFromMaxYMaxX(area, width, height);
  }

  /**
   * \<p\>Scale {@code area} by adding {@code x_diff} to the width, and {@code
   * y_diff} to the height. The size of the resulting area is clamped so that
   * its width and height are always non-negative.\</p\>
   *
   * \<p\>The area is resized from its own center.\</p\>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * @param \<S\>    The coordinate space of the area
   *
   * @return A resized area
   */

  public static \<S\> <areaType>\<S\> scaleFromCenter(
    final <areaType>\<S\> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> width =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.width(), x_diff));
    final <scalarType> height =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.height(), y_diff));
    return setSizeFromCenter(area, width, height);
  }

  /**
   * \<p\>Determine whether or not two areas overlap.\</p\>
   *
   * \<p\>Overlapping is reflexive: {@code overlaps(a, a) == true}.\</p\>
   *
   * \<p\>Overlapping is symmetric: {@code overlaps(a, b) == contains(b, a)}.\</p\>
   *
   * \<p\>Overlapping is not necessarily transitive.\</p\>
   *
   * @param a   An area
   * @param b   An area
   * @param \<S\> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} overlaps {@code b}
   */

  public static \<S\> boolean overlaps(
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    if (<opClass>.compare(a.minimumX(), b.maximumX()) \>= 0 || <opClass>.compare(a.maximumX(), b.minimumX()) \< 0) {
      return false;
    }
    if (<opClass>.compare(a.minimumY(), b.maximumY()) \>= 0 || <opClass>.compare(a.maximumY(), b.minimumY()) \< 0) {
      return false;
    }

    return true;
  }

  /**
   * \<p\>Determine whether or not one area could fit inside another.\</p\>
   *
   * \<p\>Fitting is reflexive: {@code couldFitInside(a, a) == true}.\</p\>
   *
   * \<p\>Fitting is transitive: {@code couldFitInside(a, b) → couldFitInside(b,
   * c) → couldFitInside(a, c)}.\</p\>
   *
   * \<p\>Fitting is not necessarily symmetric.\</p\>
   *
   * @param a   An area
   * @param b   An area
   * @param \<S\> The coordinate space of the areas
   *
   * @return {@code true} iff {@code a} could fit inside {@code b}
   */

  public static \<S\> boolean couldFitInside(
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    final boolean width_ok =
    <opClass>.compare(a.width(), b.width()) \<= 0;
    final boolean height_ok =
    <opClass>.compare(a.height(), b.height()) \<= 0;
    return width_ok && height_ok;
  }

  /**
   * Construct a area that will contain both {@code a} and {@code b}.
   *
   * @param a   An area
   * @param b   An area
   * @param \<S\> The coordinate space of the areas
   *
   * @return An area containing {@code a} and {@code b}
   */

  public static \<S\> <areaType>\<S\> containing(
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    return <areaType>.of(
      <opClass>.minimum(a.minimumX(), b.minimumX()),
      <opClass>.maximum(a.maximumX(), b.maximumX()),
      <opClass>.minimum(a.minimumY(), b.minimumY()),
      <opClass>.maximum(a.maximumY(), b.maximumY()));
  }

  /**
   * \<p\>Determine whether or not a area contains a given point.\</p\>
   *
   * @param a   An area
   * @param x   The X coordinate of the point
   * @param y   The Y coordinate of the point
   * @param \<S\> The coordinate space of the area
   *
   * @return {@code true} iff {@code a} contains {@code (x, y)}
   */

  public static \<S\> boolean containsPoint(
    final <areaType>\<S\> a,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(a, "area");
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");

    final boolean contain_x =
      <opClass>.compare(x, a.minimumX()) \>= 0 && <opClass>.compare(x, a.maximumX()) \< 0;
    final boolean contain_y =
      <opClass>.compare(y, a.minimumY()) \>= 0 && <opClass>.compare(y, a.maximumY()) \< 0;
    return contain_x && contain_y;
  }

  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, horizontally.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * @param \<S\> The coordinate space of the areas
   *
   * @return A fitted area
   */

  public static \<S\> <areaType>\<S\> fitBetweenHorizontal(
    final <areaType>\<S\> fit,
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(fit, "fit");
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    final <scalarType> x_min = <opClass>.minimum(a.maximumX(), b.maximumX());
    final <scalarType> x_max = <opClass>.maximum(a.minimumX(), b.minimumX());
    final <scalarType> out_x_min = <opClass>.minimum(x_min, x_max);
    final <scalarType> out_x_max = <opClass>.maximum(x_min, x_max);
    return <areaType>.of(out_x_min, out_x_max, fit.minimumY(), fit.maximumY());
  }

  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, vertically.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * @param \<S\> The coordinate space of the areas
   *
   * @return A fitted area
   */

  public static \<S\> <areaType>\<S\> fitBetweenVertical(
    final <areaType>\<S\> fit,
    final <areaType>\<S\> a,
    final <areaType>\<S\> b)
  {
    <opClass>.notNullArea(fit, "fit");
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    final <scalarType> y_min = <opClass>.minimum(a.maximumY(), b.maximumY());
    final <scalarType> y_max = <opClass>.maximum(a.minimumY(), b.minimumY());
    final <scalarType> out_y_min = <opClass>.minimum(y_min, y_max);
    final <scalarType> out_y_max = <opClass>.maximum(y_min, y_max);
    return <areaType>.of(fit.minimumX(), fit.maximumX(), out_y_min, out_y_max);
  }

  /**
   * Split {@code area} along a line parallel to the X axis placed at
   * {@code y} units from its own minimum-y edge.
   *
   * @param area The area to be split
   * @param y    The relative Y coordinate of the splitting edge
   * @param \<S\>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static \<S\> <splitXType>\<S, <areaType>\<S\>\> splitAlongParallelToX(
    final <areaType>\<S\> area,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(y, "y");

    final <scalarType> clamped_height = <opClass>.minimum(area.height(), y);
    final <scalarType> upper_y_min = area.minimumY();
    final <scalarType> upper_y_max = <opClass>.add(area.minimumY(), clamped_height);
    final <scalarType> lower_y_min = upper_y_max;
    final <scalarType> lower_y_max = area.maximumY();

    final <areaType>\<S\> lower = <areaType>.of(
      area.minimumX(), area.maximumX(), lower_y_min, lower_y_max);
    final <areaType>\<S\> upper = <areaType>.of(
      area.minimumX(), area.maximumX(), upper_y_min, upper_y_max);

    return <splitXType>.of(lower, upper);
  }

  /**
   * Split {@code area} along a line parallel to the Y axis placed at
   * {@code x} units from its own minimum-x edge.
   *
   * @param area The area to be split
   * @param x    The relative X coordinate of the splitting edge
   * @param \<S\>  The coordinate space of the areas
   *
   * @return A pair of areas
   */

  public static \<S\> <splitYType>\<S, <areaType>\<S\>\> splitAlongParallelToY(
    final <areaType>\<S\> area,
    final <scalarType> x)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x, "x");

    final <scalarType> clamped_width = <opClass>.minimum(area.width(), x);
    final <scalarType> lower_x_min = area.minimumX();
    final <scalarType> lower_x_max = <opClass>.add(area.minimumX(), clamped_width);
    final <scalarType> upper_x_min = lower_x_max;
    final <scalarType> upper_x_max = area.maximumX();

    final <areaType>\<S\> lower = <areaType>.of(
      lower_x_min, lower_x_max, area.minimumY(), area.maximumY());
    final <areaType>\<S\> upper = <areaType>.of(
      upper_x_min, upper_x_max, area.minimumY(), area.maximumY());

    return <splitYType>.of(lower, upper);
  }

  /**
   * @param area The area
   * @param \<S\>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static \<S\> String show(
    final <areaType>\<S\> area)
  {
    <opClass>.notNullArea(area, "area");

    final StringBuilder sb = new StringBuilder(128);
    return showToBuilder(area, sb);
  }

  /**
   * @param area The area
   * @param sb   A string builder
   * @param \<S\>  The coordinate space of the area
   *
   * @return A terse string describing the position and size of the area
   */

  public static \<S\> String showToBuilder(
    final <areaType>\<S\> area,
    final StringBuilder sb)
  {
    <opClass>.notNullArea(area, "area");
    NullCheck.notNull(sb, "sb");

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

>>