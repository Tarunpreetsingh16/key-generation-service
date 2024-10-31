package com.jim_jam.key_generation_service.data;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Key {
    @Id
    protected String key;
}
