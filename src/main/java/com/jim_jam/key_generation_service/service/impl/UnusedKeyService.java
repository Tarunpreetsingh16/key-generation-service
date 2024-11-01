package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.data.Key;
import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.repositories.UnusedKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer to interact with persistence layer of unused_keys collection
 */
@Slf4j
@Service
public class UnusedKeyService {
    private final UnusedKeyRepository unusedKeyRepository;

    /**
     * Primary constructor
     * @param unusedKeyRepository repository bean to interact with unused_keys collection
     */
    public UnusedKeyService(
            UnusedKeyRepository unusedKeyRepository
    ) {
        this.unusedKeyRepository = unusedKeyRepository;
    }

    /**
     * Method to get the oldest key
     * @return {@link UnusedKey}
     */
    public UnusedKey findOldest() {
        List<UnusedKey> keys = getKeys(1);
        if (keys.isEmpty() || keys.get(0) == null) {
            return null;
        }
        UnusedKey key = keys.get(0);
        log.info("Successfully fetched oldest key with id={}", key.getKey());
        return key;
    }

    /**
     * Method to delete keys
     * @param unusedKeys keys to be deleted
     */
    public void deleteAll(List<UnusedKey> unusedKeys) {
        unusedKeyRepository.deleteAll(unusedKeys);
    }

    /**
     * Method to get size of the collection
     * @return {@link Long}
     */
    public long count() {
        return unusedKeyRepository.count();
    }

    /**
     * Method to save multiple keys
     * @param unusedKeys keys to be saved
     * @return {@link List} of keys saved
     */
    public List<UnusedKey> saveAll(Iterable<UnusedKey> unusedKeys) {
        return unusedKeyRepository.saveAll(unusedKeys);
    }

    /**
     * Method to get keys
     * @param count number of keys to fetch
     * @return {@link List} of unused keys
     */
    public List<UnusedKey> getKeys(int count) {
        Pageable pageable = PageRequest.of(0, count);
        List<UnusedKey> keys = unusedKeyRepository.findByOrderByCreatedAtDesc(pageable);
        log.info("Successfully fetched {} key(s) from database", keys.size());
        return keys;
    }
}
