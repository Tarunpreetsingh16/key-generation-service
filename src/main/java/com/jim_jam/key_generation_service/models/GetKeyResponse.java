package com.jim_jam.key_generation_service.models;

import lombok.Data;
import lombok.Builder;

/**
 * Model to store data to be returned when a new key needs to be generated
 */
@Builder
@Data
public class GetKeyResponse {
    private String key;
}
