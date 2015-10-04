package org.openpkw.web.controllers;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Test controller
 * @author Sebastian.Pogorzelski
 */
@OpenPKWAPIController
public class HealthCheckController {

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/healthCheck/simpleTest", method = RequestMethod.GET)
    public ResponseEntity<Object> simpleTest() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/healthCheck/dataTest", method = RequestMethod.GET)
    public ResponseEntity<Object> dataTest() {
        userRepository.findOne(1l);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/healthCheck/securityTest", method = RequestMethod.GET)
    public ResponseEntity<Object> securityTest() {
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
