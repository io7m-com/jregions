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
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeI;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.IntegerGenerator;

/**
 * A generator for area sizes.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

public final class PAreaSizeIGenerator<S> implements Generator<PAreaSizeI<S>>
{
  private final IntegerGenerator gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PAreaSizeIGenerator(
    final IntegerGenerator in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PAreaSizeIGenerator<S> create()
  {
    return new PAreaSizeIGenerator<>(new IntegerGenerator(0, 10000));
  }

  @Override
  public PAreaSizeI<S> next()
  {
    return PAreaSizeI.of(
      this.gen.next().intValue(),
      this.gen.next().intValue());
  }
}
