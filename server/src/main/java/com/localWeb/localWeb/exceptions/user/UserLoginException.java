package com.localWeb.localWeb.exceptions.user;

import com.localWeb.localWeb.exceptions.common.BadRequestException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exception thrown when there is an issue with user login, such as invalid email or password.
 */
public class UserLoginException extends BadRequestException {
    public UserLoginException(MessageSource messageSource) {
        super(messageSource.getMessage("user.login.exception", null, LocaleContextHolder.getLocale()));
    }
}
