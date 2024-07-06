package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.auth.AdminUserDTO;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.auth.RegisterRequest;
import com.localWeb.localWeb.models.dto.request.CompleteOAuthRequest;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.models.entity.VerificationToken;
import com.localWeb.localWeb.security.CustomOAuth2User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(RegisterRequest request);

    User findByEmail(String email);

    List<AdminUserDTO> getAllUsers();

    AdminUserDTO updateUser(UUID id, AdminUserDTO userDTO, PublicUserDTO currentUser);

    void deleteUserById(UUID id, PublicUserDTO currentUser);

    User processOAuthUser(CustomOAuth2User oAuth2User) throws Exception;

    User updateOAuth2UserWithFullData(CompleteOAuthRequest request, UUID userId);

    User findById(UUID id);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void setAddressesForUser(CompleteOAuthRequest request, User user);

    void setPhoneNumberForUser(CompleteOAuthRequest request, User user);
}
