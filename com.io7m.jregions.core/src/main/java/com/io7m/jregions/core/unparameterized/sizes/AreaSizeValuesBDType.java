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

package com.io7m.jregions.core.unparameterized.sizes;

import org.immutables.value.Value;

import java.math.BigDecimal;

/**
 * The size of an area with <tt>BigDecimal</tt> coordinates.
 */

public interface AreaSizeValuesBDType
{
  /**
   * @return The size of the area on the X axis
   */

  @Value.Parameter(order = 0)
  BigDecimal sizeX();

  /**
   * @return The size of the area on the Y axis
   */

  @Value.Parameter(order = 1)
  BigDecimal sizeY();

  /**
   * @return The width of the area
   *
   * @deprecated Use {@link #sizeX()}
   */

  @Deprecated
  @Value.Derived
  @Value.Auxiliary
  default BigDecimal width()
  {
    return this.sizeX();
  }

  /**
   * @return The height of the area
   *
   * @deprecated Use {@link #sizeY()}
   */

  @Deprecated
  @Value.Derived
  @Value.Auxiliary
  default BigDecimal height()
  {
    return this.sizeY();
  }
}
