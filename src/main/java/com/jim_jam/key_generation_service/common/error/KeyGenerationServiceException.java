package com.jim_jam.key_generation_service.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class for the service
 */
@Getter
public class KeyGenerationServiceException extends Exception{
    ErrorDetail errorDetail;
    HttpStatus httpStatus;

    /**
     * Primary constructor
     * @param errorDetail detail of an error
     * @param httpStatus {@link HttpStatus}
     */
    public KeyGenerationServiceException(ErrorDetail errorDetail, HttpStatus httpStatus) {
        super(errorDetail.getTitleKey());
        this.errorDetail = errorDetail;
        this.httpStatus = httpStatus;
    }
}
