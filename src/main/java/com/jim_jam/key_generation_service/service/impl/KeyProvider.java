package com.jim_jam.key_generation_service.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.jim_jam.key_generation_service.common.error.ErrorDetail;
import com.jim_jam.key_generation_service.common.error.ErrorType;
import com.jim_jam.key_generation_service.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;
import com.jim_jam.key_generation_service.data.Key;
import com.jim_jam.key_generation_service.data.UnusedKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final int keyCacheMaxSize;
    private final int keyGenerationThreshold;
    private final Cache<String, Object> keyCache;
    private final UnusedKeyService unusedKeyService;
    private final KeyService keyService;

    /**
     * Primary constructor to set up bean
     * @param keyCache cache for keys
     */
    public KeyProvider(
            Cache<String, Object> keyCache,
            @Value("${key.cache.max.size:50}") int keyCacheMaxSize,
            @Value("${key.generation.threshold:70}") int keyGenerationThreshold,
            UnusedKeyService unusedKeyService,
            KeyService keyService
    ) {
        this.keyCache = keyCache;
        this.keyCacheMaxSize = keyCacheMaxSize;
        this.keyGenerationThreshold = keyGenerationThreshold;
        this.unusedKeyService = unusedKeyService;
        this.keyService = keyService;
    }

    /**
     * Method to get the key
     * First check cache, if not found get one from database and also generate more
     * @return {@link String} key
     */
    public String getKey() throws KeyGenerationServiceException {
        List<Key> keys = (ArrayList<Key>) keyCache.getIfPresent(KEY);
        String key;

        //If there is nothing in cache for key or if cache is empty, get from database
        if (keys == null || keys.isEmpty()) {
            Key keyFromDatabase = getNewKeyFromDatabase();
            key = keyFromDatabase.getKey();
            log.info("Returned key from database with id={}", key);
        }
        else {
            key = keys.remove(0).getKey();
            keyCache.put(KEY, keys);
            log.info("Returned key from cache with id={}", key);
        }

        // we need to check if the number of keys are below the threshold
        int thresholdSize = (int) (Math.floor(((double)keyGenerationThreshold/100) * keyCacheMaxSize));

        // if keys are below threshold, we need to generate more
        if (keys == null || keys.size() < thresholdSize) {
            loadKeysInCache();
        }

        return key;
    }

    /**
     * Method to load new keys into cache
     */
    private void loadKeysInCache() {
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


    /**
     * Method to get random substring of length {@code KeyGenerationServiceHelper.keyLength}
     * @param str string from which we want random substring
     * @return {@link String} random substring
     */
    private static String getRandomSubstring(String str, int keyLength) {
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

    public static String generateKey(int keyLength) {
        UUID uuid = UUID.randomUUID();
        String cleanUuid = uuid.toString().replaceAll("-", "");
        return getRandomSubstring(cleanUuid, keyLength);
    }

    /**
     * Method to get new key from database
     * @return {@link Key} from database
     * @throws KeyGenerationServiceException when something goes wrong while getting a new key
     */
    @Transactional
    private Key getNewKeyFromDatabase() throws KeyGenerationServiceException {
        UnusedKey unusedKey = unusedKeyService.findOldest();
        if (unusedKey == null) {
            ErrorDetail errorDetail = ErrorType.FETCH_OLDEST_KEY_FAILURE.getErrorDetail();
            throw new KeyGenerationServiceException(
                    errorDetail, ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }
        keyService.moveKeysFromUnusedToUsed(List.of(unusedKey));
        return unusedKey;
    }
}
