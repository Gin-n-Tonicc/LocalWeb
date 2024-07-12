package com.localWeb.localWeb.exceptions.organisation;

import com.localWeb.localWeb.exceptions.common.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserNotMemberOfTheOrganisation extends BadRequestException {
    public UserNotMemberOfTheOrganisation(MessageSource messageSource) {
        super(messageSource.getMessage("user.not.member.organisation", null, LocaleContextHolder.getLocale()));
    }
}