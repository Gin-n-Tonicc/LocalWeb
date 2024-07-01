package com.localWeb.localWeb.services.impl.security.events;

import com.localWeb.localWeb.models.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * OnRegistrationCompleteEvent represents an event raised when a user completes registration.
 * It carries information about the registered user, the application URL, and the locale.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    @Setter
    @Getter
    private String appUrl;
    private Locale locale;
    @Setter
    @Getter
    private User user;

    public OnRegistrationCompleteEvent(
            User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }
}
