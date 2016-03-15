package org.openpkw.web.controllers;

import org.openpkw.model.entity.User;
import org.openpkw.services.qr.dto.QrDTO;
import org.openpkw.services.qr.dto.QrResultDTO;
import org.openpkw.services.qr.QrService;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.services.sign.SignService;
import org.openpkw.services.user.UserService;
import org.openpkw.web.security.helper.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.Base64;
import java.util.Optional;

/**
 * QR Controller responsible for
 * Created by Sebastian on 06.11.2015.
 */
@OpenPKWAPIController
@RequestMapping("/api")
public class QrResultController {

    @Inject
    private QrService qrService;

    @Inject
    private UserService userService;

    @Inject
    private SignService signService;

    private final static Logger LOGGER = LoggerFactory.getLogger(QrResultController.class);

    @RequestMapping(value = "/qr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<QrResultDTO> saveResult(@RequestBody QrDTO qrDTO) {

        ResponseEntity<QrResultDTO> result;

        try {

            if (qrDTO.getQr() == null) {
                throw new NullPointerException();
            }

            verifyUserToken(qrDTO.getQr(), qrDTO.getToken());

            result = new ResponseEntity<>(qrService.saveResult(qrDTO), HttpStatus.OK);

        }
        catch (IllegalArgumentException ex) {
            LOGGER.warn("Can't save result", ex);
            result = handleException("Can't save result", HttpStatus.NOT_FOUND);
        }
        catch (NullPointerException ex) {
            LOGGER.warn("Can't save result", ex);
            result = handleException("Can't save result", HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    private void verifyUserToken(String qr, String token) throws AuthenticationException {
        if (token == null) {
            throw new BadCredentialsException("Can't verify token");
        }
        Optional<User> userOptional = userService.get(SecurityUtils.getCurrentLogin());

        if (!userOptional.isPresent()) {
            throw new BadCredentialsException("Can't verify token");
        }
        if (
                !signService.signatureVerification(signService.getPublicKeyFromBase64(userOptional.get().getPublicKey()), qr, Base64.getDecoder().decode(token))
                ) {
            throw new BadCredentialsException("Can't verify token");
        }

    }

    private ResponseEntity<QrResultDTO> handleException(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new QrResultDTO(errorMessage), httpStatus);
    }

}
