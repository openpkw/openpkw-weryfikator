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
@RequestMapping(value="/healthCheck")
public class HealthCheckController {

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    public ResponseEntity<String> simpleTest() {
        return new ResponseEntity<>("HealthCheck Simple Test OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/dataTest", method = RequestMethod.GET)
    public ResponseEntity<String> dataTest() {
        userRepository.findOne(1l);
        return new ResponseEntity<>("HealthCheck Data Test OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/securityTest", method = RequestMethod.GET)
    public ResponseEntity<String> securityTest() {
        return new ResponseEntity<>("HealthCheck Security Test OK", HttpStatus.OK);
    }



}
