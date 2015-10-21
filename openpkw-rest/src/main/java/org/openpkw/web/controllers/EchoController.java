package org.openpkw.web.controllers;

import java.util.Map;

import org.openpkw.qualifier.OpenPKWAPIController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@OpenPKWAPIController
@RequestMapping("/test")
public class EchoController {

    @RequestMapping(value = "/echo", method = RequestMethod.POST)
    public Map<String, String> echo(@RequestBody Map<String, String> object) {

        return object;
    }
}