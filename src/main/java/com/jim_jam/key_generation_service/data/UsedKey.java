package com.jim_jam.key_generation_service.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


/**
 * Collection "USED_KEYS"
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "used_keys")
public class UsedKey extends Key {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof UsedKey thatKey)) {
            return false;
        }
        return Objects.equals(getKey(), thatKey.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    @Override
    public String toString() {
        return "UsedKey{" +
                "key=" + getKey()  +
                '}';
    }
}
