/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> http://io7m.com
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

package com.io7m.jregions.tests.core;

import com.io7m.jregions.core.parameterized.areas.PAreaBD;
import com.io7m.jregions.core.parameterized.areas.PAreaBI;
import com.io7m.jregions.core.parameterized.areas.PAreaD;
import com.io7m.jregions.core.parameterized.areas.PAreaF;
import com.io7m.jregions.core.parameterized.areas.PAreaI;
import com.io7m.jregions.core.parameterized.areas.PAreaL;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitBD;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitBI;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitD;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitF;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitI;
import com.io7m.jregions.core.parameterized.areas.PAreaXSplitL;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitBD;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitBI;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitD;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitF;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitI;
import com.io7m.jregions.core.parameterized.areas.PAreaXYSplitL;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitBD;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitBI;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitD;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitF;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitI;
import com.io7m.jregions.core.parameterized.areas.PAreaYSplitL;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeBD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeD;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeF;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeI;
import com.io7m.jregions.core.parameterized.sizes.PAreaSizeL;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeBD;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeBI;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeD;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeF;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeI;
import com.io7m.jregions.core.parameterized.sizes.PVolumeSizeL;
import com.io7m.jregions.core.parameterized.volumes.PVolumeBD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeBI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeF;
import com.io7m.jregions.core.parameterized.volumes.PVolumeI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeL;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitBD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitBI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitD;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitF;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitI;
import com.io7m.jregions.core.parameterized.volumes.PVolumeXYZSplitL;
import com.io7m.jregions.core.unparameterized.areas.AreaBD;
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import com.io7m.jregions.core.unparameterized.areas.AreaD;
import com.io7m.jregions.core.unparameterized.areas.AreaF;
import com.io7m.jregions.core.unparameterized.areas.AreaI;
import com.io7m.jregions.core.unparameterized.areas.AreaL;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitBD;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitBI;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitD;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitF;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitI;
import com.io7m.jregions.core.unparameterized.areas.AreaXSplitL;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitBD;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitBI;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitD;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitF;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitI;
import com.io7m.jregions.core.unparameterized.areas.AreaXYSplitL;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitBD;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitBI;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitD;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitF;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitI;
import com.io7m.jregions.core.unparameterized.areas.AreaYSplitL;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeD;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeF;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeI;
import com.io7m.jregions.core.unparameterized.sizes.AreaSizeL;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeBD;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeBI;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeD;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeF;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeI;
import com.io7m.jregions.core.unparameterized.sizes.VolumeSizeL;
import com.io7m.jregions.core.unparameterized.volumes.VolumeBD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeBI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeF;
import com.io7m.jregions.core.unparameterized.volumes.VolumeI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeL;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitBD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitBI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitD;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitF;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitI;
import com.io7m.jregions.core.unparameterized.volumes.VolumeXYZSplitL;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BruteForceEqualityTest
{
  @Test
  public void testEquals()
  {
    final var classes = List.of(
      PAreaBD.class,
      PAreaBI.class,
      PAreaD.class,
      PAreaF.class,
      PAreaI.class,
      PAreaL.class,
      PAreaXSplitBD.class,
      PAreaXSplitBI.class,
      PAreaXSplitD.class,
      PAreaXSplitF.class,
      PAreaXSplitI.class,
      PAreaXSplitL.class,
      PAreaXYSplitBD.class,
      PAreaXYSplitBI.class,
      PAreaXYSplitD.class,
      PAreaXYSplitF.class,
      PAreaXYSplitI.class,
      PAreaXYSplitL.class,
      PAreaYSplitBD.class,
      PAreaYSplitBI.class,
      PAreaYSplitD.class,
      PAreaYSplitF.class,
      PAreaYSplitI.class,
      PAreaYSplitL.class,
      PAreaSizeBD.class,
      PAreaSizeBD.class,
      PAreaSizeD.class,
      PAreaSizeF.class,
      PAreaSizeI.class,
      PAreaSizeL.class,
      PVolumeSizeBD.class,
      PVolumeSizeBI.class,
      PVolumeSizeD.class,
      PVolumeSizeF.class,
      PVolumeSizeI.class,
      PVolumeSizeL.class,
      PVolumeBD.class,
      PVolumeBI.class,
      PVolumeD.class,
      PVolumeF.class,
      PVolumeI.class,
      PVolumeL.class,
      PVolumeXYZSplitBD.class,
      PVolumeXYZSplitBI.class,
      PVolumeXYZSplitD.class,
      PVolumeXYZSplitF.class,
      PVolumeXYZSplitI.class,
      PVolumeXYZSplitL.class,
      AreaBD.class,
      AreaBI.class,
      AreaD.class,
      AreaF.class,
      AreaI.class,
      AreaL.class,
      AreaXSplitBD.class,
      AreaXSplitBI.class,
      AreaXSplitD.class,
      AreaXSplitF.class,
      AreaXSplitI.class,
      AreaXSplitL.class,
      AreaXYSplitBD.class,
      AreaXYSplitBI.class,
      AreaXYSplitD.class,
      AreaXYSplitF.class,
      AreaXYSplitI.class,
      AreaXYSplitL.class,
      AreaYSplitBD.class,
      AreaYSplitBI.class,
      AreaYSplitD.class,
      AreaYSplitF.class,
      AreaYSplitI.class,
      AreaYSplitL.class,
      AreaSizeBD.class,
      AreaSizeBI.class,
      AreaSizeD.class,
      AreaSizeF.class,
      AreaSizeI.class,
      AreaSizeL.class,
      VolumeSizeBD.class,
      VolumeSizeBI.class,
      VolumeSizeD.class,
      VolumeSizeF.class,
      VolumeSizeI.class,
      VolumeSizeL.class,
      VolumeBD.class,
      VolumeBI.class,
      VolumeD.class,
      VolumeF.class,
      VolumeI.class,
      VolumeL.class,
      VolumeXYZSplitBD.class,
      VolumeXYZSplitBI.class,
      VolumeXYZSplitD.class,
      VolumeXYZSplitF.class,
      VolumeXYZSplitI.class,
      VolumeXYZSplitL.class
    );

    for (final var clazz : classes) {
      EqualsVerifier.forClass(clazz)
        .suppress(Warning.NULL_FIELDS)
        .verify();
    }
  }
}
