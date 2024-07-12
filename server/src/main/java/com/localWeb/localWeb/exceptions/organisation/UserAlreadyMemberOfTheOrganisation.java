package com.localWeb.localWeb.exceptions.organisation;

import com.localWeb.localWeb.exceptions.common.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserAlreadyMemberOfTheOrganisation extends BadRequestException {
    public UserAlreadyMemberOfTheOrganisation(MessageSource messageSource) {
        super(messageSource.getMessage("user.already.member.organisation", null, LocaleContextHolder.getLocale()));
    }
}
