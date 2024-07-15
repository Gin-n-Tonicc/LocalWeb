package com.localWeb.localWeb.exceptions.group;

import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when the lesson is not found.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class GroupNotFoundException extends NoSuchElementException {
    public GroupNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("group.not.found", null, LocaleContextHolder.getLocale()));
    }
}