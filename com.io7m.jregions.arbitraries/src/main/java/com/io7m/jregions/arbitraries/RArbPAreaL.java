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

import com.io7m.jregions.core.parameterized.areas.PAreaL;
import com.io7m.jregions.core.parameterized.areas.PAreasL;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Combinators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RArbPAreaL extends RArbAbstract<PAreaL>
{
  public RArbPAreaL()
  {
    super(
      PAreaL.class,
      () -> {
        final var gen =
          Arbitraries.longs()
            .between(0L, 10000L);

        return Arbitraries.create(() -> {
          final List<Long> order = new ArrayList<>(2);
          order.add(gen.sample());
          order.add(gen.sample());
          Collections.sort(order);

          final var x_min = order.get(0).longValue();
          final var x_max = order.get(1).longValue();

          order.clear();
          order.add(gen.sample());
          order.add(gen.sample());
          Collections.sort(order);

          final var y_min = order.get(0).longValue();
          final var y_max = order.get(1).longValue();

          return PAreaL.of(x_min, x_max, y_min, y_max);
        });
      }
    );
  }
}
