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
import com.io7m.jregions.core.AreaSizeB;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.LongGenerator;

import java.math.BigInteger;

/**
 * A generator for area sizes.
 */

public final class AreaSizeBGenerator implements Generator<AreaSizeB>
{
  private final Generator<BigInteger> gen;

  /**
   * Create a new generator.
   *
   * @param in_gen A number generator
   */

  public AreaSizeBGenerator(
    final Generator<BigInteger> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "gen");
  }

  /**
   * @return A generator initialized with useful defaults
   */

  public static AreaSizeBGenerator create()
  {
    final LongGenerator gen = new LongGenerator(0L, Long.MAX_VALUE);
    return new AreaSizeBGenerator(() -> new BigInteger(gen.next().toString()));
  }

  @Override
  public AreaSizeB next()
  {
    return AreaSizeB.of(this.gen.next(), this.gen.next());
  }
}