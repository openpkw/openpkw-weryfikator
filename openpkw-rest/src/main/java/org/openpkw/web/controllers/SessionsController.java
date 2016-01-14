package org.openpkw.web.controllers;

import org.openpkw.model.entity.User;
import org.openpkw.model.entity.UserDevice;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserDeviceRepository;
import org.openpkw.repositories.UserRepository;
import org.openpkw.services.sessions.SessionService;
import org.openpkw.services.sessions.dto.Token;
import org.openpkw.services.user.dto.UserCredentialsDTO;
import org.openpkw.validation.RequestValidator;
import org.openpkw.validation.RestClientErrorMessage;
import org.openpkw.validation.RestClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@OpenPKWAPIController
@Transactional
@RequestMapping("/sessions")
public class SessionsController {

    @Inject
    private RequestValidator registerUserValidator;

    @Inject
    private SessionService sessionService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> login(@RequestBody UserCredentialsDTO userCredentials) {
        Token token ;
        try {
            registerUserValidator.validateUserAuthorization(userCredentials);
            token = sessionService.login(userCredentials);
        } catch (RestClientException ex) {
            return buildResponse(ex.getErrorCode(), HttpStatus.BAD_REQUEST, null);
        }
        return buildResponse(RestClientErrorMessage.OK, HttpStatus.OK, token.getToken());
    }

    private ResponseEntity<Map<String, String>> buildResponse(RestClientErrorMessage validationError, HttpStatus httpStatus, String data) {
        Map<String, String> responseBody = new HashMap<String, String>();
        responseBody.put("errorCode", Integer.toString(validationError.getErrorCode()));
        responseBody.put("errorMessage", validationError.getErrorMessage());
        responseBody.put("data", data);
        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
    }
}