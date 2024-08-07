package com.localWeb.localWeb.models.dto.auth;

import com.localWeb.localWeb.enums.Provider;
import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.models.dto.request.CompleteOAuthRequest;
import com.localWeb.localWeb.models.dto.request.PhoneRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends CompleteOAuthRequest {
    private String email;
    private String password;
    private Provider provider = Provider.LOCAL;
    private Role role = Role.USER;
    private UUID avatarId;
    private PhoneRequest phone;
}
