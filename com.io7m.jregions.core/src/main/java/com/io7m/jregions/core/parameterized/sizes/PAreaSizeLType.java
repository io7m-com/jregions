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

import com.io7m.jregions.core.JRegionsImmutableStyleType;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeValuesLType;
import org.immutables.value.Value;

/**
 * The size of an area with <tt>long</tt> coordinates.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

@JRegionsImmutableStyleType
@Value.Immutable
public interface PAreaSizeLType<S> extends AreaSizeValuesLType
{
  @Override
  @Value.Parameter(order = 0)
  long sizeX();

  @Override
  @Value.Parameter(order = 1)
  long sizeY();

  /**
   * A builder for size values.
   *
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   */

  abstract class Builder<S>
  {
    abstract PAreaSizeL.Builder<S> setSizeX(long size_x);

    abstract PAreaSizeL.Builder<S> setSizeY(long size_y);

    /**
     * Set the width.
     *
     * @param width The width
     *
     * @return The builder
     *
     * @deprecated Use {@link #setSizeX(long)}
     */

    @Deprecated
    public final PAreaSizeL.Builder<S> setWidth(
      final long width)
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
     * @deprecated Use {@link #setSizeY(long)}
     */

    @Deprecated
    public final PAreaSizeL.Builder<S> setHeight(
      final long height)
    {
      return this.setSizeY(height);
    }
  }
}
