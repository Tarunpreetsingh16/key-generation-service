package com.jim_jam.key_generation_service.common.error;

/**
 * Enum to store types of known errors that can occur in this service
 */
public enum ErrorType {
    KEY_GENERATION_SERVICE_ERROR(
            "Key Generation Service Error",
            "key.generation.service.error",
            "Something went wrong."
    ),
    KEY_GENERATION_FAILURE(
        "Key Generation Failure",
        "key.generation.failure",
        "Failed to generate a key."
    );

    private final String title;
    private final String titleKey;
    private final String detail;

    /**
     * Primary constructor
     * @param title title of the error
     * @param titleKey key to identify the error
     * @param detail error explanation
     */
    ErrorType(
            String title,
            String titleKey,
            String detail
    ) {
        this.title = title;
        this.titleKey = titleKey;
        this.detail = detail;
    }

    /**
     * Method to return an {@link ErrorDetail} linked to an error type
     * @return {@link ErrorDetail}
     */
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.builder()
                .title(title)
                .titleKey(titleKey)
                .detail(detail)
                .build();
    }
}
