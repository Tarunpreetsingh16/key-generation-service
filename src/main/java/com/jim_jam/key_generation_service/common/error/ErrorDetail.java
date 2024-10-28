package com.jim_jam.key_generation_service.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class to store details of an error
 */
@Builder
@Getter
@Setter
@ToString
@Schema(name = "ErrorDetail", description = "Class to store details of an error")
public class ErrorDetail {
    @Schema(name = "title", description = "Title of an error", example = "Something went wrong.")
    private String titlee;

    @Schema(name = "titleKey", description = "Key to identify an error", example = "something.went.wrong")
    private String titleKey;

    @Schema(name = "detail", description = "Explanation of an error", example = "Something happened when running the API.")
    private String detail;
}
