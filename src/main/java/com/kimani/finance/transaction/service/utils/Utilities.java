package com.kimani.finance.transaction.service.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Utilities {
    public String createSHAHash(String hashFunctionName, String input) {
        final MessageDigest digest = getHashAlgorithm(hashFunctionName);
        final byte[] hashBytes = digest.digest(
                input.getBytes(StandardCharsets.UTF_8));
        return convertToHex(hashBytes);
    }

    /**
     * Convert data byte to hex to get the hashed value in hexadecimal
     */
    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    /**
     * Ensures that we can get the MessageDigest instance thread safely
     */
    private MessageDigest getHashAlgorithm(String hashFunctionName) {
        MessageDigest hashAlgorithm;
        try {
            hashAlgorithm = MessageDigest.getInstance(hashFunctionName);
        } catch (NoSuchAlgorithmException e) {

            log.warn("Error instantiating {} hash function, trying with MD5", hashFunctionName);
            try {
                hashAlgorithm = MessageDigest.getInstance("SHA3-256");
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException("Unable to instantiate MD5 digest", ex);
            }
        }
        return hashAlgorithm;
    }
}
