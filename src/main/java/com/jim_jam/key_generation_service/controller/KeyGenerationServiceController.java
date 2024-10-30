package com.jim_jam.key_generation_service.controller;

import com.jim_jam.key_generation_service.common.error.ErrorResponse;
import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;
import com.jim_jam.key_generation_service.models.GetKeyResponse;
import com.jim_jam.key_generation_service.service.IKeyGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "GET", description = "GET methods of the APIs.")
    @Operation(summary = "Get a random key", description = "Method to get generate a random key. The response is a key.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetKeyResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something goes wrong.",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    })
    })
    @GetMapping("/")
    public ResponseEntity<GetKeyResponse> getKey() throws KeyGenerationServiceException {
        String key = keyGenerationService.getRandomKey();
        return ResponseEntity.ok().body(
                GetKeyResponse.builder()
                        .key(key)
                        .build()
        );
    }
}
