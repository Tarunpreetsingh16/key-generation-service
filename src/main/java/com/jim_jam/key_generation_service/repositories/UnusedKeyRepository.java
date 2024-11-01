package com.jim_jam.key_generation_service.repositories;

import com.jim_jam.key_generation_service.data.UnusedKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository layer to interact with unused_keys collection
 */
@Repository
public interface UnusedKeyRepository extends MongoRepository<UnusedKey, String> {
    List<UnusedKey> findByOrderByCreatedAtDesc(Pageable pageable);
}
