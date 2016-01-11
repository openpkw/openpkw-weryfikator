package org.openpkw.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Web Service
 *
 * @author kamil
 */
@RestController
public class VotesController {

    @RequestMapping("/votes")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
