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

import com.io7m.jregions.core.JRegionsImmutableStyleType;
import org.immutables.value.Value;

/**
 * An area that has been split into quadrants via an X and Y axis.
 *
 * @param <T> The precise type of area
 */

@JRegionsImmutableStyleType
@Value.Immutable
public interface AreaXYSplitIType<T extends AreaIType>
{
  /**
   * @return The minimum X, minimum Y quadrant
   */

  @Value.Parameter(order = 0)
  T x0y0();

  /**
   * @return The maximum X, minimum Y quadrant
   */

  @Value.Parameter(order = 1)
  T x1y0();

  /**
   * @return The minimum X, maximum Y quadrant
   */

  @Value.Parameter(order = 2)
  T x0y1();

  /**
   * @return The maximum X, maximum Y quadrant
   */

  @Value.Parameter(order = 3)
  T x1y1();
}
