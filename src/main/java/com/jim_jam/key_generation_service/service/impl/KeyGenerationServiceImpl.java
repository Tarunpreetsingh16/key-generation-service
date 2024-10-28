package com.jim_jam.key_generation_service.service.impl;

import com.jim_jam.key_generation_service.common.error.ErrorDetail;
import com.jim_jam.key_generation_service.common.error.ErrorType;
import com.jim_jam.key_generation_service.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.key_generation_service.common.error.KeyGenerationServiceException;
import com.jim_jam.key_generation_service.helper.KeyGenerationServiceHelper;
import com.jim_jam.key_generation_service.service.IKeyGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service layer
 */
@Slf4j
@Service
public class KeyGenerationServiceImpl implements IKeyGenerationService {

    @Value("${key.length:6}")
    private int keyLength;

    KeyGenerationServiceHelper keyGenerationServiceHelper = new KeyGenerationServiceHelper();

    @Override
    public String generateRandomKey() throws KeyGenerationServiceException {
        UUID uuid = UUID.randomUUID();
        String cleanUuid = uuid.toString().replaceAll("-", "");
        String key = keyGenerationServiceHelper.getRandomSubstring(cleanUuid, keyLength);
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
