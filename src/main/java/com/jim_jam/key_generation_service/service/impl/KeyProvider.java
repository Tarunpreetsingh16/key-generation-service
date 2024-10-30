package com.jim_jam.key_generation_service.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.jim_jam.key_generation_service.helper.KeyGenerationServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service layer to provide keys
 */
@Slf4j
@Service
public class KeyProvider {
    private final static String KEY = "KEYS";

    @Value("${key.cache.max.size:50}")
    private int keyCacheMaxSize;

    @Value("${key.generation.threshold:70}")
    private int keyGenerationThreshold;

    @Value("${key.length:6}")
    private int keyLength;

    private final Cache<String, Object> keyCache;

    /**
     * Primary constructor to set up bean
     * @param keyCache cache for keys
     */
    public KeyProvider(
            Cache<String, Object> keyCache
    ) {
        this.keyCache = keyCache;
    }

    /**
     * Method to get the key
     * First check cache, if not found get one from database and also generate more
     * @return {@link String} key
     */
    public String getKey() {
        List<String> keys = (List<String>) keyCache.getIfPresent(KEY);
        String key;

        //If there is nothing in cache for key or if cache is empty, get from database
        if (keys == null || keys.isEmpty()) {
            // TODO: get key from database
            key = "keyFromDatabase";
        }
        else {
            key = keys.remove(0);
            keyCache.put(KEY, keys);
        }

        // we need to check if the number of keys are below the threshold
        int thresholdSize = (int) (Math.floor(((double)keyGenerationThreshold/100) * keyCacheMaxSize));

        // if keys are below threshold, we need to generate more
        if (keys == null || keys.size() < thresholdSize) {
            generateNewKeys();
        }

        return key;
    }

    /**
     * Method to generate more keys
     * @return {@link List} of new keys
     */
    private List<String> generateNewKeys() {
        List<String> keys = (List<String>) keyCache.getIfPresent(KEY);
        int diff = keyCacheMaxSize;
        if (keys != null && !keys.isEmpty()) {
            diff = keyCacheMaxSize - keys.size();
        }

        List<String> newKeys = new ArrayList<>();

        while (diff > 0) {
            UUID uuid = UUID.randomUUID();
            String cleanUuid = uuid.toString().replaceAll("-", "");
            String generatedKey = getRandomSubstring(cleanUuid);
            newKeys.add(generatedKey);
            diff--;
        }
        return newKeys;
    }

    /**
     * Method to get random substring of length {@code KeyGenerationServiceHelper.keyLength}
     * @param str string from which we want random substring
     * @return {@link String} random substring
     */
    private String getRandomSubstring(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < keyLength) {
            return str;
        }

        int startIndex = (int) (Math.random() * (str.length() - keyLength));
        int endIndex = startIndex + keyLength;
        return str.substring(startIndex, endIndex);
    }
}
