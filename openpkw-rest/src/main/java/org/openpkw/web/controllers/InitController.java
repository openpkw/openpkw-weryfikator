package org.openpkw.web.controllers;

import org.openpkw.services.init.InitService;
import org.openpkw.services.init.dto.InitDTO;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.validation.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by mrozi on 14.01.16.
 */
@OpenPKWAPIController @RequestMapping("/api") public class InitController {

    @Inject private InitService initService;

    private final static Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @RequestMapping(value = "/init", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON) public ResponseEntity<InitDTO> init() {
        try {
            return new ResponseEntity<>(initService.initDatabase(false), HttpStatus.OK);
        } catch (RestClientException exception) {
            LOGGER.warn("Can't init", exception);
            return new ResponseEntity<>(new InitDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reinit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON) public ResponseEntity<InitDTO> reInit() {
        try {
            return new ResponseEntity<>(initService.initDatabase(true), HttpStatus.OK);
        } catch (RestClientException exception) {
            LOGGER.warn("Can't reinit", exception);
            return new ResponseEntity<>(new InitDTO(exception.getErrorCode().getErrorMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
