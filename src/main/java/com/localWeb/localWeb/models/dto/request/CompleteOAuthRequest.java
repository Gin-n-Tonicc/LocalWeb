package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOAuthRequest {
    private String firstname;
    private String lastname;
    private String address;
    private String education;
    private String currentWorkPlace;
    private String workExperience;
    private String whatCanHelpWith;
    private Role role;
}