Volumes(
  scalarType,
  volumeType,
  volumeSizeType,
  volumeSplitXYZType,
  className,
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

package com.io7m.jregions.core.unparameterized.volumes;

import java.util.Objects;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over volumes.\</p>
 *
 * \<p>These functions operate using the concepts of \<i>minimum-x\</i>,
 * \<i>maximum-x\</i>, \<i>minimum-y\</i>, \<i>maximum-y\</i>,
 * \<i>minimum-z\</i>, \<i>maximum-z\</i>, edges. It is up to
 * individual applications to assign meaning to these edges such as "left" for
 * \<i>minimum-x\</i>, "top" for \<i>minimum-y\</i>, or "far" for \<i>minimum-z\</i>.\</p>
 */

public final class <className>
{
  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Create a volume of size ({@code size_x}, {@code size_y}, {@code size_z})
   * placing the minimum corner at {@code (x, y, z)}.
   *
   * @param x      The X value of the minimum corner
   * @param y      The Y value of the minimum corner
   * @param z      The Z value of the minimum corner
   * @param size_x The size of the volume on the X axis
   * @param size_y The size of the volume on the Y axis
   * @param size_z The size of the volume on the Z axis
   *
   * @return A volume
   */

  public static <volumeType> create(
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z,
    final <scalarType> size_x,
    final <scalarType> size_y,
    final <scalarType> size_z)
  {
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");
    <opClass>.notNullScalar(z, "z");
    <opClass>.notNullScalar(size_x, "size_x");
    <opClass>.notNullScalar(size_y, "size_y");
    <opClass>.notNullScalar(size_z, "size_z");

    return <volumeType>.of(
      x,
      <opClass>.add(x, size_x),
      y,
      <opClass>.add(y, size_y),
      z,
      <opClass>.add(z, size_z));
  }

  /**
   * \<p>Determine whether or not one volume contains another.\</p>
   *
   * \<p>Containing is reflexive: {@code contains(a, a) == true}.\</p>
   *
   * \<p>Containing is transitive: {@code contains(a, b) → contains(b, c) →
   * contains(a, c)}.\</p>
   *
   * \<p>Containing is not necessarily symmetric.\</p>
   *
   * @param a   Volume A
   * @param b   Volume B
   *
   * @return {@code true} iff {@code a} contains {@code b}
   */

  public static boolean contains(
    final <volumeType> a,
    final <volumeType> b)
  {
    <opClass>.notNullVolume(a, "Volume A");
    <opClass>.notNullVolume(b, "Volume B");

    final <scalarType> a_x0 = a.minimumX();
    final <scalarType> a_x1 = a.maximumX();
    final <scalarType> a_y0 = a.minimumY();
    final <scalarType> a_y1 = a.maximumY();
    final <scalarType> a_z0 = a.minimumZ();
    final <scalarType> a_z1 = a.maximumZ();
    final <scalarType> b_x0 = b.minimumX();
    final <scalarType> b_x1 = b.maximumX();
    final <scalarType> b_y0 = b.minimumY();
    final <scalarType> b_y1 = b.maximumY();
    final <scalarType> b_z0 = b.minimumZ();
    final <scalarType> b_z1 = b.maximumZ();

    final boolean c0 = <opClass>.compare(b_x0, a_x0) >= 0;
    final boolean c1 = <opClass>.compare(b_x1, a_x1) \<= 0;
    final boolean c2 = <opClass>.compare(b_y0, a_y0) >= 0;
    final boolean c3 = <opClass>.compare(b_y1, a_y1) \<= 0;
    final boolean c4 = <opClass>.compare(b_z0, a_z0) >= 0;
    final boolean c5 = <opClass>.compare(b_z1, a_z1) \<= 0;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }

  /**
   * Move the given volume by {@code (x, y, z)}.
   *
   * @param volume The volume
   * @param x    The amount to move on the X axis
   * @param y    The amount to move on the Y axis
   * @param z    The amount to move on the Z axis
   *
   * @return A moved volume
   */

  public static <volumeType> moveRelative(
    final <volumeType> volume,
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(volume, "Volume");

    final <scalarType> x_min = <opClass>.add(volume.minimumX(), x);
    final <scalarType> x_max = <opClass>.add(volume.maximumX(), x);
    final <scalarType> y_min = <opClass>.add(volume.minimumY(), y);
    final <scalarType> y_max = <opClass>.add(volume.maximumY(), y);
    final <scalarType> z_min = <opClass>.add(volume.minimumZ(), z);
    final <scalarType> z_max = <opClass>.add(volume.maximumZ(), z);
    return <volumeType>.of(x_min, x_max, y_min, y_max, z_min, z_max);
  }

  /**
   * Move the given volume by {@code x}, without allowing {@code volume}
   * to leave {@code container}.
   *
   * @param container The container volume
   * @param volume    The volume
   * @param x         The amount to move on the X axis
   *
   * @since 3.0.0
   * @return A moved volume
   */

  public static <volumeType> moveRelativeClampedX(
    final <volumeType> container,
    final <volumeType> volume,
    final <scalarType> x)
  {
    <opClass>.notNullVolume(container, "Container");
    <opClass>.notNullVolume(volume, "Volume");

    Preconditions.checkPrecondition(
      contains(container, volume),
      "Container must contain volume");

    final var sizeX = volume.sizeX();
    final var newMinY = volume.minimumY();
    final var newMaxY = volume.maximumY();
    final var newMinZ = volume.minimumZ();
    final var newMaxZ = volume.maximumZ();
    final var movedOnX = moveRelative(volume, x, <opClass>.ZERO, <opClass>.ZERO);
    if (!contains(container, movedOnX)) {
      if (<opClass>.compare(x, <opClass>.ZERO) \< 0) {
        final var newMinX = container.minimumX();
        final var newMaxX = <opClass>.minimum(container.maximumX(), <opClass>.add(newMinX, sizeX));
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      } else {
        final var newMinX = <opClass>.maximum(container.minimumX(), <opClass>.subtract(container.maximumX(), sizeX));
        final var newMaxX = container.maximumX();
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      }
    }
    return movedOnX;
  }

  /**
   * Move the given volume by {@code y}, without allowing {@code volume}
   * to leave {@code container}.
   *
   * @param container The container volume
   * @param volume    The volume
   * @param y         The amount to move on the Y axis
   *
   * @since 3.0.0
   * @return A moved volume
   */

  public static <volumeType> moveRelativeClampedY(
    final <volumeType> container,
    final <volumeType> volume,
    final <scalarType> y)
  {
    <opClass>.notNullVolume(container, "Container");
    <opClass>.notNullVolume(volume, "Volume");

    Preconditions.checkPrecondition(
      contains(container, volume),
      "Container must contain volume");

    final var sizeY = volume.sizeY();
    final var newMinX = volume.minimumX();
    final var newMaxX = volume.maximumX();
    final var newMinZ = volume.minimumZ();
    final var newMaxZ = volume.maximumZ();
    final var movedOnY = moveRelative(volume, <opClass>.ZERO, y, <opClass>.ZERO);
    if (!contains(container, movedOnY)) {
      if (<opClass>.compare(y, <opClass>.ZERO) \< 0) {
        final var newMinY = container.minimumY();
        final var newMaxY = <opClass>.minimum(container.maximumY(), <opClass>.add(newMinY, sizeY));
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      } else {
        final var newMinY = <opClass>.maximum(container.minimumY(), <opClass>.subtract(container.maximumY(), sizeY));
        final var newMaxY = container.maximumY();
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      }
    }
    return movedOnY;
  }

  /**
   * Move the given volume by {@code z}, without allowing {@code volume}
   * to leave {@code container}.
   *
   * @param container The container volume
   * @param volume    The volume
   * @param z         The amount to move on the Z axis
   *
   * @since 3.0.0
   * @return A moved volume
   */

  public static <volumeType> moveRelativeClampedZ(
    final <volumeType> container,
    final <volumeType> volume,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(container, "Container");
    <opClass>.notNullVolume(volume, "Volume");

    Preconditions.checkPrecondition(
      contains(container, volume),
      "Container must contain volume");

    final var sizeZ = volume.sizeZ();
    final var newMinX = volume.minimumX();
    final var newMaxX = volume.maximumX();
    final var newMinY = volume.minimumY();
    final var newMaxY = volume.maximumY();
    final var movedOnZ = moveRelative(volume, <opClass>.ZERO, <opClass>.ZERO, z);
    if (!contains(container, movedOnZ)) {
      if (<opClass>.compare(z, <opClass>.ZERO) \< 0) {
        final var newMinZ = container.minimumZ();
        final var newMaxZ = <opClass>.minimum(container.maximumZ(), <opClass>.add(newMinZ, sizeZ));
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      } else {
        final var newMinZ = <opClass>.maximum(container.minimumZ(), <opClass>.subtract(container.maximumZ(), sizeZ));
        final var newMaxZ = container.maximumZ();
        return <volumeType>.of(newMinX, newMaxX, newMinY, newMaxY, newMinZ, newMaxZ);
      }
    }
    return movedOnZ;
  }

  /**
   * Move the given volume by {@code (x, y, z)}, without allowing {@code volume}
   * to leave {@code container}.
   *
   * @param container The container volume
   * @param volume    The volume
   * @param x         The amount to move on the X axis
   * @param y         The amount to move on the Y axis
   * @param z         The amount to move on the Z axis
   *
   * @since 3.0.0
   * @return A moved volume
   */

  public static <volumeType> moveRelativeClamped(
    final <volumeType> container,
    final <volumeType> volume,
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(container, "Container");
    <opClass>.notNullVolume(volume, "Volume");

    Preconditions.checkPrecondition(
      contains(container, volume),
      "Container must contain volume");

    final var m0 = moveRelativeClampedX(container, volume, x);
    final var m1 = moveRelativeClampedY(container, m0, y);
    return moveRelativeClampedZ(container, m1, z);
  }

  /**
   * Move the given volume to {@code (x, y, z)}.
   *
   * @param volume The volume
   * @param x    The position to which to move on the X axis
   * @param y    The position to which to move on the Y axis
   * @param z    The position to which to move on the Z axis
   *
   * @return A moved volume
   */

  public static <volumeType> moveAbsolute(
    final <volumeType> volume,
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(volume, "Volume");
    return create(x, y, z, volume.sizeX(), volume.sizeY(), volume.sizeZ());
  }

  /**
   * Move the given volume to {@code (0, 0, 0)}.
   *
   * @param volume The volume
   *
   * @return A moved volume
   */

  public static <volumeType> moveToOrigin(
    final <volumeType> volume)
  {
    <opClass>.notNullVolume(volume, "Volume");
    return create(
      <opClass>.ZERO,
      <opClass>.ZERO,
      <opClass>.ZERO,
      volume.sizeX(),
      volume.sizeY(),
      volume.sizeZ());
  }

  /**
   * \<p>Determine whether or not two volumes overlap.\</p>
   *
   * \<p>Overlapping is reflexive: {@code overlaps(a, a) == true}.\</p>
   *
   * \<p>Overlapping is symmetric: {@code overlaps(a, b) == overlaps(b, a)}.\</p>
   *
   * \<p>Overlapping is not necessarily transitive.\</p>
   *
   * @param a   A volume
   * @param b   A volume
   *
   * @return {@code true} iff {@code a} overlaps {@code b}
   */

  public static boolean overlaps(
    final <volumeType> a,
    final <volumeType> b)
  {
    <opClass>.notNullVolume(a, "a");
    <opClass>.notNullVolume(b, "b");

    final <volumeType> cmp_a = create(
      a.minimumX(),
      a.minimumY(),
      a.minimumZ(),
      <opClass>.maximum(<opClass>.ONE, a.sizeX()),
      <opClass>.maximum(<opClass>.ONE, a.sizeY()),
      <opClass>.maximum(<opClass>.ONE, a.sizeZ()));

    final <volumeType> cmp_b = create(
      b.minimumX(),
      b.minimumY(),
      b.minimumZ(),
      <opClass>.maximum(<opClass>.ONE, b.sizeX()),
      <opClass>.maximum(<opClass>.ONE, b.sizeY()),
      <opClass>.maximum(<opClass>.ONE, b.sizeZ()));

    final <scalarType> a_x0 = cmp_a.minimumX();
    final <scalarType> a_x1 = cmp_a.maximumX();
    final <scalarType> a_y0 = cmp_a.minimumY();
    final <scalarType> a_y1 = cmp_a.maximumY();
    final <scalarType> a_z0 = cmp_a.minimumZ();
    final <scalarType> a_z1 = cmp_a.maximumZ();
    final <scalarType> b_x0 = cmp_b.minimumX();
    final <scalarType> b_x1 = cmp_b.maximumX();
    final <scalarType> b_y0 = cmp_b.minimumY();
    final <scalarType> b_y1 = cmp_b.maximumY();
    final <scalarType> b_z0 = cmp_b.minimumZ();
    final <scalarType> b_z1 = cmp_b.maximumZ();

    final boolean c0 = <opClass>.compare(a_x0, b_x1) \< 0;
    final boolean c1 = <opClass>.compare(a_x1, b_x0) > 0;
    final boolean c2 = <opClass>.compare(a_y0, b_y1) \< 0;
    final boolean c3 = <opClass>.compare(a_y1, b_y0) > 0;
    final boolean c4 = <opClass>.compare(a_z0, b_z1) \< 0;
    final boolean c5 = <opClass>.compare(a_z1, b_z0) > 0;

    return c0 && c1 && c2 && c3 && c4 && c5;
  }

  /**
   * \<p>Determine whether or not one volume could fit inside another.\</p>
   *
   * \<p>Fitting is reflexive: {@code couldFitInside(a, a) == true}.\</p>
   *
   * \<p>Fitting is transitive: {@code couldFitInside(a, b) → couldFitInside(b,
   * c) → couldFitInside(a, c)}.\</p>
   *
   * \<p>Fitting is not necessarily symmetric.\</p>
   *
   * @param a   A volume
   * @param b   A volume
   *
   * @return {@code true} iff {@code a} could fit inside {@code b}
   */

  public static boolean couldFitInside(
    final <volumeType> a,
    final <volumeType> b)
  {
    <opClass>.notNullVolume(a, "a");
    <opClass>.notNullVolume(b, "b");

    final boolean sizeX_ok =
      <opClass>.compare(a.sizeX(), b.sizeX()) \<= 0;
    final boolean sizeY_ok =
      <opClass>.compare(a.sizeY(), b.sizeY()) \<= 0;
    final boolean sizeZ_ok =
      <opClass>.compare(a.sizeZ(), b.sizeZ()) \<= 0;
    return sizeX_ok && sizeY_ok && sizeZ_ok;
  }

  /**
   * Construct a volume that will contain both {@code a} and {@code b}.
   *
   * @param a   A volume
   * @param b   A volume
   *
   * @return A volume containing {@code a} and {@code b}
   */

  public static <volumeType> containing(
    final <volumeType> a,
    final <volumeType> b)
  {
    <opClass>.notNullVolume(a, "a");
    <opClass>.notNullVolume(b, "b");

    return <volumeType>.of(
      <opClass>.minimum(a.minimumX(), b.minimumX()),
      <opClass>.maximum(a.maximumX(), b.maximumX()),
      <opClass>.minimum(a.minimumY(), b.minimumY()),
      <opClass>.maximum(a.maximumY(), b.maximumY()),
      <opClass>.minimum(a.minimumZ(), b.minimumZ()),
      <opClass>.maximum(a.maximumZ(), b.maximumZ()));
  }

  /**
   * \<p>Determine whether or not a volume contains a given point.\</p>
   *
   * @param a   A volume
   * @param x   The X coordinate of the point
   * @param y   The Y coordinate of the point
   * @param z   The Z coordinate of the point
   *
   * @return {@code true} iff {@code a} contains {@code (x, y, z)}
   */

  public static boolean containsPoint(
    final <volumeType> a,
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(a, "volume");
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");
    <opClass>.notNullScalar(z, "z");

    final boolean contain_x =
      <opClass>.compare(x, a.minimumX()) >= 0 && <opClass>.compare(x, a.maximumX()) \< 0;
    final boolean contain_y =
      <opClass>.compare(y, a.minimumY()) >= 0 && <opClass>.compare(y, a.maximumY()) \< 0;
    final boolean contain_z =
      <opClass>.compare(z, a.minimumZ()) >= 0 && <opClass>.compare(z, a.maximumZ()) \< 0;
    return contain_x && contain_y && contain_z;
  }

  /**
   * \<p>Split the given volume at point {@code (x, y, z)}.\</p>
   *
   * @param v   A volume
   * @param x   The X coordinate of the point
   * @param y   The Y coordinate of the point
   * @param z   The Z coordinate of the point
   *
   * @return {@code true} iff {@code a} contains {@code (x, y, z)}
   */

  public static <volumeSplitXYZType>\<<volumeType>\> splitAtXYZ(
    final <volumeType> v,
    final <scalarType> x,
    final <scalarType> y,
    final <scalarType> z)
  {
    <opClass>.notNullVolume(v, "volume");
    <opClass>.notNullScalar(x, "x");
    <opClass>.notNullScalar(y, "y");
    <opClass>.notNullScalar(z, "z");

    final <scalarType> clamped_sizeX = <opClass>.minimum(v.sizeX(), x);
    final <scalarType> lower_x_min = v.minimumX();
    final <scalarType> lower_x_max = <opClass>.add(v.minimumX(), clamped_sizeX);
    final <scalarType> upper_x_min = lower_x_max;
    final <scalarType> upper_x_max = <opClass>.maximum(v.maximumX(), lower_x_max);

    final <scalarType> clamped_sizeY = <opClass>.minimum(v.sizeY(), y);
    final <scalarType> lower_y_min = v.minimumY();
    final <scalarType> lower_y_max = <opClass>.add(v.minimumY(), clamped_sizeY);
    final <scalarType> upper_y_min = lower_y_max;
    final <scalarType> upper_y_max = <opClass>.maximum(v.maximumY(), lower_y_max);

    final <scalarType> clamped_sizeZ = <opClass>.minimum(v.sizeZ(), z);
    final <scalarType> lower_z_min = v.minimumZ();
    final <scalarType> lower_z_max = <opClass>.add(v.minimumZ(), clamped_sizeZ);
    final <scalarType> upper_z_min = lower_z_max;
    final <scalarType> upper_z_max = <opClass>.maximum(v.maximumZ(), lower_z_max);

    final <volumeType> x0y0z0 = <volumeType>.of(
      lower_x_min, lower_x_max, lower_y_min, lower_y_max, lower_z_min, lower_z_max);
    final <volumeType> x0y1z0 = <volumeType>.of(
      lower_x_min, lower_x_max, upper_y_min, upper_y_max, lower_z_min, lower_z_max);
    final <volumeType> x1y0z0 = <volumeType>.of(
      upper_x_min, upper_x_max, lower_y_min, lower_y_max, lower_z_min, lower_z_max);
    final <volumeType> x1y1z0 = <volumeType>.of(
      upper_x_min, upper_x_max, upper_y_min, upper_y_max, lower_z_min, lower_z_max);

    final <volumeType> x0y0z1 = <volumeType>.of(
      lower_x_min, lower_x_max, lower_y_min, lower_y_max, upper_z_min, upper_z_max);
    final <volumeType> x0y1z1 = <volumeType>.of(
      lower_x_min, lower_x_max, upper_y_min, upper_y_max, upper_z_min, upper_z_max);
    final <volumeType> x1y0z1 = <volumeType>.of(
      upper_x_min, upper_x_max, lower_y_min, lower_y_max, upper_z_min, upper_z_max);
    final <volumeType> x1y1z1 = <volumeType>.of(
      upper_x_min, upper_x_max, upper_y_min, upper_y_max, upper_z_min, upper_z_max);

    return <volumeSplitXYZType>.of(
      x0y0z0,
      x1y0z0,
      x0y1z0,
      x1y1z0,
      x0y0z1,
      x1y0z1,
      x0y1z1,
      x1y1z1);
  }

  /**
   * @param volume The volume
   *
   * @return A terse string describing the position and size of the volume
   */

  public static String show(
    final <volumeType> volume)
  {
    <opClass>.notNullVolume(volume, "volume");

    final StringBuilder sb = new StringBuilder(128);
    return showToBuilder(volume, sb);
  }

  /**
   * @param volume The volume
   * @return The size of the volume
   */

  public static <volumeSizeType> size(
    final <volumeType> volume)
  {
    <opClass>.notNullVolume(volume, "volume");
    return <volumeSizeType>.of(volume.sizeX(), volume.sizeY(), volume.sizeZ());
  }

  /**
   * @param volume The volume
   * @param sb   A string builder
   *
   * @return A terse string describing the position and size of the volume
   */

  public static String showToBuilder(
    final <volumeType> volume,
    final StringBuilder sb)
  {
    <opClass>.notNullVolume(volume, "volume");
    Objects.requireNonNull(sb, "sb");

    sb.append(volume.sizeX());
    sb.append("x");
    sb.append(volume.sizeY());
    sb.append("x");
    sb.append(volume.sizeZ());
    sb.append(" ");
    sb.append(volume.minimumX());
    sb.append("+");
    sb.append(volume.minimumY());
    sb.append("+");
    sb.append(volume.minimumZ());
    return sb.toString();
  }
}

>>