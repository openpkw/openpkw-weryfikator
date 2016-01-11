package org.openpkw.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * REST Web Service
 *
 * @author kamil
 */
@RestController
public class PeripheralsController {

    @RequestMapping("/peripherals/{districtCommitteeNumber}/{peripheralCommitteeNumber}/{teritorialCode}/{town}/{street}")
    public String getPeripherals(
            @PathVariable int districtCommitteeNumber,
            @PathVariable int peripheralCommitteeNumber,
            @PathVariable String teritorialCode,
            @PathVariable String town,
            @PathVariable String street
    ) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
