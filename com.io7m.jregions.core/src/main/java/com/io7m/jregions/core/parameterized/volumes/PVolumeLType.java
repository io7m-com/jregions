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

package com.io7m.jregions.core.parameterized.volumes;

import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jregions.core.JRegionsImmutableStyleType;
import com.io7m.jregions.core.unparameterized.volumes.VolumeValuesLType;
import org.immutables.value.Value;

/**
 * <p>A volume with <tt>long</tt> coordinates.</p>
 *
 * <p>The coordinates of the area are given in <i>half-closed</i> form. That is,
 * {@link #minimumX()} refers to the minimum <i>inclusive</i> value on the X
 * axis, and {@link #maximumX()} refers to the maximum <i>exclusive</i> value on
 * the X axis. Likewise for the Y and Z axes.</p>
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            volume
 */

@JRegionsImmutableStyleType
@Value.Immutable
public interface PVolumeLType<S> extends VolumeValuesLType
{
  @Override
  @Value.Parameter(order = 0)
  long minimumX();

  @Override
  @Value.Parameter(order = 1)
  long maximumX();

  @Override
  @Value.Parameter(order = 2)
  long minimumY();

  @Override
  @Value.Parameter(order = 3)
  long maximumY();

  @Override
  @Value.Parameter(order = 4)
  long minimumZ();

  @Override
  @Value.Parameter(order = 5)
  long maximumZ();

  /**
   * Check the preconditions for the parameters.
   */

  @Value.Check
  default void checkPreconditions()
  {
    Preconditions.checkPreconditionL(
      this.maximumX(),
      this.maximumX() >= this.minimumX(),
      x -> "X maximum must be >= X minimum");
    Preconditions.checkPreconditionL(
      this.maximumY(),
      this.maximumY() >= this.minimumY(),
      y -> "Y maximum must be >= Y minimum");
    Preconditions.checkPreconditionL(
      this.maximumZ(),
      this.maximumZ() >= this.minimumZ(),
      z -> "Z maximum must be >= Z minimum");
  }
}
