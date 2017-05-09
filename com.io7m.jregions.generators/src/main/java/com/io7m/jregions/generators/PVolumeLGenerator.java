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

package com.io7m.jregions.generators;

import com.io7m.jnull.NullCheck;
import com.io7m.jregions.core.parameterized.volumes.PVolumeL;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generator for volumes.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            volume
 */

public final class PVolumeLGenerator<S> implements Generator<PVolumeL<S>>
{
  private final Generator<Long> gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PVolumeLGenerator(
    final Generator<Long> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            volume
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PVolumeLGenerator<S> create()
  {
    return new PVolumeLGenerator<>(new LongGenerator(0L, 10000L));
  }

  @Override
  public PVolumeL<S> next()
  {
    final List<Long> order = new ArrayList<>(2);
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final long x_min = order.get(0).longValue();
    final long x_max = order.get(1).longValue();

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final long y_min = order.get(0).longValue();
    final long y_max = order.get(1).longValue();

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final long z_min = order.get(0).longValue();
    final long z_max = order.get(1).longValue();

    return PVolumeL.of(x_min, x_max, y_min, y_max, z_min, z_max);
  }
}
