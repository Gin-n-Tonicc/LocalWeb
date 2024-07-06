package com.localWeb.localWeb.exceptions.country;

import com.localWeb.localWeb.exceptions.common.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when a country with the same name already exists.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class CountryCreateException extends BadRequestException {
    public CountryCreateException(MessageSource messageSource, boolean isUnique) {
        super(
                isUnique
                        ? messageSource.getMessage("country.create.exists", null, LocaleContextHolder.getLocale())
                        : messageSource.getMessage("country.create.invalid", null, LocaleContextHolder.getLocale())
        );
    }
}
