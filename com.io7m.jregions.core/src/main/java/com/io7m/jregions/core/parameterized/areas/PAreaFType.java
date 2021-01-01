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

package com.io7m.jregions.core.parameterized.areas;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jaffirm.core.Preconditions;
import com.io7m.jregions.core.unparameterized.areas.AreaValuesFType;
import org.immutables.value.Value;

/**
 * <p>An area with {@code float} coordinates.</p>
 *
 * <p>The coordinates of the area are given in <i>half-closed</i> form. That is,
 * {@link #minimumX()} refers to the minimum <i>inclusive</i> value on the X
 * axis, and {@link #maximumX()} refers to the maximum <i>exclusive</i> value on
 * the X axis. Likewise for the Y axis.</p>
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

@ImmutablesStyleType
@Value.Immutable
public interface PAreaFType<S> extends AreaValuesFType
{
  @Override
  @Value.Parameter(order = 0)
  float minimumX();

  @Override
  @Value.Parameter(order = 1)
  float maximumX();

  @Override
  @Value.Parameter(order = 2)
  float minimumY();

  @Override
  @Value.Parameter(order = 3)
  float maximumY();

  /**
   * Check the preconditions for the parameters.
   */

  @Value.Check
  default void checkPreconditions()
  {
    Preconditions.checkPreconditionD(
      this.maximumX(),
      this.maximumX() >= this.minimumX(),
      x -> "X maximum must be >= X minimum");
    Preconditions.checkPreconditionD(
      this.maximumY(),
      this.maximumY() >= this.minimumY(),
      y -> "Y maximum must be >= Y minimum");
  }
}
