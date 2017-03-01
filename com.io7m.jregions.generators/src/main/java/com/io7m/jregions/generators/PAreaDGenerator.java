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
import com.io7m.jregions.core.parameterized.PAreaD;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A generator for areas.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

public final class PAreaDGenerator<S> implements Generator<PAreaD<S>>
{
  private final Generator<Double> gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PAreaDGenerator(
    final Generator<Double> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PAreaDGenerator<S> create()
  {
    return new PAreaDGenerator<>(new DoubleGenerator(0.0, 10000.0));
  }

  @Override
  public PAreaD<S> next()
  {
    final List<Double> order = new ArrayList<>(2);
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final double x_min = order.get(0).doubleValue();
    final double x_max = order.get(1).doubleValue();

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final double y_min = order.get(0).doubleValue();
    final double y_max = order.get(1).doubleValue();

    return PAreaD.of(x_min, x_max, y_min, y_max);
  }
}
