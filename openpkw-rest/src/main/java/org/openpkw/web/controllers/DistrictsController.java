package org.openpkw.web.controllers;

import javax.inject.Inject;

import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.services.RESTServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 */
@RestController
public class DistrictsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DistrictsController.class);

    @Inject
    private RESTServiceFacade restServiceFacade;

    /**
     * Zwraca listę wszystkich okręgów
     */
    @RequestMapping("/districts")
    public ResponseEntity<DistrictsDTO> getDistricts() {
        ResponseEntity<DistrictsDTO> result = new ResponseEntity<>(restServiceFacade.getDistricts(), HttpStatus.OK);

        return result;
    }
}