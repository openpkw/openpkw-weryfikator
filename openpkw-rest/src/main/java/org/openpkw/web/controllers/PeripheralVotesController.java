package org.openpkw.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * REST Web Service ilosc gloswo w obwodzie
 *
 * @author kamil
 */
@RestController
public class PeripheralVotesController {

    @RequestMapping("peripheralVotes/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}")
    public String getPeripheralVotes(
            @PathVariable int districtCommitteeNumber,
            @PathVariable String teritorialCode,
            @PathVariable int peripheralCommitteeNumber
    ) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
