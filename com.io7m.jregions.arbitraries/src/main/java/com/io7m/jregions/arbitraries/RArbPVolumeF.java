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

import com.io7m.jregions.core.parameterized.volumes.PVolumeF;
import net.jqwik.api.Arbitraries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RArbPVolumeF extends RArbAbstract<PVolumeF>
{
  public RArbPVolumeF()
  {
    super(
      PVolumeF.class,
      () -> {
        final var gen =
          Arbitraries.floats()
            .between(0.0f, 10000.0f);

        return Arbitraries.create(() -> {
          final List<Float> order = new ArrayList<>(2);
          order.add(gen.sample());
          order.add(gen.sample());
          Collections.sort(order);

          final var x_min = order.get(0).floatValue();
          final var x_max = order.get(1).floatValue();

          order.clear();
          order.add(gen.sample());
          order.add(gen.sample());
          Collections.sort(order);

          final var y_min = order.get(0).floatValue();
          final var y_max = order.get(1).floatValue();

          order.clear();
          order.add(gen.sample());
          order.add(gen.sample());
          Collections.sort(order);

          final var z_min = order.get(0).floatValue();
          final var z_max = order.get(1).floatValue();

          return PVolumeF.of(x_min, x_max, y_min, y_max, z_min, z_max);
        });
      }
    );
  }
}
