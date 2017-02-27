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
import com.io7m.jregions.core.parameterized.PAreaI;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PAreaITest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    final PAreaI<Object> area = PAreaI.of(0, 100, 0, 100);
    Assert.assertEquals(0L, (long) area.minimumX());
    Assert.assertEquals(0L, (long) area.minimumY());
    Assert.assertEquals(100L, (long) area.width());
    Assert.assertEquals(100L, (long) area.height());
    Assert.assertEquals(100L, (long) area.maximumX());
    Assert.assertEquals(100L, (long) area.maximumY());
  }

  @Test
  public void testBadX()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("X"));
    PAreaI.of(10, 9, 0, 100);
  }

  @Test
  public void testBadY()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Y"));
    PAreaI.of(0, 100, 10, 9);
  }
}
