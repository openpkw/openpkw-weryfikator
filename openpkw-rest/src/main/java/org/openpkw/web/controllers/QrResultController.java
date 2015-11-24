package org.openpkw.web.controllers;

import org.openpkw.qr.dto.QrDTO;
import org.openpkw.qr.dto.QrResultDTO;
import org.openpkw.qr.parser.QrWrapper;
import org.openpkw.qr.service.QrService;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

/**
 * QR Controller responsible for
 * Created by Sebastian on 06.11.2015.
 */
@OpenPKWAPIController
@RequestMapping("/api")
public class QrResultController {

    @Inject
    private QrService qrService;


    private final static Logger LOGGER = LoggerFactory.getLogger(QrResultController.class);

    @RequestMapping(value = "/qr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<QrResultDTO> saveResult(@RequestBody QrDTO result) {

        //TODO add necessary validations
        if (result.getQr() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {

            QrResultDTO qrResultDTO = qrService.saveResult(result);

        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Can't save result", ex);
            return new ResponseEntity<>(new QrResultDTO("Can't save result"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
