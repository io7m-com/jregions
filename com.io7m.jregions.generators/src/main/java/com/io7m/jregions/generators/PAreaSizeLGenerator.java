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
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeL;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

/**
 * A generator for area sizes.
 *
 * @param <S> A phantom type parameter indicating the coordinate space of the
 *            area
 */

public final class PAreaSizeLGenerator<S> implements Generator<PAreaSizeL<S>>
{
  private final LongGenerator gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public PAreaSizeLGenerator(
    final LongGenerator in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "gen");
  }

  /**
   * @param <S> A phantom type parameter indicating the coordinate space of the
   *            area
   *
   * @return A generator initialized with useful defaults
   */

  public static <S> PAreaSizeLGenerator<S> create()
  {
    return new PAreaSizeLGenerator<>(new LongGenerator(0L, 10000L));
  }

  @Override
  public PAreaSizeL<S> next()
  {
    return PAreaSizeL.of(
      this.gen.next().longValue(),
      this.gen.next().longValue());
  }
}
