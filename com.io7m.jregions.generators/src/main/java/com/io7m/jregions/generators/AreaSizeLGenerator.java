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
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeL;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

/**
 * A generator for area sizes.
 */

public final class AreaSizeLGenerator implements Generator<AreaSizeL>
{
  private final LongGenerator gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public AreaSizeLGenerator(
    final LongGenerator in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "gen");
  }

  /**
   * @return A generator initialized with useful defaults
   */

  public static AreaSizeLGenerator create()
  {
    return new AreaSizeLGenerator(new LongGenerator(0L, 10000L));
  }

  @Override
  public AreaSizeL next()
  {
    return AreaSizeL.of(
      this.gen.next().longValue(),
      this.gen.next().longValue());
  }
}
