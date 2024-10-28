package com.jim_jam.key_generation_service.common.error;

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
public class ErrorDetail {
    private String title;
    private String titleKey;
    private String detail;
}
