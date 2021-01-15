/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package com.io7m.jregions.tests.percentage_pass;

import java.util.Objects;

final class PercentPassDisplayNameFormatter
{
  private final String displayName;

  PercentPassDisplayNameFormatter(
    final String inDisplayName)
  {
    this.displayName =
      Objects.requireNonNull(inDisplayName, "displayName");
  }

  public String format(
    final int currentRepetition,
    final int totalRepetitions)
  {
    return String.format(
      "%s (%d of %d)",
      this.displayName,
      Integer.valueOf(currentRepetition),
      Integer.valueOf(totalRepetitions)
    );
  }
}
