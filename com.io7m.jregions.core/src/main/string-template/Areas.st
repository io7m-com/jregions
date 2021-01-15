Areas(
  scalarType,
  areaType,
  areaSizeType,
  className,
  splitXType,
  splitYType,
  splitXYType,
  opClass) ::= <<
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

package com.io7m.jregions.core.unparameterized.areas;

import java.util.Objects;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over areas.\</p>
 *
 * \<p>These functions operate using the concepts of \<i>minimum-x\</i>,
 * \<i>maximum-x\</i>, \<i>minimum-y\</i>, and \<i>maximum-y\</i> edges. It is up to
 * individual applications to assign meaning to these edges such as "left" for
 * \<i>minimum-x\</i> and "top" for \<i>minimum-y\</i>.\</p>
 */

public final class <className>
{
  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Create an area of size {@code size_x} on the X axis and size {@code size_y}
   * on the Y axis, placing the minimum corner at {@code (x, y)}.
   *
   * @param x      The X value of the minimum corner
   * @param y      The Y value of the minimum corner
   * @param size_x The size of the area on the X axis
   * @param size_y The size of the area on the Y axis
   *
   * @return An area
   */

  public static <areaType> create(
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> size_x,
    final <scalarType> size_y)
  {
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");
    <opClass>.notNullScalar(size_x, "size_x");
    <opClass>.notNullScalar(size_y, "size_y");

    return <areaType>.of(
      x,
      <opClass>.add(x, size_x),
      y,
      <opClass>.add(y, size_y));
  }

  /**
   * \<p>Determine whether or not one area contains another.\</p>
   *
   * \<p>Containing is reflexive: {@code contains(a, a) == true}.\</p>
   *
   * \<p>Containing is transitive: {@code contains(a, b) → contains(b, c) →
   * contains(a, c)}.\</p>
   *
   * \<p>Containing is not necessarily symmetric.\</p>
   *
   * @param a   Area A
   * @param b   Area B
   * 
   *
   * @return {@code true} iff {@code a} contains {@code b}
   */

  public static boolean contains(
    final <areaType> a,
    final <areaType> b)
  {
    <opClass>.notNullArea(a, "Area A");
    <opClass>.notNullArea(b, "Area B");

    final <scalarType> a_x0 = a.minimumX();
    final <scalarType> a_x1 = a.maximumX();
    final <scalarType> a_y0 = a.minimumY();
    final <scalarType> a_y1 = a.maximumY();
    final <scalarType> b_x0 = b.minimumX();
    final <scalarType> b_x1 = b.maximumX();
    final <scalarType> b_y0 = b.minimumY();
    final <scalarType> b_y1 = b.maximumY();

    final boolean c0 = <opClass>.compare(b_x0, a_x0) >= 0;
    final boolean c1 = <opClass>.compare(b_x1, a_x1) \<= 0;
    final boolean c2 = <opClass>.compare(b_y0, a_y0) >= 0;
    final boolean c3 = <opClass>.compare(b_y1, a_y1) \<= 0;

    return c0 && c1 && c2 && c3;
  }

  /**
   * Move the given area by {@code (x, y)}.
   *
   * @param area The area
   * @param x    The amount to move on the X axis
   * @param y    The amount to move on the Y axis
   * 
   *
   * @return A moved area
   */

  public static <areaType> moveRelative(
    final <areaType> area,
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
   * Move the given area by {@code x}, without allowing {@code area}
   * to leave {@code container}.
   *
   * @param container The container area
   * @param area      The area
   * @param x         The amount to move on the X axis
   *
   * @since 3.0.0
   * @return A moved area
   */

  public static <areaType> moveRelativeClampedX(
    final <areaType> container,
    final <areaType> area,
    final <scalarType> x)
  {
    <opClass>.notNullArea(container, "Container");
    <opClass>.notNullArea(area, "Area");

    Preconditions.checkPrecondition(
      contains(container, area),
      "Container must contain area");

    final var sizeX = area.sizeX();
    final var newMinY = area.minimumY();
    final var newMaxY = area.maximumY();
    final var movedOnX = moveRelative(area, x, <opClass>.ZERO);
    if (!contains(container, movedOnX)) {
      if (<opClass>.compare(x, <opClass>.ZERO) \< 0) {
        final var newMinX = container.minimumX();
        final var newMaxX = <opClass>.minimum(container.maximumX(), <opClass>.add(newMinX, sizeX));
        return <areaType>.of(newMinX, newMaxX, newMinY, newMaxY);
      } else {
        final var newMinX = <opClass>.maximum(container.minimumX(), <opClass>.subtract(container.maximumX(), sizeX));
        final var newMaxX = container.maximumX();
        return <areaType>.of(newMinX, newMaxX, newMinY, newMaxY);
      }
    }
    return movedOnX;
  }

  /**
   * Move the given area by {@code y}, without allowing {@code area}
   * to leave {@code container}.
   *
   * @param container The container area
   * @param area      The area
   * @param y         The amount to move on the Y axis
   *
   * @since 3.0.0
   * @return A moved area
   */

  public static <areaType> moveRelativeClampedY(
    final <areaType> container,
    final <areaType> area,
    final <scalarType> y)
  {
    <opClass>.notNullArea(container, "Container");
    <opClass>.notNullArea(area, "Area");

    Preconditions.checkPrecondition(
      contains(container, area),
      "Container must contain area");

    final var sizeY = area.sizeY();
    final var newMinX = area.minimumX();
    final var newMaxX = area.maximumX();
    final var movedOnY = moveRelative(area, <opClass>.ZERO, y);
    if (!contains(container, movedOnY)) {
      if (<opClass>.compare(y, <opClass>.ZERO) \< 0) {
        final var newMinY = container.minimumY();
        final var newMaxY = <opClass>.minimum(container.maximumY(), <opClass>.add(newMinY, sizeY));
        return <areaType>.of(newMinX, newMaxX, newMinY, newMaxY);
      } else {
        final var newMinY = <opClass>.maximum(container.minimumY(), <opClass>.subtract(container.maximumY(), sizeY));
        final var newMaxY = container.maximumY();
        return <areaType>.of(newMinX, newMaxX, newMinY, newMaxY);
      }
    }
    return movedOnY;
  }

  /**
   * Move the given area by {@code (x, y)}, without allowing {@code area}
   * to leave {@code container}.
   *
   * @param container The container area
   * @param area      The area
   * @param x         The amount to move on the X axis
   * @param y         The amount to move on the Y axis
   *
   * @since 3.0.0
   * @return A moved area
   */

  public static <areaType> moveRelativeClamped(
    final <areaType> container,
    final <areaType> area,
    final <scalarType> x,
    final <scalarType> y)
  {
    final var m0 = moveRelativeClampedX(container, area, x);
    return moveRelativeClampedY(container, m0, y);
  }

  /**
   * Move the given area to {@code (x, y)}.
   *
   * @param area The area
   * @param x    The position to which to move on the X axis
   * @param y    The position to which to move on the Y axis
   * 
   *
   * @return A moved area
   */

  public static <areaType> moveAbsolute(
    final <areaType> area,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "Area");
    return create(x, y, area.sizeX(), area.sizeY());
  }

  /**
   * Move the given area to {@code (0, 0)}.
   *
   * @param area The area
   * 
   *
   * @return A moved area
   */

  public static <areaType> moveToOrigin(
    final <areaType> area)
  {
    <opClass>.notNullArea(area, "Area");
    return create(
      <opClass>.ZERO,
      <opClass>.ZERO,
      area.sizeX(),
      area.sizeY());
  }

  private static <scalarType> clamp(
    final <scalarType> x,
    final <scalarType> minimum,
    final <scalarType> maximum)
  {
    Preconditions.checkPrecondition(
      <opClass>.compare(maximum, minimum) >= 0,
      "Maximum >= minimum");
    return <opClass>.maximum(<opClass>.minimum(x, maximum), minimum);
  }









  /**
   * Align the area {@code inner} along the X axis in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnXCenter(
    final <areaType> outer,
    final <areaType> inner)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> outer_sizeX = outer.sizeX();
    final <scalarType> inner_sizeX = inner.sizeX();
    final <scalarType> xm0 =
      <opClass>.add(outer.minimumX(), <opClass>.divide(outer_sizeX, <opClass>.constant(2L)));
    final <scalarType> xm1 =
    <opClass>.subtract(xm0, <opClass>.divide(inner_sizeX, <opClass>.constant(2L)));
    return create(xm1, inner.minimumY(), inner_sizeX, inner.sizeY());
  }

  /**
   * Equivalent to calling {@link #alignOnXMinXOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnXMinX(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnXMinXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} along the X axis against the inside minimum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnXMinXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset);
    final <scalarType> x_max = <opClass>.add(x_min, inner.sizeX());
    return <areaType>.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignOnXMaxXOffset(<areaType>,
   * <areaType>, <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnXMaxX(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnXMaxXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} along the X axis against the inside maximum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnXMaxXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset);
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.sizeX());
    return <areaType>.of(x_min, x_max, inner.minimumY(), inner.maximumY());
  }

  /**
   * Equivalent to calling {@link #alignOnYMinYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnYMinY(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnYMinYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} along the Y axis against the inside minimum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnYMinYOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset);
    final <scalarType> y_max = <opClass>.add(y_min, inner.sizeY());
    return <areaType>.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignOnYMaxYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnYMaxY(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnYMaxYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} along the Y axis against the inside maximum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnYMaxYOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset, "offset");

    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.sizeY());
    return <areaType>.of(inner.minimumX(), inner.maximumX(), y_min, y_max);
  }

  /**
   * Align the area {@code inner} along the Y axis in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   * @since 1.1.0
   * @return An aligned area
   */

  public static <areaType> alignOnYCenter(
    final <areaType> outer,
    final <areaType> inner)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");

    final <scalarType> outer_sizeY = outer.sizeY();
    final <scalarType> inner_sizeY = inner.sizeY();

    final <scalarType> ym0 =
      <opClass>.add(outer.minimumY(), <opClass>.divide(outer_sizeY, <opClass>.constant(2L)));
    final <scalarType> ym1 =
      <opClass>.subtract(ym0, <opClass>.divide(inner_sizeY, <opClass>.constant(2L)));
    return create(inner.minimumX(), ym1, inner.sizeX(), inner_sizeY);
  }








  /**
   * Align the area {@code inner} horizontally in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnXCenter(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignHorizontallyCenter(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnXCenter(outer, inner);
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMinXOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnXMinX(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignHorizontallyMinX(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnXMinXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} horizontally against the inside minimum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @deprecated Use {@link #alignOnXMinXOffset(<areaType>, <areaType>, <scalarType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignHorizontallyMinXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    return alignOnXMinXOffset(outer, inner, offset);
  }

  /**
   * Equivalent to calling {@link #alignHorizontallyMaxXOffset(<areaType>,
   * <areaType>, <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnXMaxX(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignHorizontallyMaxX(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnXMaxXOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} horizontally against the inside maximum-x edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-x edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @deprecated Use {@link #alignOnXMaxXOffset(<areaType>, <areaType>, <scalarType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignHorizontallyMaxXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    return alignOnXMaxXOffset(outer, inner, offset);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMinYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnYMinY(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignVerticallyMinY(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnYMinYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} vertically against the inside minimum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * minimum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @deprecated Use {@link #alignOnYMinYOffset(<areaType>, <areaType>, <scalarType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignVerticallyMinYOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    return alignOnYMinYOffset(outer, inner, offset);
  }

  /**
   * Equivalent to calling {@link #alignVerticallyMaxYOffset(<areaType>, <areaType>,
   * <scalarType>)} with a zero offset.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnYMaxY(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignVerticallyMaxY(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnYMaxYOffset(outer, inner, <opClass>.ZERO);
  }

  /**
   * Align the area {@code inner} vertically against the inside maximum-y edge
   * of {@code outer}. The area will be at least {@code offset} units from the
   * maximum-y edge.
   *
   * @param outer  The outer area
   * @param inner  The inner area
   * @param offset The offset from the edge
   *
   * @deprecated Use {@link #alignOnYMaxYOffset(<areaType>, <areaType>, <scalarType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignVerticallyMaxYOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset)
  {
    return alignOnYMaxYOffset(outer, inner, offset);
  }

  /**
   * Align the area {@code inner} vertically in the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   *
   * @deprecated Use {@link #alignOnYCenter(<areaType>, <areaType>)}
   * @return An aligned area
   */

  @Deprecated
  public static <areaType> alignVerticallyCenter(
    final <areaType> outer,
    final <areaType> inner)
  {
    return alignOnYCenter(outer, inner);
  }








  /**
   * Equivalent to calling {@link #alignMinYMinXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMinYMinX(
    final <areaType> outer,
    final <areaType> inner)
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
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMinYMinXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset_x);
    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset_y);
    final <scalarType> y_max = <opClass>.add(y_min, inner.sizeY());
    final <scalarType> x_max = <opClass>.add(x_min, inner.sizeX());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMinYMaxXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMinYMaxX(
    final <areaType> outer,
    final <areaType> inner)
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
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMinYMaxXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset_x);
    final <scalarType> y_min = <opClass>.add(outer.minimumY(), offset_y);
    final <scalarType> y_max = <opClass>.add(y_min, inner.sizeY());
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.sizeX());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMinXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMaxYMinX(
    final <areaType> outer,
    final <areaType> inner)
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
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMaxYMinXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_min = <opClass>.add(outer.minimumX(), offset_x);
    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset_y);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.sizeY());
    final <scalarType> x_max = <opClass>.add(x_min, inner.sizeX());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Equivalent to calling {@link #alignMaxYMaxXOffset(<areaType>, <areaType>,
   * <scalarType>, <scalarType>)} with zero offsets.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMaxYMaxX(
    final <areaType> outer,
    final <areaType> inner)
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
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignMaxYMaxXOffset(
    final <areaType> outer,
    final <areaType> inner,
    final <scalarType> offset_x,
    final <scalarType> offset_y)
  {
    <opClass>.notNullArea(outer, "outer");
    <opClass>.notNullArea(inner, "inner");
    <opClass>.notNullScalar(offset_x, "offset x");
    <opClass>.notNullScalar(offset_y, "offset y");

    final <scalarType> x_max = <opClass>.subtract(outer.maximumX(), offset_x);
    final <scalarType> y_max = <opClass>.subtract(outer.maximumY(), offset_y);
    final <scalarType> y_min = <opClass>.subtract(y_max, inner.sizeY());
    final <scalarType> x_min = <opClass>.subtract(x_max, inner.sizeX());
    return <areaType>.of(x_min, x_max, y_min, y_max);
  }

  /**
   * Align the area {@code inner} such that the center of the area is equal to
   * the center of {@code outer}.
   *
   * @param outer The outer area
   * @param inner The inner area
   * 
   *
   * @return An aligned area
   */

  public static <areaType> alignCenter(
    final <areaType> outer,
    final <areaType> inner)
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
   *
   * @return A new area
   */

  public static <areaType> hollowOut(
    final <areaType> outer,
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
   *
   * @return A new area
   */

  public static <areaType> hollowOutEvenly(
    final <areaType> outer,
    final <scalarType> offset)
  {
    return hollowOut(outer, offset, offset, offset, offset);
  }

  /**
   * \<p>Set the sizeX and sizeY of {@code area} to {@code sizeX} and {@code
   * sizeY}, respectively.\</p>
   *
   * \<p>The area is resized from its own center.\</p>
   *
   * @param area   The area
   * @param sizeX  The new sizeX (must be non-negative)
   * @param sizeY The new sizeY (must be non-negative)
   *
   * @return A resized area
   */

  public static <areaType> setSizeFromCenter(
    final <areaType> area,
    final <scalarType> sizeX,
    final <scalarType> sizeY)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(sizeX, "sizeX");
    <opClass>.notNullScalar(sizeX, "sizeY");

    return alignCenter(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), sizeX),
      area.minimumY(),
      <opClass>.add(area.minimumY(), sizeY)));
  }

  /**
   * \<p>Set the sizeX and sizeY of {@code area} to {@code sizeX} and {@code
   * sizeY}, respectively.\</p>
   *
   * \<p>The area is resized by moving its minimum-y-minimum-x corner.\</p>
   *
   * @param area   The area
   * @param sizeX  The new sizeX (must be non-negative)
   * @param sizeY The new sizeY (must be non-negative)
   * 
   *
   * @return A resized area
   */

  public static <areaType> setSizeFromMinYMinX(
    final <areaType> area,
    final <scalarType> sizeX,
    final <scalarType> sizeY)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(sizeX, "sizeX");
    <opClass>.notNullScalar(sizeX, "sizeY");

    return alignMaxYMaxX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), sizeX),
      area.minimumY(),
      <opClass>.add(area.minimumY(), sizeY)));
  }

  /**
   * \<p>Set the sizeX and sizeY of {@code area} to {@code sizeX} and {@code
   * sizeY}, respectively.\</p>
   *
   * \<p>The area is resized by moving its minimum-y-maximum-x corner.\</p>
   *
   * @param area   The area
   * @param sizeX  The new sizeX (must be non-negative)
   * @param sizeY The new sizeY (must be non-negative)
   * 
   *
   * @return A resized area
   */

  public static <areaType> setSizeFromMinYMaxX(
    final <areaType> area,
    final <scalarType> sizeX,
    final <scalarType> sizeY)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(sizeX, "sizeX");
    <opClass>.notNullScalar(sizeX, "sizeY");

    return alignMaxYMinX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), sizeX),
      area.minimumY(),
      <opClass>.add(area.minimumY(), sizeY)));
  }

  /**
   * \<p>Set the sizeX and sizeY of {@code area} to {@code sizeX} and {@code
   * sizeY}, respectively.\</p>
   *
   * \<p>The area is resized by moving its maximum-y-maximum-x corner.\</p>
   *
   * @param area   The area
   * @param sizeX  The new sizeX (must be non-negative)
   * @param sizeY The new sizeY (must be non-negative)
   * 
   *
   * @return A resized area
   */

  public static <areaType> setSizeFromMaxYMaxX(
    final <areaType> area,
    final <scalarType> sizeX,
    final <scalarType> sizeY)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(sizeX, "sizeX");
    <opClass>.notNullScalar(sizeX, "sizeY");

    return alignMinYMinX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), sizeX),
      area.minimumY(),
      <opClass>.add(area.minimumY(), sizeY)));
  }

  /**
   * \<p>Set the sizeX and sizeY of {@code area} to {@code sizeX} and {@code
   * sizeY}, respectively.\</p>
   *
   * \<p>The area is resized by moving its maximum-y-minimum-x corner.\</p>
   *
   * @param area   The area
   * @param sizeX  The new sizeX (must be non-negative)
   * @param sizeY The new sizeY (must be non-negative)
   * 
   *
   * @return A resized area
   */

  public static <areaType> setSizeFromMaxYMinX(
    final <areaType> area,
    final <scalarType> sizeX,
    final <scalarType> sizeY)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(sizeX, "sizeX");
    <opClass>.notNullScalar(sizeX, "sizeY");

    return alignMinYMaxX(area, <areaType>.of(
      area.minimumX(),
      <opClass>.add(area.minimumX(), sizeX),
      area.minimumY(),
      <opClass>.add(area.minimumY(), sizeY)));
  }

  /**
   * \<p>Scale {@code area} by adding {@code x_diff} to the sizeX, and {@code
   * y_diff} to the sizeY. The size of the resulting area is clamped so that
   * its sizeX and sizeY are always non-negative.\</p>
   *
   * \<p>The area is resized by moving its minimum-y-minimum-x corner.\</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * 
   *
   * @return A resized area
   */

  public static <areaType> scaleFromMinYMinX(
    final <areaType> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> sizeX =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeX(), x_diff));
    final <scalarType> sizeY =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeY(), y_diff));
    return setSizeFromMinYMinX(area, sizeX, sizeY);
  }

  /**
   * \<p>Scale {@code area} by adding {@code x_diff} to the sizeX, and {@code
   * y_diff} to the sizeY. The size of the resulting area is clamped so that
   * its sizeX and sizeY are always non-negative.\</p>
   *
   * \<p>The area is resized by moving its minimum-y-maximum-x corner.\</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * 
   *
   * @return A resized area
   */

  public static <areaType> scaleFromMinYMaxX(
    final <areaType> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> sizeX =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeX(), x_diff));
    final <scalarType> sizeY =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeY(), y_diff));
    return setSizeFromMinYMaxX(area, sizeX, sizeY);
  }

  /**
   * \<p>Scale {@code area} by adding {@code x_diff} to the sizeX, and {@code
   * y_diff} to the sizeY. The size of the resulting area is clamped so that
   * its sizeX and sizeY are always non-negative.\</p>
   *
   * \<p>The area is resized by moving its maximum-y-minimum-x corner.\</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * 
   *
   * @return A resized area
   */

  public static <areaType> scaleFromMaxYMinX(
    final <areaType> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> sizeX =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeX(), x_diff));
    final <scalarType> sizeY =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeY(), y_diff));
    return setSizeFromMaxYMinX(area, sizeX, sizeY);
  }

  /**
   * \<p>Scale {@code area} by adding {@code x_diff} to the sizeX, and {@code
   * y_diff} to the sizeY. The size of the resulting area is clamped so that
   * its sizeX and sizeY are always non-negative.\</p>
   *
   * \<p>The area is resized by moving its maximum-y-maximum-x corner.\</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * 
   *
   * @return A resized area
   */

  public static <areaType> scaleFromMaxYMaxX(
    final <areaType> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> sizeX =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeX(), x_diff));
    final <scalarType> sizeY =
      <opClass>.maximum(<opClass>.ZERO,<opClass>.add(area.sizeY(), y_diff));
    return setSizeFromMaxYMaxX(area, sizeX, sizeY);
  }

  /**
   * \<p>Scale {@code area} by adding {@code x_diff} to the sizeX, and {@code
   * y_diff} to the sizeY. The size of the resulting area is clamped so that
   * its sizeX and sizeY are always non-negative.\</p>
   *
   * \<p>The area is resized from its own center.\</p>
   *
   * @param area   The area
   * @param x_diff The X difference
   * @param y_diff The Y difference
   * 
   *
   * @return A resized area
   */

  public static <areaType> scaleFromCenter(
    final <areaType> area,
    final <scalarType> x_diff,
    final <scalarType> y_diff)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x_diff, "diff x");
    <opClass>.notNullScalar(y_diff, "diff y");

    final <scalarType> sizeX =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeX(), x_diff));
    final <scalarType> sizeY =
      <opClass>.maximum(<opClass>.ZERO, <opClass>.add(area.sizeY(), y_diff));
    return setSizeFromCenter(area, sizeX, sizeY);
  }

  /**
   * \<p>Determine whether or not two areas overlap.\</p>
   *
   * \<p>Overlapping is reflexive: {@code overlaps(a, a) == true}.\</p>
   *
   * \<p>Overlapping is symmetric: {@code overlaps(a, b) == overlaps(b, a)}.\</p>
   *
   * \<p>Overlapping is not necessarily transitive.\</p>
   *
   * @param a   An area
   * @param b   An area
   *
   * @return {@code true} iff {@code a} overlaps {@code b}
   */

  public static boolean overlaps(
    final <areaType> a,
    final <areaType> b)
  {
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    final <areaType> cmp_a = create(
      a.minimumX(),
      a.minimumY(),
      <opClass>.maximum(<opClass>.ONE, a.sizeX()),
      <opClass>.maximum(<opClass>.ONE, a.sizeY()));

    final <areaType> cmp_b = create(
      b.minimumX(),
      b.minimumY(),
      <opClass>.maximum(<opClass>.ONE, b.sizeX()),
      <opClass>.maximum(<opClass>.ONE, b.sizeY()));

    final <scalarType> a_x0 = cmp_a.minimumX();
    final <scalarType> a_x1 = cmp_a.maximumX();
    final <scalarType> a_y0 = cmp_a.minimumY();
    final <scalarType> a_y1 = cmp_a.maximumY();
    final <scalarType> b_x0 = cmp_b.minimumX();
    final <scalarType> b_x1 = cmp_b.maximumX();
    final <scalarType> b_y0 = cmp_b.minimumY();
    final <scalarType> b_y1 = cmp_b.maximumY();

    final boolean c0 = <opClass>.compare(a_x0, b_x1) \< 0;
    final boolean c1 = <opClass>.compare(a_x1, b_x0) > 0;
    final boolean c2 = <opClass>.compare(a_y0, b_y1) \< 0;
    final boolean c3 = <opClass>.compare(a_y1, b_y0) > 0;

    return c0 && c1 && c2 && c3;
  }

  /**
   * \<p>Determine whether or not one area could fit inside another.\</p>
   *
   * \<p>Fitting is reflexive: {@code couldFitInside(a, a) == true}.\</p>
   *
   * \<p>Fitting is transitive: {@code couldFitInside(a, b) → couldFitInside(b,
   * c) → couldFitInside(a, c)}.\</p>
   *
   * \<p>Fitting is not necessarily symmetric.\</p>
   *
   * @param a   An area
   * @param b   An area
   * 
   *
   * @return {@code true} iff {@code a} could fit inside {@code b}
   */

  public static boolean couldFitInside(
    final <areaType> a,
    final <areaType> b)
  {
    <opClass>.notNullArea(a, "a");
    <opClass>.notNullArea(b, "b");

    final boolean sizeX_ok =
    <opClass>.compare(a.sizeX(), b.sizeX()) \<= 0;
    final boolean sizeY_ok =
    <opClass>.compare(a.sizeY(), b.sizeY()) \<= 0;
    return sizeX_ok && sizeY_ok;
  }

  /**
   * Construct a area that will contain both {@code a} and {@code b}.
   *
   * @param a   An area
   * @param b   An area
   * 
   *
   * @return An area containing {@code a} and {@code b}
   */

  public static <areaType> containing(
    final <areaType> a,
    final <areaType> b)
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
   * \<p>Determine whether or not a area contains a given point.\</p>
   *
   * @param a   An area
   * @param x   The X coordinate of the point
   * @param y   The Y coordinate of the point
   * 
   *
   * @return {@code true} iff {@code a} contains {@code (x, y)}
   */

  public static boolean containsPoint(
    final <areaType> a,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(a, "area");
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");

    final boolean contain_x =
      <opClass>.compare(x, a.minimumX()) >= 0 && <opClass>.compare(x, a.maximumX()) \< 0;
    final boolean contain_y =
      <opClass>.compare(y, a.minimumY()) >= 0 && <opClass>.compare(y, a.maximumY()) \< 0;
    return contain_x && contain_y;
  }









  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, horizontally.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * 
   * @deprecated Use {@link #fitBetweenOnX(<areaType>, <areaType>, <areaType>)}
   * @return A fitted area
   */

  @Deprecated
  public static <areaType> fitBetweenHorizontal(
    final <areaType> fit,
    final <areaType> a,
    final <areaType> b)
  {
    return fitBetweenOnX(fit, a, b);
  }

  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, vertically.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   * 
   * @deprecated Use {@link #fitBetweenOnY(<areaType>, <areaType>, <areaType>)}
   * @return A fitted area
   */

  @Deprecated
  public static <areaType> fitBetweenVertical(
    final <areaType> fit,
    final <areaType> a,
    final <areaType> b)
  {
    return fitBetweenOnY(fit, a, b);
  }








  /**
   * Attempt to fit {@code fit} between {@code a} and {@code b}, horizontally.
   *
   * @param fit The area to be fitted
   * @param a   An area
   * @param b   An area
   *
   *
   * @return A fitted area
   */

  public static <areaType> fitBetweenOnX(
    final <areaType> fit,
    final <areaType> a,
    final <areaType> b)
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
   *
   *
   * @return A fitted area
   */

  public static <areaType> fitBetweenOnY(
    final <areaType> fit,
    final <areaType> a,
    final <areaType> b)
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
   *
   *
   * @return A pair of areas
   */

  public static <splitXType>\<<areaType>\> splitAlongParallelToX(
    final <areaType> area,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(y, "y");

    final <scalarType> clamped_sizeY = <opClass>.minimum(area.sizeY(), y);
    final <scalarType> upper_y_min = area.minimumY();
    final <scalarType> upper_y_max = <opClass>.add(area.minimumY(), clamped_sizeY);
    final <scalarType> lower_y_min = upper_y_max;
    final <scalarType> lower_y_max = area.maximumY();

    final <areaType> lower = <areaType>.of(
      area.minimumX(), area.maximumX(), lower_y_min, lower_y_max);
    final <areaType> upper = <areaType>.of(
      area.minimumX(), area.maximumX(), upper_y_min, upper_y_max);

    return <splitXType>.of(lower, upper);
  }

  /**
   * Split {@code area} along a line parallel to the Y axis placed at
   * {@code x} units from its own minimum-x edge.
   *
   * @param area The area to be split
   * @param x    The relative X coordinate of the splitting edge
   *
   *
   * @return A pair of areas
   */

  public static <splitYType>\<<areaType>\> splitAlongParallelToY(
    final <areaType> area,
    final <scalarType> x)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x, "x");

    final <scalarType> clamped_sizeX = <opClass>.minimum(area.sizeX(), x);
    final <scalarType> lower_x_min = area.minimumX();
    final <scalarType> lower_x_max = <opClass>.add(area.minimumX(), clamped_sizeX);
    final <scalarType> upper_x_min = lower_x_max;
    final <scalarType> upper_x_max = area.maximumX();

    final <areaType> lower = <areaType>.of(
      lower_x_min, lower_x_max, area.minimumY(), area.maximumY());
    final <areaType> upper = <areaType>.of(
      upper_x_min, upper_x_max, area.minimumY(), area.maximumY());

    return <splitYType>.of(lower, upper);
  }

  /**
   * Split {@code area} along a two axis-aligned perpendicular lines that intercept at {@code (x, y)}.
   *
   * @param area The area to be split
   * @param x    The relative X coordinate of the splitting edge
   * @param y    The relative Y coordinate of the splitting edge
   *
   * @return The four resulting quadrants
   * @since 1.1.0
   */

  public static <splitXYType>\<<areaType>\> splitAlongXY(
    final <areaType> area,
    final <scalarType> x,
    final <scalarType> y)
  {
    <opClass>.notNullArea(area, "area");
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");

    final <scalarType> clamped_sizeX = <opClass>.minimum(area.sizeX(), x);
    final <scalarType> lower_x_min = area.minimumX();
    final <scalarType> lower_x_max = <opClass>.add(area.minimumX(), clamped_sizeX);
    final <scalarType> upper_x_min = lower_x_max;
    final <scalarType> upper_x_max = <opClass>.maximum(area.maximumX(), lower_x_max);

    final <scalarType> clamped_sizeY = <opClass>.minimum(area.sizeY(), y);
    final <scalarType> lower_y_min = area.minimumY();
    final <scalarType> lower_y_max = <opClass>.add(area.minimumY(), clamped_sizeY);
    final <scalarType> upper_y_min = lower_y_max;
    final <scalarType> upper_y_max = <opClass>.maximum(area.maximumY(), lower_y_max);

    final <areaType> x0y0 = <areaType>.of(
      lower_x_min, lower_x_max, lower_y_min, lower_y_max);
    final <areaType> x0y1 = <areaType>.of(
      lower_x_min, lower_x_max, upper_y_min, upper_y_max);
    final <areaType> x1y0 = <areaType>.of(
      upper_x_min, upper_x_max, lower_y_min, lower_y_max);
    final <areaType> x1y1 = <areaType>.of(
      upper_x_min, upper_x_max, upper_y_min, upper_y_max);

    return <splitXYType>.of(x0y0, x1y0, x0y1, x1y1);
  }

  /**
   * @param area The area
   *
   * @return A terse string describing the position and size of the area
   */

  public static String show(
    final <areaType> area)
  {
    <opClass>.notNullArea(area, "area");

    final StringBuilder sb = new StringBuilder(128);
    return showToBuilder(area, sb);
  }

  /**
   * @param area The area
   *
   * @return The size of the area
   */

  public static <areaSizeType> size(
    final <areaType> area)
  {
    <opClass>.notNullArea(area, "area");
    return <areaSizeType>.of(area.sizeX(), area.sizeY());
  }

  /**
   * @param area The area
   * @param sb   A string builder
   *
   * @return A terse string describing the position and size of the area
   */

  public static String showToBuilder(
    final <areaType> area,
    final StringBuilder sb)
  {
    <opClass>.notNullArea(area, "area");
    Objects.requireNonNull(sb, "sb");

    sb.append(area.sizeX());
    sb.append("x");
    sb.append(area.sizeY());
    sb.append(" ");
    sb.append(area.minimumX());
    sb.append("+");
    sb.append(area.minimumY());
    return sb.toString();
  }
}

>>