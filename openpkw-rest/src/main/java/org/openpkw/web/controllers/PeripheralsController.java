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
            @PathVariable(value = "districtCommitteeNumber") int districtCommitteeNumber,
            @PathVariable(value = "peripheralCommitteeNumber") int peripheralCommitteeNumber,
            @PathVariable(value = "teritorialCode") String teritorialCode,
            @PathVariable(value = "town") String town,
            @PathVariable(value = "street") String street
    ) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
