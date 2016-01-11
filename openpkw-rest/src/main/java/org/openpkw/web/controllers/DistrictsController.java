package org.openpkw.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictsController {

    //zwraca listę wszystkich okręgów
    @RequestMapping("/districts")
    public String getDistricts() {
        
        throw new UnsupportedOperationException();
    }
}
