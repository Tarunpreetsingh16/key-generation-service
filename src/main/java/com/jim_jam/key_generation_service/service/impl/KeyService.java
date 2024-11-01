package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.data.UsedKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer to handle generic tasks related to keys
 */
@Service
@Slf4j
public class KeyService {
    private final UnusedKeyService unusedKeyService;
    private final UsedKeyService usedKeyService;

    /**
     * Primary constructor
     * @param unusedKeyService service bean to interact with unused_keys collection
     * @param usedKeyService service bean to interact with used_keys collection
     */
    public KeyService(
            UnusedKeyService unusedKeyService,
            UsedKeyService usedKeyService
    ) {
        this.unusedKeyService = unusedKeyService;
        this.usedKeyService = usedKeyService;
    }

    /**
     * Method to move from unused_keys to used_keys collection
     * @param unusedKeys keys that are not in use
     */
    public void moveKeysFromUnusedToUsed(List<UnusedKey> unusedKeys) {
        unusedKeyService.deleteAll(unusedKeys);

        List<UsedKey> usedKeys = new ArrayList<>();
        unusedKeys.forEach(unusedKey ->
                usedKeys.add(
                        UsedKey.builder()
                                .key(unusedKey.getKey())
                                .build()));

        usedKeyService.saveAll(usedKeys);
        log.info("Successfully moved {} key(s) from unused to used", unusedKeys.size());
    }
}
