package org.openpkw.web.controllers;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.services.password.ChangePasswordService;
import org.openpkw.services.password.dto.ChangePasswordRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@OpenPKWAPIController
@RequestMapping("/api/account/password")
public class ChangePasswordController {

    @Inject
    private ChangePasswordService changePasswordService;

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> createChangePasswordRequest(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        changePasswordService.createChangePasswordRequest(changePasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Void> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        return null;
    }
}
