package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.repositories.UsedKeyRepository;
import org.springframework.stereotype.Service;
import com.jim_jam.key_generation_service.data.UsedKey;

import java.util.List;
import java.util.Optional;

/**
 * Service layer to interact with used_key collection repository
 */
@Service
public class UsedKeyService {
    private final UsedKeyRepository usedKeyRepository;

    /**
     * Primary constructor
     * @param usedKeyRepository repository bean to interact with used_key repository
     */
    public UsedKeyService(
            UsedKeyRepository usedKeyRepository
    ) {
        this.usedKeyRepository = usedKeyRepository;
    }

    /**
     * Method to save a list of keys
     * @param usedKeys key to be saved
     * @return {@link List} saved keys
     */
    public List<UsedKey> saveAll(List<UsedKey> usedKeys) {
        return usedKeyRepository.saveAll(usedKeys);
    }

    /**
     * Method to find a key with an id
     * @param id of the key
     * @return {@link UnusedKey}
     */
    public UsedKey findById(String id) {
        Optional<UsedKey> usedKey = usedKeyRepository.findById(id);
        return usedKey.orElse(null);
    }
}
