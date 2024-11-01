package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.repositories.UnusedKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer to interact with persistence layer of unused_keys collection
 */
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
        Optional<UnusedKey> unusedKey = unusedKeyRepository.findAll().stream().limit(1).findFirst();
        return unusedKey.orElse(null);
    }

    /**
     * Method to delete a key
     * @param unusedKey key to be deleted
     */
    public void delete(UnusedKey unusedKey) {
        unusedKeyRepository.delete(unusedKey);
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
}
