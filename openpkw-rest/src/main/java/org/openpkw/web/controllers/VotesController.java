package org.openpkw.web.controllers;

import org.openpkw.rest.dto.VotesAnswerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping("/votes")
    public ResponseEntity<VotesAnswerDTO> getJson() {
        ResponseEntity<VotesAnswerDTO> result;
        try {
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get votes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
