package com.localWeb.localWeb.exceptions.lesson;

import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when the lesson is not found.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class LessonNotFoundException extends NoSuchElementException {
    public LessonNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("lesson.not.found", null, LocaleContextHolder.getLocale()));
    }
}