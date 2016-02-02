package org.openpkw.web.controllers;

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

    @RequestMapping("/votes")
    public String getJson() {
        ResponseEntity<VotesAnswerDTO> resul;
        try {
            resul = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get votes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
