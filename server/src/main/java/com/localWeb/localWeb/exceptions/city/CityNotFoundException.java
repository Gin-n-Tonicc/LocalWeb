package com.localWeb.localWeb.exceptions.city;


import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when a city is not found.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class CityNotFoundException extends NoSuchElementException {
    public CityNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("city.not.found", null, LocaleContextHolder.getLocale()));
    }
}
