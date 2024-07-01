package com.localWeb.localWeb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.localWeb.localWeb.enums.Permission.*;

/**
 * Enumeration representing different roles in the application.
 * Each role has a set of associated permissions.
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    TRAINER_READ,
                    TRAINER_UPDATE,
                    TRAINER_DELETE,
                    TRAINER_CREATE,
                    ORGANISATION_READ,
                    ORGANISATION_UPDATE,
                    ORGANISATION_CREATE,
                    ORGANISATION_DELETE,
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
            )
    ),
    TRAINER(
            Set.of(
                    TRAINER_READ,
                    TRAINER_UPDATE,
                    TRAINER_DELETE,
                    TRAINER_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
            )
    ),
    ORGANISATION(
            Set.of(
                    TRAINER_READ,
                    TRAINER_UPDATE,
                    TRAINER_DELETE,
                    TRAINER_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE,
                    ORGANISATION_READ,
                    ORGANISATION_CREATE,
                    ORGANISATION_UPDATE,
                    ORGANISATION_DELETE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
