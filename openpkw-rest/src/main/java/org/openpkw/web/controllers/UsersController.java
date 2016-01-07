package org.openpkw.web.controllers;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.dto.ResponseDTO;
import org.openpkw.web.dto.UserDTO;
import org.openpkw.web.helper.UserBuilder;
import org.openpkw.web.validation.RequestValidator;
import org.openpkw.web.validation.RestClientErrorMessage;
import org.openpkw.web.validation.RestClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@OpenPKWAPIController
@Transactional
@RequestMapping("/users")
public class UsersController {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserDeviceRepository deviceRepository;

    @Inject
    private RequestValidator registerUserValidator;

    @Inject
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO userRegister) {
        try {
            registerUserValidator.validateUserRegistration(userRegister);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }

        Optional<User> userOptional = userRepository.findByEmailAddress(userRegister.getEmail());
        if (userOptional.isPresent()) {
            return buildResponse(RestClientErrorMessage.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
        }

        User user = new User();
        user.setEmail(userRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setFirstName(userRegister.getFirst_name());
        user.setLastName(userRegister.getLast_name());
        user.setIsActive(true);
        user.setUserType(UserType.VOLUNTEER);

        try {
            userRepository.saveAndFlush(user);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register new user: " + ex.getMessage(), ex);
        }

        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, null);
    }

    /**
     * Wyjaśnienie dla {email:.+}: https://jira.spring.io/browse/SPR-6164
     */
    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<UserDTO> get(@PathVariable String email) throws InstantiationException, IllegalAccessException {
        Optional<User> user = userRepository.findByEmailAddress(email);

        if (user.isPresent()) {
            return new ResponseEntity<>(userToJson(user.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDTO.buildErrorMessage(RestClientErrorMessage.USER_NOT_FOUND, UserDTO.class), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{email:.+}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<Map<String, String>> delete(@PathVariable("email") String email) {
        Optional<User> user = userRepository.findByEmailAddress(email);

        if (user != null) {
            for (UserDevice device : user.get().getUserDevices()) {
                deviceRepository.delete(device);
            }
            userRepository.delete(user.get());
            return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, null);
        } else {
            return buildResponse(RestClientErrorMessage.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }
    }

    private ResponseEntity<Map<String, String>> buildResponse(RestClientErrorMessage validationError, HttpStatus httpStatus, String data) {
        Map<String, String> responseBody = new HashMap<String, String>();
        responseBody.put("errorCode", Integer.toString(validationError.getErrorCode()));
        responseBody.put("errorMessage", validationError.getErrorMessage());
        responseBody.put("data", data);
        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
    }

    // TODO: Użyć jakiejś biblioteki
    private UserDTO userToJson(User user) {
        return new UserBuilder()
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .build();
    }
}