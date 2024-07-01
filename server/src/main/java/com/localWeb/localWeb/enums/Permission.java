package com.localWeb.localWeb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing permissions available in the application.
 * Each permission has a corresponding string representation.
 * This class is used to define and access different permissions required for authorization purposes.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    ORGANISATION_READ("organisation:read"),
    ORGANISATION_UPDATE("organisation:update"),
    ORGANISATION_CREATE("organisation:create"),
    ORGANISATION_DELETE("organisation:delete"),
    TRAINER_READ("trainer:read"),
    TRAINER_UPDATE("trainer:update"),
    TRAINER_CREATE("trainer:create"),
    TRAINER_DELETE("trainer:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete");

    private final String permission;
}

