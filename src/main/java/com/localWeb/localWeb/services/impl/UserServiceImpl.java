package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.enums.Provider;
import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.exceptions.common.AccessDeniedException;
import com.localWeb.localWeb.exceptions.user.UserCreateException;
import com.localWeb.localWeb.exceptions.user.UserNotFoundException;
import com.localWeb.localWeb.models.dto.auth.AdminUserDTO;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.auth.RegisterRequest;
import com.localWeb.localWeb.models.dto.request.CompleteOAuthRequest;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.models.entity.VerificationToken;
import com.localWeb.localWeb.repositories.UserRepository;
import com.localWeb.localWeb.repositories.VerificationTokenRepository;
import com.localWeb.localWeb.security.CustomOAuth2User;
import com.localWeb.localWeb.services.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;
    private final VerificationTokenRepository verificationTokenRepository;

    /**
     * Creates a new user based on the provided registration request.
     *
     * @param request The registration request containing user details.
     * @return The created user.
     * @throws UserCreateException             If there is an issue creating the user.
     * @throws DataIntegrityViolationException If there is a data integrity violation while creating the user.
     * @throws ConstraintViolationException    If there is a constraint violation while creating the user.
     */
    @Override
    public User createUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserCreateException(messageSource, true);
        }

        try {
            User user = buildUser(request);
            user.setRole(Role.USER);
            user.setCreatedAt(LocalDateTime.now());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserCreateException(messageSource, true);
        } catch (ConstraintViolationException exception) {
            throw new UserCreateException(exception.getConstraintViolations());
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", messageSource));
    }

    @Override
    public List<AdminUserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(x -> modelMapper.map(x, AdminUserDTO.class))
                .toList();
    }

    @Override
    public AdminUserDTO updateUser(UUID id, AdminUserDTO userDTO, PublicUserDTO currentUser) {
        User userToUpdate = findById(id);

        if (userToUpdate.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(messageSource);
        }

        modelMapper.map(userDTO, userToUpdate);
        userToUpdate.setId(id);

        User updatedUser = userRepository.save(userToUpdate);
        return modelMapper.map(updatedUser, AdminUserDTO.class);
    }


    @Override
    public void deleteUserById(UUID id, PublicUserDTO currentUser) {
        User user = findById(id);

        if (user.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(messageSource);
        }

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Processes the OAuth user obtained from the OAuth2 provider.
     * If the user does not exist in the database, a new user is created based on the OAuth user details.
     *
     * @param oAuth2User The OAuth2 user obtained from the OAuth provider.
     * @return The processed user.
     */
    @Override
    public User processOAuthUser(CustomOAuth2User oAuth2User) {
        User user = userRepository.findByEmail(oAuth2User.getEmail()).orElse(null);

        if (user == null) {
            final String SURNAME_PLACEHOLDER = "CHANGE_SURNAME";

            RegisterRequest registerRequest = new RegisterRequest();

            registerRequest.setEmail(oAuth2User.getEmail());
            registerRequest.setProvider(oAuth2User.getProvider());

            if(oAuth2User.getProvider().equals(Provider.GOOGLE)) {
                registerRequest.setName(oAuth2User.getGivenName());
                registerRequest.setSurname(oAuth2User.getFamilyName());
            } else if (oAuth2User.getProvider().equals(Provider.FACEBOOK)) {
                registerRequest.setName(oAuth2User.getName());
                registerRequest.setSurname(SURNAME_PLACEHOLDER);
            }

            user = userRepository.save(buildUser(registerRequest));
        }
        return user;
    }

    @Override
    public User updateOAuth2UserWithFullData(CompleteOAuthRequest request, UUID userId) {
        User user = findById(userId);
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setAdditionalInfoRequired(false);

        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", messageSource));
    }

    private User buildUser(RegisterRequest request) {
        boolean additionalInfoRequired = !request.getProvider().equals(Provider.LOCAL);

        User.UserBuilder userBuilder = User
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .provider(request.getProvider())
                .role(Role.USER)
                .additionalInfoRequired(additionalInfoRequired);

        if (request.getPassword() != null) {
            userBuilder.password(passwordEncoder.encode(request.getPassword()));
        }

        return userBuilder.build();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }
}
