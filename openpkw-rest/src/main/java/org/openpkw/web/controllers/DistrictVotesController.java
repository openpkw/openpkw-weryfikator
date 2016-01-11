package org.openpkw.web.controllers;

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

    @RequestMapping("/districtVotes/{districtCommitteeNumber}")
    public String getDistrictVotesInCommittee(@PathVariable int districtCommitteeNumber) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
