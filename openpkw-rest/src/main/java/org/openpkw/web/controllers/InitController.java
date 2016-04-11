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
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@OpenPKWAPIController
@RequestMapping("/database")
public class InitController {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @Inject
    private InitService initService;

    @RequestMapping(value = "/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InitDTO> init() {
        try {
            return new ResponseEntity<>(initService.initDatabase(false), HttpStatus.OK);
        } catch (RestClientException exception) {
            LOGGER.warn("Can't init", exception);
            return new ResponseEntity<>(new InitDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reinit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InitDTO> reInit() {
        try {
            return new ResponseEntity<>(initService.initDatabase(true), HttpStatus.OK);
        } catch (RestClientException exception) {
            LOGGER.warn("Can't reinit", exception);
            return new ResponseEntity<>(new InitDTO(exception.getErrorCode().getErrorMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/generateVotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> generateVotes() {
        initService.generateVotes();
        return new ResponseEntity<>("Votes generation completed successfully.", HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteVotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> deleteVotes() {
        initService.deleteVotes();
        return new ResponseEntity<>("Votes deleted successfully.", HttpStatus.OK);
    }
}