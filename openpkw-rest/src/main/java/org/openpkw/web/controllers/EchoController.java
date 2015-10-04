package org.openpkw.web.controllers;

import org.openpkw.model.entity.User;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@OpenPKWAPIController
@RequestMapping("/test")
public class EchoController {



    @RequestMapping(value = "/echo",
            method = RequestMethod.POST
    )
    public Map echo(@RequestBody Map object) {

        return object;
    }



}
