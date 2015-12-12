package org.openpkw.web.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.dto.NewUserDTO;
import org.openpkw.web.validation.RequestValidator;
import org.openpkw.web.validation.RestClientErrorMessage;
import org.openpkw.web.validation.RestClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@OpenPKWAPIController
@Transactional
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceRepository deviceRepository;

    @Autowired
    private RequestValidator registerUserValidator;

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<Map<String, String>> register(@RequestBody NewUserDTO userRegister) {
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
        user.setPassword(userRegister.getPassword());
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
    public ResponseEntity<Map<String, String>> get(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmailAddress(email);

        if (user.isPresent()) {
            return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, userToJson(user.get()));
        } else {
            return buildResponse(RestClientErrorMessage.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
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
    private String userToJson(User user) {
        String result = "{";
        result += "\"firstName\":\"" + user.getFirstName() + "\",";
        result += "\"lastName\":\"" + user.getLastName() + "\",";
        result += "\"email\":\"" + user.getEmail() + "\"";
        result += "}";
        return result;
    }
}