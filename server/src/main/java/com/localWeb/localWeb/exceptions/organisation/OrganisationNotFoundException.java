package com.localWeb.localWeb.exceptions.organisation;

import com.localWeb.localWeb.exceptions.common.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class OrganisationNotFoundException extends NoSuchElementException {
    public OrganisationNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("organisation.not.found", null, LocaleContextHolder.getLocale()));
    }
}
