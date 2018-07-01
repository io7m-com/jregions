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

package com.io7m.jregions.core.unparameterized.areas;

import com.io7m.immutables.styles.ImmutablesStyleType;
import com.io7m.jaffirm.core.Preconditions;
import org.immutables.value.Value;

/**
 * <p>An area with <tt>int</tt> coordinates.</p>
 *
 * <p>The coordinates of the area are given in <i>half-closed</i> form. That is,
 * {@link #minimumX()} refers to the minimum <i>inclusive</i> value on the X
 * axis, and {@link #maximumX()} refers to the maximum <i>exclusive</i> value on
 * the X axis. Likewise for the Y axis.</p>
 */

@ImmutablesStyleType
@Value.Immutable
public interface AreaIType extends AreaValuesIType
{
  @Override
  @Value.Parameter(order = 0)
  int minimumX();

  @Override
  @Value.Parameter(order = 1)
  int maximumX();

  @Override
  @Value.Parameter(order = 2)
  int minimumY();

  @Override
  @Value.Parameter(order = 3)
  int maximumY();

  /**
   * Check the preconditions for the parameters.
   */

  @Value.Check
  default void checkPreconditions()
  {
    Preconditions.checkPreconditionI(
      this.maximumX(),
      this.maximumX() >= this.minimumX(),
      x -> "X maximum must be >= X minimum");
    Preconditions.checkPreconditionI(
      this.maximumY(),
      this.maximumY() >= this.minimumY(),
      y -> "Y maximum must be >= Y minimum");
  }
}
