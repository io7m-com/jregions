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

package com.io7m.jregions.core.parameterized.sizes;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jregions.core.JRegionsImmutableStyleType;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeValuesBIType;
import org.immutables.value.Value;

import java.math.BigInteger;

/**
 * The size of an area with <tt>BigInteger</tt> coordinates.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

@JRegionsImmutableStyleType
@Value.Immutable
public interface PAreaSizeBIType<S> extends AreaSizeValuesBIType
{
  @Override
  @Value.Parameter(order = 0)
  BigInteger sizeX();

  @Override
  @Value.Parameter(order = 1)
  BigInteger sizeY();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    Preconditions.checkPrecondition(
      this.sizeX(),
      this.sizeX().compareTo(BigInteger.ZERO) >= 0,
      d -> "Width must be non-negative");
    Preconditions.checkPrecondition(
      this.sizeY(),
      this.sizeY().compareTo(BigInteger.ZERO) >= 0,
      d -> "Height must be non-negative");
  }

  /**
   * A builder for size values.
   *
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   */

  abstract class Builder<S>
  {
    abstract PAreaSizeBI.Builder<S> setSizeX(BigInteger size_x);

    abstract PAreaSizeBI.Builder<S> setSizeY(BigInteger size_y);

    /**
     * Set the width.
     *
     * @param width The width
     *
     * @return The builder
     *
     * @deprecated Use {@link #setSizeX(BigInteger)}
     */

    @Deprecated
    public final PAreaSizeBI.Builder<S> setWidth(
      final BigInteger width)
    {
      return this.setSizeX(width);
    }

    /**
     * Set the height.
     *
     * @param height The height
     *
     * @return The builder
     *
     * @deprecated Use {@link #setSizeY(BigInteger)}
     */

    @Deprecated
    public final PAreaSizeBI.Builder<S> setHeight(
      final BigInteger height)
    {
      return this.setSizeY(height);
    }
  }
}
