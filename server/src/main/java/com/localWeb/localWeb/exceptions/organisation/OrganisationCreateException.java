package com.localWeb.localWeb.exceptions.organisation;


import com.localWeb.localWeb.exceptions.common.BadRequestException;
import jakarta.validation.ConstraintViolation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Exception thrown when there is an issue creating a organisation, either due to invalid data or duplicate organisation details.
 * Sets the appropriate message using MessageSource (the messages are in src/main/resources/messages).
 */
public class OrganisationCreateException extends BadRequestException {

    /**
     * Constructs a OrganisationCreateException with a message indicating either a duplicate email or invalid organisation data.
     */
    public OrganisationCreateException(MessageSource messageSource, boolean isUnique) {
        super(
                isUnique
                        ? messageSource.getMessage("organisation.email.exists", null, LocaleContextHolder.getLocale())
                        : messageSource.getMessage("organisation.invalid.data", null, LocaleContextHolder.getLocale())
        );
    }

    /**
     * Constructs a OrganisationCreateException with validation errors.
     */
    public OrganisationCreateException(Set<ConstraintViolation<?>> validationErrors) {
        super(
                validationErrors
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"))
        );
    }
}
