package com.jim_jam.key_generation_service.service;

import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;

public interface IKeyGenerationService {

    /**
     * Method to generate a key
     * @return {@link String} random key
     * @throws KeyGenerationServiceException when something goes wrong while generating key
     */
    String generateRandomKey() throws KeyGenerationServiceException;
}
