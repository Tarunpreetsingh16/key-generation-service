package com.jim_jam.key_generation_service.controller;

import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;
import com.jim_jam.key_generation_service.models.GetKeyResponse;
import com.jim_jam.key_generation_service.service.IKeyGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyGenerationServiceController {

    private final IKeyGenerationService keyGenerationService;

    public KeyGenerationServiceController(
            IKeyGenerationService keyGenerationService
    ) {
        this.keyGenerationService = keyGenerationService;
    }

    @GetMapping("/")
    public ResponseEntity<GetKeyResponse> getKey() throws KeyGenerationServiceException {
        String key = keyGenerationService.generateRandomKey();
        return ResponseEntity.ok().body(
                GetKeyResponse.builder()
                        .key(key)
                        .build()
        );
    }
}
