package com.jim_jam.key_generation_service.repositories;

import com.jim_jam.key_generation_service.data.UnusedKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer to interact with unused_keys collection
 */
@Repository
public interface UnusedKeyRepository extends MongoRepository<UnusedKey, String> {
}
