package org.openpkw.web.controllers;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
