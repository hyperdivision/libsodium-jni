#!/bin/sh

./submodule-update.sh

cd libsodium
./autogen.sh
./dist-build/android-arm.sh
./dist-build/android-armv7-a.sh
./dist-build/android-mips32.sh
./dist-build/android-x86.sh
./dist-build/android-x86_64.sh
