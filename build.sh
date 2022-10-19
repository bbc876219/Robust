#!/usr/bin/env bash
./gradlew clean
./gradlew --stacktrace autopatchbase:uploadArchives
./gradlew --stacktrace  auto-patch-plugin:uploadArchives
./gradlew --stacktrace gradle-plugin:uploadArchives
./gradlew --stacktrace patch:uploadArchives