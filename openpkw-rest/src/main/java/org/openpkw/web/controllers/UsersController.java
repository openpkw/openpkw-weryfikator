package org.openpkw.web.controllers;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.user.UserService;
import org.openpkw.services.user.dto.ResponseDTO;
import org.openpkw.services.user.dto.UserDTO;
import org.openpkw.web.helper.UserBuilder;
import org.openpkw.validation.RequestValidator;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
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

    @Inject
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO userRegister) {
        try {
            registerUserValidator.validateUserRegistration(userRegister);
            userService.register(userRegister);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }
        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, null);
    }

    /**
     * Wyjaśnienie dla {email:.+}: https://jira.spring.io/browse/SPR-6164
     */
    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<UserDTO> get(@PathVariable String email) throws InstantiationException, IllegalAccessException {
        Optional<User> user = userService.get(email);
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

        if (user.isPresent())
            return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, null);
        else
            return buildResponse(RestClientErrorMessage.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
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