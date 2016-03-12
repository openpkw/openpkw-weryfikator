package org.openpkw.web.controllers;

import javax.inject.Inject;

import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SzestKam (Kamil Szestowicki) 
 */
@RestController
public class DistrictsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DistrictsController.class);

    @Inject
    RESTService restService;

    /**
     * Zwraca listę wszystkich okręgów
     */
    @RequestMapping("/districts")
    public ResponseEntity<DistrictsDTO> getDistricts() {
        ResponseEntity<DistrictsDTO> result;

        try {
            result = new ResponseEntity<>(restService.getDistricts(), HttpStatus.OK);
        } catch (NullPointerException nex) {
            String errorMsg = "Can't get districts [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(restService.getDistricts(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}