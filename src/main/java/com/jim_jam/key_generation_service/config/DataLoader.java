package com.jim_jam.key_generation_service.config;

import com.jim_jam.key_generation_service.common.Constants;
import com.jim_jam.key_generation_service.data.UnusedKey;
import com.jim_jam.key_generation_service.service.impl.KeyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class DataLoader {

    private final int keyLength;
    private final int keyCacheMaxSize;
    private final MongoTemplate mongoTemplate;

    public DataLoader(
            @Value("${key.length:6}") int keyLength,
            @Value("${key.cache.max.size:50}") int keyCacheMaxSize,
            MongoTemplate mongoTemplate
    ) {
        this.keyLength = keyLength;
        this.keyCacheMaxSize = keyCacheMaxSize;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            createCollections();
            if(mongoTemplate.count(new Query(), Constants.UNUSED_KEYS_COLLECTION.getValue()) == 0) {
                loadData();
            }
        };
    }

    private void createCollections() {
        mongoTemplate.createCollection(Constants.UNUSED_KEYS_COLLECTION.getValue());
        mongoTemplate.createCollection(Constants.USED_KEYS_COLLECTION_NAME.getValue());
    }

    private void loadData() {
        Set<UnusedKey> unusedKeys = new HashSet<>();

        int i = 0;
        while (i < keyCacheMaxSize) {
            UnusedKey unusedKey = UnusedKey.builder()
                    .key(KeyProvider.generateKey(keyLength))
                    .build();

            if (mongoTemplate.findById(unusedKey, UnusedKey.class) == null && !unusedKeys.contains(unusedKey)) {
                unusedKeys.add(unusedKey);
                i++;
            }
        }

        mongoTemplate.insertAll(unusedKeys);
        log. info("Successfully loaded data into database: number of records = {}",
                mongoTemplate.count(new Query(), Constants.UNUSED_KEYS_COLLECTION.getValue()));
    }
}
