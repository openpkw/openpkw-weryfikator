package org.openpkw.web.controllers;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;

/**
 * REST Web Service
 *
 * @author kamil
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
            HttpHeaders htppHeaders = new HttpHeaders();
            htppHeaders.add("Access-Control-Allow-Origin", "*");
            htppHeaders.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            result = new ResponseEntity<>(restService.getAllVotesAnswer(), htppHeaders, HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get votes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(restService.getAllVotesAnswer(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
