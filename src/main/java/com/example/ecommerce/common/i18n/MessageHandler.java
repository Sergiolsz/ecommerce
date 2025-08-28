package com.example.ecommerce.common.i18n;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageHandler {

    private final MessageSource messageSource;

    /**
     * Get message by code and arguments using the current locale
     *
     * @param code the message code
     * @param args the message arguments
     * @return the localized message
     */
    public String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    /**
     * Get message by code and arguments using the specified locale
     *
     * @param code   the message code
     * @param locale the locale to use
     * @param args   the message arguments
     * @return the localized message
     */
    public String getMessage(String code, Locale locale, Object... args) {
        Locale effectiveLocale = (locale != null) ? locale : LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, effectiveLocale);
    }
}