package org.openpkw.web.controllers;

import javax.inject.Inject;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Web Service
 *
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@RestController
public class VotesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(VotesController.class);

    @Inject
    RESTService restService;

    @RequestMapping("/votes")
    public ResponseEntity<AllVotesAnswerDTO> getJson() {
        ResponseEntity<AllVotesAnswerDTO> result;
        try {
            result = new ResponseEntity<>(restService.getAllVotes(), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get votes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(restService.getAllVotes(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}