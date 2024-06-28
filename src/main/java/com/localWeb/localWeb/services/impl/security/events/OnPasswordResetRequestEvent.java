package com.localWeb.localWeb.services.impl.security.events;

import com.localWeb.localWeb.models.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * OnPasswordResetRequestEvent represents an event raised when a password reset request is initiated.
 * It carries information about the user requesting the password reset and the base URL of the application.
 */
@Getter
public class OnPasswordResetRequestEvent extends ApplicationEvent {

    private final User user;
    private final String appBaseUrl;

    public OnPasswordResetRequestEvent(User user, String appBaseUrl) {
        super(user);
        this.user = user;
        this.appBaseUrl = appBaseUrl;
    }
}