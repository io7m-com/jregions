/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import net.jqwik.api.providers.ArbitraryProvider;

/**
 * Area types (JQwik arbitraries)
 */

module com.io7m.jregions.arbitraries
{
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  requires com.io7m.jregions.core;
  requires net.jqwik.api;

  uses ArbitraryProvider;

  provides ArbitraryProvider with
    com.io7m.jregions.arbitraries.RArbAreaBD,
    com.io7m.jregions.arbitraries.RArbAreaBI,
    com.io7m.jregions.arbitraries.RArbAreaD,
    com.io7m.jregions.arbitraries.RArbAreaF,
    com.io7m.jregions.arbitraries.RArbAreaI,
    com.io7m.jregions.arbitraries.RArbAreaL,
    com.io7m.jregions.arbitraries.RArbAreaSizeBD,
    com.io7m.jregions.arbitraries.RArbAreaSizeBI,
    com.io7m.jregions.arbitraries.RArbAreaSizeD,
    com.io7m.jregions.arbitraries.RArbAreaSizeF,
    com.io7m.jregions.arbitraries.RArbAreaSizeI,
    com.io7m.jregions.arbitraries.RArbAreaSizeL,
    com.io7m.jregions.arbitraries.RArbPAreaBD,
    com.io7m.jregions.arbitraries.RArbPAreaBI,
    com.io7m.jregions.arbitraries.RArbPAreaD,
    com.io7m.jregions.arbitraries.RArbPAreaF,
    com.io7m.jregions.arbitraries.RArbPAreaI,
    com.io7m.jregions.arbitraries.RArbPAreaL,
    com.io7m.jregions.arbitraries.RArbPAreaSizeBD,
    com.io7m.jregions.arbitraries.RArbPAreaSizeBI,
    com.io7m.jregions.arbitraries.RArbPAreaSizeD,
    com.io7m.jregions.arbitraries.RArbPAreaSizeF,
    com.io7m.jregions.arbitraries.RArbPAreaSizeI,
    com.io7m.jregions.arbitraries.RArbPAreaSizeL,
    com.io7m.jregions.arbitraries.RArbPVolumeBD,
    com.io7m.jregions.arbitraries.RArbPVolumeBI,
    com.io7m.jregions.arbitraries.RArbPVolumeD,
    com.io7m.jregions.arbitraries.RArbPVolumeF,
    com.io7m.jregions.arbitraries.RArbPVolumeI,
    com.io7m.jregions.arbitraries.RArbPVolumeL,
    com.io7m.jregions.arbitraries.RArbVolumeBD,
    com.io7m.jregions.arbitraries.RArbVolumeBI,
    com.io7m.jregions.arbitraries.RArbVolumeD,
    com.io7m.jregions.arbitraries.RArbVolumeF,
    com.io7m.jregions.arbitraries.RArbVolumeI,
    com.io7m.jregions.arbitraries.RArbVolumeL
  ;

  exports com.io7m.jregions.arbitraries;
}
