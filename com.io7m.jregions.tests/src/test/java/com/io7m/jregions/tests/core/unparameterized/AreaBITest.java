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

package com.io7m.jregions.tests.core.unparameterized;

import com.io7m.jaffirm.core.PreconditionViolationException;
import com.io7m.jregions.core.unparameterized.areas.AreaBI;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

public final class AreaBITest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testIdentities()
  {
    final AreaBI area = AreaBI.of(
      BigInteger.ZERO,
      new BigInteger("100"),
      BigInteger.ZERO,
      new BigInteger("100"));
    Assert.assertEquals(BigInteger.ZERO, area.minimumX());
    Assert.assertEquals(BigInteger.ZERO, area.minimumY());
    Assert.assertEquals(new BigInteger("100"), area.width());
    Assert.assertEquals(new BigInteger("100"), area.height());
    Assert.assertEquals(new BigInteger("100"), area.maximumX());
    Assert.assertEquals(new BigInteger("100"), area.maximumY());
  }

  @Test
  public void testBadX()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("X"));
    AreaBI.of(
      BigInteger.TEN,
      new BigInteger("9"),
      BigInteger.ZERO,
      new BigInteger("100"));
  }

  @Test
  public void testBadY()
  {
    this.expected.expect(PreconditionViolationException.class);
    this.expected.expectMessage(StringContains.containsString("Y"));
    AreaBI.of(
      BigInteger.ZERO,
      new BigInteger("100"),
      BigInteger.TEN,
      new BigInteger("9"));
  }
}
