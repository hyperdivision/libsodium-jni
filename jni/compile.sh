#!/bin/bash -ev

set -e

. ../setenv.sh

if [ -z "$JAVA_HOME" ]; then
    echo "ERROR You should set JAVA_HOME"
    echo "Exiting!"
    exit 1
fi

echo "${JAVA_HOME}"


C_INCLUDE_PATH="${JAVA_HOME}/include:${JAVA_HOME}/include/linux:${JAVA_HOME}/include/darwin:/System/Library/Frameworks/JavaVM.framework/Headers"
export C_INCLUDE_PATH

rm -f *.java
rm -f *.c
rm -f *.so

export PATH=/usr/local/bin:$PATH

swig -I../libsodium/src/libsodium/include -java -package com.local.jni -outdir ../src/main/java/org/libsodium/jni sodium.i


./jnilib.sh
