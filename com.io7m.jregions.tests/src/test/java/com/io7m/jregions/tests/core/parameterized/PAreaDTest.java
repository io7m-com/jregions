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

package com.io7m.jregions.tests.core.parameterized;

import com.io7m.jaffirm.core.PreconditionViolationException;
import com.io7m.jregions.core.parameterized.PAreaD;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PAreaDTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    final PAreaD<Object> area = PAreaD.of((double) 0, 100.0, (double) 0, 100.0);
    Assert.assertEquals(0.0, area.minimumX(), 0.0);
    Assert.assertEquals(0.0, area.minimumY(), 0.0);
    Assert.assertEquals(100.0, area.width(), 0.0);
    Assert.assertEquals(100.0, area.height(), 0.0);
    Assert.assertEquals(100.0, area.maximumX(), 0.0);
    Assert.assertEquals(100.0, area.maximumY(), 0.0);
  }

  @Test
  public void testBadX()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("X"));
    PAreaD.of(10.0, 9.0, 0.0, 100.0);
  }

  @Test
  public void testBadY()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Y"));
    PAreaD.of((double) 0, 100.0, 10.0, 9.0);
  }
}
