package org.openpkw.web.controllers;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.dto.AuthorizeUserRequest;
import org.openpkw.web.dto.Token;
import org.openpkw.web.validation.LoginControllerRequestValidator;
import org.openpkw.web.validation.RestClientErrorMessage;
import org.openpkw.web.validation.RestClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@OpenPKWAPIController
@Transactional
@RequestMapping("/sessions")
public class SessionsController {

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceRepository deviceRepository;

    @Autowired
    private LoginControllerRequestValidator registerUserValidator;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthorizeUserRequest autorize) {
        try {
            registerUserValidator.validateUserAuthorization(autorize);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }

        User user = userRepository.findByEmailAddress(autorize.getEmail());
        if (user == null) {
            return buildResponse(RestClientErrorMessage.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, null);
        }

        // W piewszej fazie implementacji zakładamy, że każdy użytkownik używa tylko jednego urządzenia
        // Identyfikatorem urządzenia jest email użytkownika
        String deviceID = autorize.getEmail();
        UserDevice device = deviceRepository.findByUserIdAndDevId(user.getUserID(), deviceID);
        if (device == null) {
            device = new UserDevice();
            device.setDevId(deviceID);
            device.setUser(user);
        }
        Token token = createUserToken(device);
        deviceRepository.saveAndFlush(device);

        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, token.getToken());
    }

    private Token createUserToken(UserDevice device) {
        byte[] bToken = new byte[24];
        Token token = new Token();
        secureRandom.nextBytes(bToken);
        token.setToken(bToken);
        device.setToken(token.getToken());
        return token;
    }

    private ResponseEntity<Map<String, String>> buildResponse(RestClientErrorMessage validationError, HttpStatus httpStatus, String data) {
        Map<String, String> responseBody = new HashMap<String, String>();
        responseBody.put("errorCode", Integer.toString(validationError.getErrorCode()));
        responseBody.put("errorMessage", validationError.getErrorMessage());
        responseBody.put("data", data);
        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
    }
}