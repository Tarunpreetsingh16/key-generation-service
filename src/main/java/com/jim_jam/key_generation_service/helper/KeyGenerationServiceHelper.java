package com.jim_jam.key_generation_service.helper;

import lombok.extern.slf4j.Slf4j;

/**
 * Helper class for Key generation service
 */
@Slf4j
public class KeyGenerationServiceHelper {

    private int keyLength;

    /**
     * Method to get random substring of length {@code KeyGenerationServiceHelper.keyLength}
     * @param str string from which we want random substring
     * @param length length of the substring
     * @return {@link String} random substring
     */
    public String getRandomSubstring(String str, int length) {
        if (str == null) {
            return null;
        }
        if (str.length() < keyLength) {
            return str;
        }

        int startIndex = (int) (Math.random() * (str.length() - length));
        int endIndex = startIndex + length;
        String substring = str.substring(startIndex, endIndex);
        log.info("Successfully generated key.");
        return substring;
    }
}
