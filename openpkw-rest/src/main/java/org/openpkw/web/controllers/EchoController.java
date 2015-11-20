package org.openpkw.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.PeripherialCommitteeAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Tomasz Łabuz on 2015-07-17.
 */
@OpenPKWAPIController
@RequestMapping("/test")
public class EchoController {

    @Autowired
    private PeripheralCommitteeRepository pcRepository;

    @RequestMapping(value = "/echo", method = RequestMethod.POST)
    public Map<String, String> echo(@RequestBody Map<String, String> object) {

        return object;
    }

    @RequestMapping(value = "/selftest", method = RequestMethod.GET)
    public Map<String, String> selfTest() {
        String databaseStatus = getDatabaseStatus();

        Map<String, String> result = new HashMap<String, String>();

        result.put("WebappStatus", "OK");
        result.put("DatabaseStatus", databaseStatus);

        return result;
    }

    private String getDatabaseStatus() {
        try {
            if (pcRepository != null) {
                List<PeripheralCommittee> peripheralCommittees = pcRepository.findAll();
                if (peripheralCommittees != null) {
                    if (peripheralCommittees.size() > 0) {
                        return "OK. Loaded " + peripheralCommittees.size() + " peripheral committees from the database.";
                    } else {
                        return "Unknown. Connected to the database but no peripheral committees have been found.";
                    }
                } else {
                    return "Unknown. Connected to the database but could not load any peripheral committees..";
                }
            } else {
                return "Failure. Peripherial Committee Repository is not injected";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Failure. An exception has been thrown when trying to load peripheral committess from the database: " + ex.getMessage();
        }
    }
}