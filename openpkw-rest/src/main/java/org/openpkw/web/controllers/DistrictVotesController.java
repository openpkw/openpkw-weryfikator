package org.openpkw.web.controllers;

import org.openpkw.services.rest.dto.VotesAnswerDTO;
import org.openpkw.services.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST Web Service
 *
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@RestController
public class DistrictVotesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DistrictVotesController.class);

    @Inject
    private RESTService restService;

    @RequestMapping("/districtVotes/{districtCommitteeNumber}")
    public ResponseEntity<VotesAnswerDTO> getDistrictVotesInCommittee(@PathVariable("districtCommitteeNumber") int districtCommitteeNumber) {
        ResponseEntity<VotesAnswerDTO> result;

        try {
            result = new ResponseEntity<>(restService.getVotes(districtCommitteeNumber), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get districts [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}