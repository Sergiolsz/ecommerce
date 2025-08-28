package com.example.ecommerce.domain.exception;

import com.example.ecommerce.common.i18n.MessageHandler;
import com.example.ecommerce.common.utils.MessageCodes;

public class EventException extends DomainException {

    /**
     * Constructs a new EventException with a localized error message.
     *
     * @param messageHandler the MessageHandler to retrieve the localized message
     */
    public EventException(MessageHandler messageHandler) {
        super(messageHandler.getMessage(MessageCodes.KAFKA_PRODUCT_ERROR));
    }

}
