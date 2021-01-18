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

package com.local.jni.keys;

import com.local.jni.crypto.Point;
import com.local.jni.crypto.Util;
import com.local.jni.encoders.Encoder;

import static com.local.jni.SodiumConstants.PUBLICKEY_BYTES;
import static com.local.jni.SodiumConstants.SECRETKEY_BYTES;
import static com.local.jni.NaCl.sodium;
import static com.local.jni.crypto.Util.checkLength;
import static com.local.jni.crypto.Util.zeros;

public class KeyPair {

    private byte[] publicKey;
    private byte[] seed;
    private final byte[] secretKey;

    public KeyPair() {
        this.secretKey = zeros(SECRETKEY_BYTES);
        this.publicKey = zeros(PUBLICKEY_BYTES);
        sodium().crypto_box_curve25519xsalsa20poly1305_keypair(publicKey, secretKey);
    }


    public KeyPair(byte[] seed){
        Util.checkLength(seed, SECRETKEY_BYTES);
        this.seed = seed;
        this.secretKey = zeros(SECRETKEY_BYTES);
        this.publicKey = zeros(PUBLICKEY_BYTES);
        Util.isValid(sodium().crypto_box_curve25519xsalsa20poly1305_seed_keypair(publicKey, secretKey, seed), "Failed to generate a key pair");
    }

//    public KeyPair(byte[] secretKey) {
//        this.secretKey = secretKey;
//        checkLength(this.secretKey, SECRETKEY_BYTES);
//    }

    public KeyPair(String secretKey, Encoder encoder) {
        this(encoder.decode(secretKey));
    }

    public PublicKey getPublicKey() {
        Point point = new Point();
        byte[] key = publicKey != null ? publicKey : point.mult(secretKey).toBytes();
        return new PublicKey(key);
    }

    public PrivateKey getPrivateKey() {
        return new PrivateKey(secretKey);
    }
}
