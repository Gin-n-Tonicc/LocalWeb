package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.enums.AddressableType;
import com.localWeb.localWeb.enums.PhonableType;
import com.localWeb.localWeb.enums.Provider;
import com.localWeb.localWeb.enums.Role;
import com.localWeb.localWeb.exceptions.city.CityNotFoundException;
import com.localWeb.localWeb.exceptions.common.AccessDeniedException;
import com.localWeb.localWeb.exceptions.country.CountryNotFoundException;
import com.localWeb.localWeb.exceptions.user.UserCreateException;
import com.localWeb.localWeb.exceptions.user.UserNotFoundException;
import com.localWeb.localWeb.models.dto.auth.AdminUserDTO;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.auth.RegisterRequest;
import com.localWeb.localWeb.models.dto.common.AddressDTO;
import com.localWeb.localWeb.models.dto.request.CompleteOAuthRequest;
import com.localWeb.localWeb.models.entity.*;
import com.localWeb.localWeb.repositories.*;
import com.localWeb.localWeb.security.CustomOAuth2User;
import com.localWeb.localWeb.services.FileService;
import com.localWeb.localWeb.services.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final PhoneRepository phoneRepository;
    private final CountryRepository countryRepository;

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
    public User processOAuthUser(CustomOAuth2User oAuth2User) throws Exception {
        User user = userRepository.findByEmail(oAuth2User.getEmail()).orElse(null);

        if (user == null) {
            final String SURNAME_PLACEHOLDER = "CHANGE_SURNAME";

            RegisterRequest registerRequest = new RegisterRequest();

            registerRequest.setEmail(oAuth2User.getEmail());
            registerRequest.setProvider(oAuth2User.getProvider());

            if (oAuth2User.getProvider().equals(Provider.GOOGLE)) {
                registerRequest.setName(oAuth2User.getGivenName());
                registerRequest.setSurname(oAuth2User.getFamilyName());

                File file = fileService.downloadImageFromURL(oAuth2User.getPicture());
                com.localWeb.localWeb.models.entity.File avatar = fileService.uploadFile(file, oAuth2User.getName() + ".png");

                registerRequest.setAvatarId(avatar.getId());

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

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public void setAddressesForUser(CompleteOAuthRequest request, User user) {
        // Handle primary address
        if (request.getPrimaryAddress() != null) {
            AddressDTO primaryAddress = request.getPrimaryAddress();
            setAndSaveAddress(primaryAddress, user);
        }

        // Handle secondary address
        if (request.getSecondaryAddress() != null) {
            AddressDTO secondaryAddress = request.getSecondaryAddress();
            setAndSaveAddress(secondaryAddress, user);
        }
    }

    @Override
    public void setPhoneNumberForUser(CompleteOAuthRequest request, User user) {
        Phone phone = new Phone();
        Country country = countryRepository.findByIdAndDeletedAtIsNull(request.getPhone().getCountry()).orElseThrow(() -> new CountryNotFoundException(messageSource));
        phone.setCountry(country);
        phone.setNumber(request.getPhone().getNumber());
        phone.setPhonableType(PhonableType.USER);
        phone.setPhoneable(user);
        phoneRepository.save(phone);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", messageSource));
    }

    private User buildUser(RegisterRequest request) {
        boolean additionalInfoRequired = !request.getProvider().equals(Provider.LOCAL);
        com.localWeb.localWeb.models.entity.File avatar = fileRepository.findByIdAndDeletedAtIsNull(request.getAvatarId());

        User.UserBuilder userBuilder = User
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .provider(request.getProvider())
                .role(Role.USER)
                .avatar(avatar)
                .additionalInfoRequired(additionalInfoRequired);

        if (request.getPassword() != null) {
            userBuilder.password(passwordEncoder.encode(request.getPassword()));
        }

        return userBuilder.build();
    }

    /**
     * We use this method to map the AddressDTO to Address entity.
     * The reason is that in Address we have polymorphic association,
     * therefore the usual modelmapper doesn't work properly.
     */
    private void setAndSaveAddress(AddressDTO addressDTO, User user) {
        addressDTO.setAddressableId(user.getId());
        addressDTO.setAddressableType(AddressableType.USER);

        City city = cityRepository.findById(addressDTO.getCityId())
                .orElseThrow(() -> new CityNotFoundException(messageSource));

        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setLine(addressDTO.getLine());
        address.setLat(addressDTO.getLat());
        address.setLng(addressDTO.getLng());
        address.setCity(city);
        address.setAddressable(user);
        address.setAddressableType(AddressableType.USER);

        addressRepository.save(address);
    }
}
