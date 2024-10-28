package com.jim_jam.key_generation_service.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "ErrorResponse", description = "Class to store data that will be returned when an error occurs")
public class ErrorResponse {
    @Schema(name = "title", description = "Title of an error", example = "Something went wrong.")
    private String title;

    @Schema(name = "titleKey", description = "Key to identify an error", example = "something.went.wrong")
    private String titleKey;

    @Schema(name = "errors", description = "List of errors that caused a specific error", example = "[]")
    private List<ErrorDetail> errors;

    @Schema(name = "timestamp", description = "Instant when error occurred", example = "2024-10-27T10:15:30.123+0000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    private Date timestamp;

    /**
     * Method to extend builder method
     * @return {@link ErrorResponseBuilder}
     */
    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder()
                .errors(List.of())
                .timestamp(Date.from(Instant.now()));
    }
}
