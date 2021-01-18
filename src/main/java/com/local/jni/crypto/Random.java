/**
 * Copyright 2013 Bruno Oliveira, and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.local.jni.crypto;

import static com.local.jni.SodiumConstants.RANDOMBYTES_SEEDBYTES;
import static com.local.jni.NaCl.sodium;

public class Random {

    private static final int DEFAULT_SIZE = 32;

    /**
     * Generate random bytes
     *
     * @param n number or random bytes
     * @return Byte array with random bytes
     */
    public byte[] randomBytes(int n) {
        byte[] buffer = new byte[n];
        sodium().randombytes(buffer, n);
        return buffer;
    }

    public byte[] randomBytes() {
        byte[] buffer = new byte[DEFAULT_SIZE];
        sodium().randombytes(buffer, DEFAULT_SIZE);
        return buffer;
    }

    /**
     * Deterministically generate pseudorandom bytes of length 'size' from a seed
     *
     * @param seed the seed to generate the bytes from 
     * @param size the size of the buffer
     * @return Byte array with deterministically generated pseudorandom bytes
     */
    public byte[] randomBytesDeterministic(byte[] seed, int size) {
        if (seed == null || seed.length != RANDOMBYTES_SEEDBYTES)
            throw new RuntimeException("Invalid size: " + seed.length);
        byte[] buffer = new byte[size];
        sodium().randombytes_buf_deterministic(buffer, size, seed);
        return buffer;
    }
    
}
