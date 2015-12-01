package org.openpkw.web.controllers;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.model.entity.UserType;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.web.Autorize;
import org.openpkw.web.Token;
import org.openpkw.web.UserRegister;
import org.openpkw.web.validation.LoginControllerRequestValidator;
import org.openpkw.web.validation.RestClientErrorMessage;
import org.openpkw.web.validation.RestClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@OpenPKWAPIController
@Transactional
@RequestMapping("/authorize")
public class LoginController {

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceRepository deviceRepository;

    @Autowired
    private LoginControllerRequestValidator registerUserValidator;

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegister userRegister) {
        try {
            registerUserValidator.validateUserRegistration(userRegister);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }

        User user = userRepository.findByEmailAddress(userRegister.getEmail());
        if (user != null) {
            return buildResponse(RestClientErrorMessage.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST, null);
        }

        if (StringUtils.isEmpty(userRegister.getEmail())) {

        }

        user = new User();
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        user.setFirstName(userRegister.getFirst_name());
        user.setLastName(userRegister.getLast_name());
        user.setIsActive(true);
        user.setUserType(userRegister.getUserType());

        try {
            userRepository.saveAndFlush(user);
            System.out.println("" + user);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register new user: " + ex.getMessage(), ex);
        }
        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, null);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> login(Autorize autorize) {
        try {
            registerUserValidator.validateUserAuthorization(autorize);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }
        System.out.println(autorize);
        Token token = new Token();
        User user = userRepository.findByEmailAddress(autorize.getEmail());
        if (user == null) {
            user = new User(autorize.getEmail(), autorize.getPassword());
            user.setUserType(UserType.VOLUNTEER);
            user.setIsActive(Boolean.TRUE);
            user.setTokenCreatedDate(new Date());
            userRepository.saveAndFlush(user);
        }

        UserDevice device = deviceRepository.findByUserIdAndDevId(user.getUserID(), autorize.getDevid());
        if (device == null) {
            device = new UserDevice();
            device.setDevId(autorize.getDevid());
            device.setUser(user);
        }
        token = updateUserToken(device);
        deviceRepository.saveAndFlush(device);

        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, token.getToken());
    }

    private Token updateUserToken(UserDevice device) {
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