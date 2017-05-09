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

import com.io7m.jregions.core.JRegionsImmutableStyleType;
import org.immutables.value.Value;

/**
 * A volume that has been split into octants via an X, Y, and Z axis.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 * @param <T> The precise type of volume
 */

@JRegionsImmutableStyleType
@Value.Immutable
public interface PVolumeXYZSplitBIType<S, T extends PVolumeBIType<S>>
{
  /**
   * @return The minimum X, minimum Y, minimum Z octant
   */

  @Value.Parameter
  T x0y0z0();

  /**
   * @return The maximum X, minimum Y, minimum Z octant
   */

  @Value.Parameter
  T x1y0z0();

  /**
   * @return The minimum X, maximum Y, minimum Z octant
   */

  @Value.Parameter
  T x0y1z0();

  /**
   * @return The maximum X, maximum Y, minimum Z octant
   */

  @Value.Parameter
  T x1y1z0();

  /**
   * @return The minimum X, minimum Y, maximum Z octant
   */

  @Value.Parameter
  T x0y0z1();

  /**
   * @return The maximum X, minimum Y, maximum Z octant
   */

  @Value.Parameter
  T x1y0z1();

  /**
   * @return The minimum X, maximum Y, maximum Z octant
   */

  @Value.Parameter
  T x0y1z1();

  /**
   * @return The maximum X, maximum Y, maximum Z octant
   */

  @Value.Parameter
  T x1y1z1();
}
