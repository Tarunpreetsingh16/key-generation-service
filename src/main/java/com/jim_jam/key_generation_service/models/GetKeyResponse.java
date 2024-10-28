package com.jim_jam.key_generation_service.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

/**
 * Model to store data to be returned when a new key needs to be generated
 */
@Builder
@Data
@Schema(name = "GetKeyResponse", description = "Model to store data to be returned when a new key needs to be generated")
public class GetKeyResponse {
    @Schema(name = "key", description = "Random key generated", example = "ae34fc")
    private String key;
}
