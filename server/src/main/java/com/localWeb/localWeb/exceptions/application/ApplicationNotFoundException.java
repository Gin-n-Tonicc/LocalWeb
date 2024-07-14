package com.localWeb.localWeb.exceptions.application;

import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when the category is not found.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class ApplicationNotFoundException extends NoSuchElementException {
    public ApplicationNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("application.not.found", null, LocaleContextHolder.getLocale()));
    }
}