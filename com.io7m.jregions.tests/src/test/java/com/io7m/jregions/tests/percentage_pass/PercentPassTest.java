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

package com.io7m.jregions.tests.percentage_pass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(PercentPassExtension.class)
public final class PercentPassTest
{
  private static final Logger LOG =
    LoggerFactory.getLogger(PercentPassTest.class);

  private static int EXECS;

  @BeforeEach
  public void setup()
  {
    LOG.debug("setup");
  }

  @AfterEach
  public void tearDown()
  {
    LOG.debug("tearDown");
  }

  @PercentPassing
  public void testExecute1()
  {
    LOG.debug("testExecute1");
  }

  @PercentPassing(executionCount = 1000)
  public void testExecute2()
  {
    LOG.debug("testExecute2");
    ++EXECS;
    if (EXECS > 990) {
      Assertions.fail();
    }
  }
}
