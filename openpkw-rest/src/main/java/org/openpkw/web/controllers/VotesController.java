package org.openpkw.web.controllers;

import org.openpkw.rest.dto.AllVotesAnswerDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.openpkw.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

/**
 * REST Web Service
 *
 * @author kamil
 */
@RestController
public class VotesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(VotesController.class);

    @Autowired
    RESTService restService;

    @RequestMapping("/votes")
    public ResponseEntity<AllVotesAnswerDTO> getJson() {
        ResponseEntity<AllVotesAnswerDTO> result;
        try {

            result = new ResponseEntity<>(restService.getAllVotesAnswer(), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get votes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(restService.getAllVotesAnswer(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
