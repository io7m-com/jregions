/*
 * Copyright © 2023 Mark Raynsford <code@io7m.com> https://www.io7m.com
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


package com.io7m.jregions.arbitraries;


import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Combinators;

public final class RArbAreaSizeF extends RArbAbstract<AreaSizeF>
{
  public RArbAreaSizeF()
  {
    super(
      AreaSizeF.class,
      () -> {
        return Combinators.combine(
          Arbitraries.floats().between(0.0f, 10000.0f),
          Arbitraries.floats().between(0.0f, 10000.0f)
        ).as(AreaSizeF::of);
      }
    );
  }
}
