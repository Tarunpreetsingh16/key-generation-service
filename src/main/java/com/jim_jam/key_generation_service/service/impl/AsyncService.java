package com.jim_jam.key_generation_service.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.jim_jam.key_generation_service.data.Key;
import com.jim_jam.key_generation_service.data.UnusedKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to contain async methods
 */
@Slf4j
@Service
public class AsyncService {

    private final static String KEY = "KEYS";

    private final int keyCacheMaxSize;
    private final Cache<String, Object> keyCache;
    private final UnusedKeyService unusedKeyService;
    private final KeyService keyService;

    /**
     * Primary constructor to set up bean
     * @param keyCache cache for keys
     */
    public AsyncService(
            Cache<String, Object> keyCache,
            @Value("${key.cache.max.size:50}") int keyCacheMaxSize,
            UnusedKeyService unusedKeyService,
            KeyService keyService
    ) {
        this.keyCache = keyCache;
        this.keyCacheMaxSize = keyCacheMaxSize;
        this.unusedKeyService = unusedKeyService;
        this.keyService = keyService;
    }

    /**
     * Method to load new keys into cache
     */
    @Async
    public void loadKeysInCache() {
        List<Key> existingKeysInCache = (List<Key>) keyCache.getIfPresent(KEY);

        int diff = keyCacheMaxSize;
        if (existingKeysInCache != null && !existingKeysInCache.isEmpty()) {
            diff = keyCacheMaxSize - existingKeysInCache.size();
        }
        List<UnusedKey> unusedKeys = unusedKeyService.getKeys(diff);

        List<Key> newKeys = new ArrayList<>(unusedKeys);

        if (existingKeysInCache != null) {
            newKeys.addAll(existingKeysInCache);
        }

        keyCache.put(KEY, newKeys);
        log.info("Successfully loaded cache to its max capacity. {} new keys loaded.", newKeys.size());

        keyService.moveKeysFromUnusedToUsed(unusedKeys);
    }

}
