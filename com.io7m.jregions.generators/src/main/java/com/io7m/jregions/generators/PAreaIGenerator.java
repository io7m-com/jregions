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
import com.io7m.jregions.core.parameterized.PAreaI;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.IntegerGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generator for areas.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

public final class PAreaIGenerator<S> implements Generator<PAreaI<S>>
{
  private final IntegerGenerator gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PAreaIGenerator(
    final IntegerGenerator in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PAreaIGenerator<S> create()
  {
    return new PAreaIGenerator<>(new IntegerGenerator(0, 10000));
  }

  @Override
  public PAreaI<S> next()
  {
    final List<Integer> order = new ArrayList<>(2);
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final int x_min = order.get(0).intValue();
    final int x_max = order.get(1).intValue();

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final int y_min = order.get(0).intValue();
    final int y_max = order.get(1).intValue();

    return PAreaI.of(x_min, x_max, y_min, y_max);
  }
}
