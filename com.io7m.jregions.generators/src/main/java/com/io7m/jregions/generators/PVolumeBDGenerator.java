/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABDLITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jregions.generators;

import java.util.Objects;
import com.io7m.jregions.core.parameterized.volumes.PVolumeBD;
import net.java.quickcheck.Generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A generator for volumes.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            volume
 */

public final class PVolumeBDGenerator<S> implements Generator<PVolumeBD<S>>
{
  private final Generator<BigDecimal> gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PVolumeBDGenerator(
    final Generator<BigDecimal> in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            volume
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PVolumeBDGenerator<S> create()
  {
    return new PVolumeBDGenerator<>(new Generator<BigDecimal>()
    {
      private final Random random = new Random();

      @Override
      public BigDecimal next()
      {
        return BigDecimal.valueOf(Math.abs(this.random.nextLong()));
      }
    });
  }

  @Override
  public PVolumeBD<S> next()
  {
    final List<BigDecimal> order = new ArrayList<>(2);
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final BigDecimal x_min = BigDecimal.valueOf(order.get(0).longValue());
    final BigDecimal x_max = BigDecimal.valueOf(order.get(1).longValue());

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final BigDecimal y_min = BigDecimal.valueOf(order.get(0).longValue());
    final BigDecimal y_max = BigDecimal.valueOf(order.get(1).longValue());

    order.clear();
    order.add(this.gen.next());
    order.add(this.gen.next());
    Collections.sort(order);

    final BigDecimal z_min = BigDecimal.valueOf(order.get(0).longValue());
    final BigDecimal z_max = BigDecimal.valueOf(order.get(1).longValue());

    return PVolumeBD.of(x_min, x_max, y_min, y_max, z_min, z_max);
  }
}
