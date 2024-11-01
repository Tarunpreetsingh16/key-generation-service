package com.jim_jam.key_generation_service.repositories;

import com.jim_jam.key_generation_service.data.UsedKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer to interact with used_keys collection
 */
@Repository
public interface UsedKeyRepository extends MongoRepository<UsedKey, String>  {
}
