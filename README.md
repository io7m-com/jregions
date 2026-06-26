jregions
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jregions/com.io7m.jregions.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jregions%22)
[![Maven Central (snapshot)](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fcom%2Fio7m%2Fjregions%2Fcom.io7m.jregions%2Fmaven-metadata.xml&style=flat-square)](https://central.sonatype.com/repository/maven-snapshots/com/io7m/jregions/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jregions.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jregions)
![Java Version](https://img.shields.io/badge/21-java?label=java&color=e6c35c)

![com.io7m.jregions](./src/site/resources/jregions.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jregions/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jregions/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jregions/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jregions/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jregions/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jregions/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jregions/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jregions/actions?query=workflow%3Amain.windows.temurin.lts)|

## Repository Relocation

Development of this project has moved to an
[open-source but not open-contribution](https://sqlite.org/copyright.html#notopencontrib)
model.

Source code and commits will remain publicly available perpetually, but issues
and/or pull requests will be rejected and/or ignored. Additionally, this project
will now only be available via a read-only mirror at:

  https://codeberg.org/io7m-com/jregions


## jregions

The `jregions` package provides a set of immutable area types specialized
to most of the Java numeric types. The area types come with an extensive set
of functions for aligning and moving areas.

## Features

* Extensive set of functions for scaling, aligning, moving boxes.
* Written in pure Java 21.
* High coverage test suite.
* [OSGi-ready](https://www.osgi.org/)
* [JPMS-ready](https://en.wikipedia.org/wiki/Java_Platform_Module_System)
* ISC license.

