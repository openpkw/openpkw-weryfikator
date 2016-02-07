package org.openpkw.web.controllers;

import org.openpkw.rest.dto.VotesAnswerDTO;
import org.openpkw.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Web Service
 *
 * @author kamil
 */
@RestController
public class DistrictVotesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DistrictVotesController.class);

    @Autowired
    RESTService restService;

    @RequestMapping("/districtVotes/{districtCommitteeNumber}")
    public ResponseEntity<VotesAnswerDTO> getDistrictVotesInCommittee(@PathVariable("districtCommitteeNumber") int districtCommitteeNumber) {
        ResponseEntity<VotesAnswerDTO> result;

        try{
            result = new ResponseEntity<>(restService.getVotesAnswer(districtCommitteeNumber),HttpStatus.OK);
        }catch (NullPointerException nex) {
            String errorMsg = "Can't get districts [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
