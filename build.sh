#!/usr/bin/env bash
./gradlew clean
./gradlew --stacktrace autopatchbase:uploadArchives
./gradlew --stacktrace  auto-patch-plugin:uploadArchives
./gradlew --stacktrace gradle-plugin:uploadArchives
./gradlew --stacktrace patch:uploadArchives

#打原生包和补丁包时应用
./gradlew  assembleDebug  -Dorg.gradle.daemon=false -Dorg.gradle.debug=false --stacktrace
#测试时补丁上传命令
 adb push app/build/outputs/robust/patch.jar /storage/emulated/0/Android/data/com.meituan.robust.sample/files/robust/patch.jar
