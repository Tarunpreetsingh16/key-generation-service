package com.jim_jam.key_generation_service.schedulers;

import com.jim_jam.key_generation_service.data.Key;
import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.data.UsedKey;
import com.jim_jam.key_generation_service.service.impl.KeyProvider;
import com.jim_jam.key_generation_service.service.impl.UnusedKeyService;
import com.jim_jam.key_generation_service.service.impl.UsedKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Scheduler to load new keys in the database
 */
@Slf4j
@Component
public class KeyGenerationScheduler {

    private final long keyDatabaseEstimatedSize;
    private final int keyLength;
    private final int keyDatabaseLoadThreshold;
    private final UnusedKeyService unusedKeyService;
    private final UsedKeyService usedKeyService;

    /**
     * Primary constructor
     * @param keyDatabaseEstimatedSize estimated max size of keys in database
     * @param keyLength length of the key
     * @param keyDatabaseLoadThreshold threshold below which system needs to create new keys
     * @param unusedKeyService bean to interact with unusedKey collection
     * @param usedKeyService bean to interact with usedKey collection
     */
    public KeyGenerationScheduler(
            @Value("${estimated.keys.size:200}") long keyDatabaseEstimatedSize,
            @Value("${key.length:6}") int keyLength,
            @Value("${key.database.load.threshold:75}") int keyDatabaseLoadThreshold,
            UnusedKeyService unusedKeyService,
            UsedKeyService usedKeyService
    ) {
        this.keyDatabaseEstimatedSize = keyDatabaseEstimatedSize;
        this.keyLength = keyLength;
        this.keyDatabaseLoadThreshold = keyDatabaseLoadThreshold;
        this.unusedKeyService = unusedKeyService;
        this.usedKeyService = usedKeyService;
    }

    /**
     * Scheduler to create new keys
     */
    @Scheduled(fixedDelayString = "${key.generation.delay:10000}")
    public void generateKeys() {
        // we need to check if the number of keys are below the threshold
        int thresholdSize = (int) (Math.floor(((double) keyDatabaseLoadThreshold / 100) * keyDatabaseEstimatedSize));

        if (unusedKeyService.count() < thresholdSize) {
            log.info("Keys in database lower than threshold. Loading new keys.");
            loadData();
        }
    }

    /**
     * Method to load data into the database on the first launch when database is empty
     */
    private void loadData() {
        Set<UnusedKey> unusedKeys = new HashSet<>();
        long diff = keyDatabaseEstimatedSize - unusedKeyService.count();

        int i = 0;
        while (i < diff) {
            Key key = KeyProvider.generateKey(keyLength);
            UnusedKey unusedKey = UnusedKey.builder()
                    .key(key.getKey())
                    .build();

            if (!unusedKeys.contains(unusedKey)
                    && usedKeyService.findById(key.getKey()) == null
                    && unusedKeyService.findById(key.getKey()) == null) {
                unusedKeys.add(unusedKey);
                i++;
            }
        }

        List<UnusedKey> savedUnusedKeys = unusedKeyService.saveAll(unusedKeys);
        log.info("Successfully loaded keys into database: number of records = {}", savedUnusedKeys.size());
    }
}
