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

/**
 * Area types (Core)
 */

module com.io7m.jregions.core
{
  requires static com.io7m.immutables.style;
  requires static org.immutables.value;
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  requires com.io7m.junreachable.core;
  requires com.io7m.jaffirm.core;

  exports com.io7m.jregions.core.conversions;
  exports com.io7m.jregions.core.parameterized.areas;
  exports com.io7m.jregions.core.parameterized.sizes;
  exports com.io7m.jregions.core.parameterized.volumes;
  exports com.io7m.jregions.core.unparameterized.areas;
  exports com.io7m.jregions.core.unparameterized.sizes;
  exports com.io7m.jregions.core.unparameterized.volumes;

  opens com.io7m.jregions.core.conversions
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.parameterized.areas
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.parameterized.sizes
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.parameterized.volumes
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.unparameterized.areas
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.unparameterized.sizes
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
  opens com.io7m.jregions.core.unparameterized.volumes
    to com.io7m.jregions.tests, nl.jqno.equalsverifier;
}