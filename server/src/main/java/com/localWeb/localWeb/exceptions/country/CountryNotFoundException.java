package com.localWeb.localWeb.exceptions.country;

import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when the country is not found.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class CountryNotFoundException extends NoSuchElementException {
    public CountryNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("country.not.found", null, LocaleContextHolder.getLocale()));
    }
}