package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.repositories.UsedKeyRepository;
import org.springframework.stereotype.Service;

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
}
