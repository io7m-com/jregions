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

/**
 * Unit tests.
 */

import net.jqwik.api.providers.ArbitraryProvider;

open module com.io7m.jregions.tests
{
  requires com.io7m.jregions.core;
  requires com.io7m.jregions.arbitraries;

  requires com.io7m.jaffirm.core;
  requires com.io7m.junreachable.core;
  requires com.io7m.percentpass.extension;
  requires net.jqwik.api;
  requires nl.jqno.equalsverifier;

  requires org.junit.jupiter.api;
  requires org.junit.jupiter.engine;
  requires org.junit.platform.commons;
  requires org.junit.platform.engine;
  requires org.junit.platform.launcher;

  uses ArbitraryProvider;

  exports com.io7m.jregions.tests.bugs;
  exports com.io7m.jregions.tests.core.conversions;
  exports com.io7m.jregions.tests.core.parameterized;
  exports com.io7m.jregions.tests.core.unparameterized;
  exports com.io7m.jregions.tests.core;
  exports com.io7m.jregions.tests;
}