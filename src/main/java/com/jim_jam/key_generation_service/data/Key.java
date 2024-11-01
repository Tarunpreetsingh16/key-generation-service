package com.jim_jam.key_generation_service.data;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Key {
    @Id
    protected String key;

    @CreatedDate
    protected Instant createdAt = Instant.now();
}
