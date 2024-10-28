package com.jim_jam.key_generation_service.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Builder;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Class to store data that will be returned when an error occurs
 */
@Data
@Builder
public class ErrorResponse {
    private String title;
    private String titleKey;
    private List<ErrorDetail> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date timestamp;

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder()
                .errors(List.of())
                .timestamp(Date.from(Instant.now()));
    }
}
