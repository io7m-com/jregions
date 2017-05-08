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

package com.io7m.jregions.core.unparameterized.volumes;

import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeValuesIType;
import org.immutables.value.Value;

/**
 * <p>A volume with <tt>int</tt> coordinates.</p>
 *
 * <p>The coordinates of the volume are given in <i>half-closed</i> form. That
 * is, {@link #minimumX()} refers to the minimum <i>inclusive</i> value on the X
 * axis, and {@link #maximumX()} refers to the maximum <i>exclusive</i> value on
 * the X axis. Likewise for the Y and Z axes.</p>
 */

public interface VolumeValuesIType extends VolumeSizeValuesIType
{
  /**
   * @return The value on the X axis of the minimum edge of the box (inclusive)
   */

  @Value.Parameter(order = 0)
  int minimumX();

  /**
   * @return The value on the X axis of the maximum edge of the box (exclusive)
   */

  @Value.Parameter(order = 1)
  int maximumX();

  /**
   * @return The value on the Y axis of the minimum edge of the box (inclusive)
   */

  @Value.Parameter(order = 2)
  int minimumY();

  /**
   * @return The value on the Y axis of the maximum edge of the box (exclusive)
   */

  @Value.Parameter(order = 3)
  int maximumY();

  /**
   * @return The value on the Z axis of the minimum edge of the box (inclusive)
   */

  @Value.Parameter(order = 4)
  int minimumZ();

  /**
   * @return The value on the Z axis of the maximum edge of the box (exclusive)
   */

  @Value.Parameter(order = 5)
  int maximumZ();

  @Override
  default int sizeX()
  {
    return Math.subtractExact(this.maximumX(), this.minimumX());
  }

  @Override
  default int sizeY()
  {
    return Math.subtractExact(this.maximumY(), this.minimumY());
  }

  @Override
  default int sizeZ()
  {
    return Math.subtractExact(this.maximumZ(), this.minimumZ());
  }
}
