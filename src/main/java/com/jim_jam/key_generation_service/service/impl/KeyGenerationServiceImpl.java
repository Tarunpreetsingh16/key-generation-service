package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.common.error.ErrorDetail;
import com.jim_jam.key_generation_service.common.error.ErrorType;
import com.jim_jam.key_generation_service.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;
import com.jim_jam.key_generation_service.service.IKeyGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer
 */
@Slf4j
@Service
public class KeyGenerationServiceImpl implements IKeyGenerationService {

    private final KeyProvider keyProvider;

    public KeyGenerationServiceImpl(
            KeyProvider keyProvider
    ) {
        this.keyProvider = keyProvider;
    }

    @Override
    public String getRandomKey() throws KeyGenerationServiceException {
        String key = keyProvider.getKey();
        if (key == null) {
            ErrorDetail errorDetail = ErrorType.KEY_GENERATION_FAILURE.getErrorDetail();
            throw new KeyGenerationServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey())
            );
        }
        return key;
    }
}
