package com.jim_jam.key_generation_service.config;

import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.service.impl.KeyProvider;
import com.jim_jam.key_generation_service.service.impl.UnusedKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration
public class DataLoader {

    private final int keyLength;
    private final int initialKeysCount;
    private final UnusedKeyService unusedKeyService;

    /**
     * Primary constructor
     * @param keyLength length of the keys that we want to use as identifier
     * @param initialKeysCount number of keys we want to load in database
     * @param unusedKeyService service layer for unusedKeyRepository
     */
    public DataLoader(
            @Value("${key.length:6}") int keyLength,
            @Value("${initial.keys.count:100}") int initialKeysCount,
            UnusedKeyService unusedKeyService
    ) {
        this.keyLength = keyLength;
        this.initialKeysCount = initialKeysCount;
        this.unusedKeyService = unusedKeyService;
    }

    /**
     * Bean to run on application load
     * @return {@link ApplicationRunner}
     */
    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if(unusedKeyService.count() == 0) {
                loadData();
            }
        };
    }

    /**
     * Method to load data into the database on the first launch when database is empty
     */
    private void loadData() {
        Set<UnusedKey> unusedKeys = new HashSet<>();

        int i = 0;
        while (i < initialKeysCount) {
            UnusedKey unusedKey = UnusedKey.builder()
                    .key(KeyProvider.generateKey(keyLength))
                    .build();

            if (!unusedKeys.contains(unusedKey)) {
                unusedKeys.add(unusedKey);
                i++;
            }
        }

        List<UnusedKey> savedUnusedKeys = unusedKeyService.saveAll(unusedKeys);
        log.info("Successfully loaded data into database: number of records = {}", savedUnusedKeys.size());
    }
}
