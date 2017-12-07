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

import java.util.Objects;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBD;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

import java.math.BigDecimal;

/**
 * A generator for area sizes.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

public final class PAreaSizeBDGenerator<S> implements Generator<PAreaSizeBD<S>>
{
  private final Generator<BigDecimal> gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PAreaSizeBDGenerator(
    final Generator<BigDecimal> in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PAreaSizeBDGenerator<S> create()
  {
    final LongGenerator gen = new LongGenerator(0L, Long.MAX_VALUE);
    return new PAreaSizeBDGenerator<>(() -> new BigDecimal(gen.next().toString()));
  }

  @Override
  public PAreaSizeBD<S> next()
  {
    return PAreaSizeBD.of(this.gen.next(), this.gen.next());
  }
}
