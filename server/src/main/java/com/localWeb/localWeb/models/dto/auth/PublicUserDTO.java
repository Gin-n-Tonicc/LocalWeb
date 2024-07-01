package com.localWeb.localWeb.models.dto.auth;

import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.models.entity.File;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Role role;
    private File avatar;
    private boolean additionalInfoRequired;
}
