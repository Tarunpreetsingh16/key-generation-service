package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.repositories.UsedKeyRepository;
import org.springframework.stereotype.Service;
import com.jim_jam.key_generation_service.data.UsedKey;

import java.util.List;

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
}
