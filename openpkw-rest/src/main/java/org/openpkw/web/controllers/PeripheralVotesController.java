package org.openpkw.web.controllers;

import com.thoughtworks.xstream.mapper.Mapper;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.openpkw.rest.services.RESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;

/**
 * REST Web Service ilosc gloswo w obwodzie
 *
 * @author kamil
 */
@RestController
public class PeripheralVotesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DistrictVotesController.class);

    @Inject
    RESTService restService;

    @RequestMapping("peripheralVotes/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}")
    public ResponseEntity<VotesAnswerDTO> getPeripheralVotes(
            @PathVariable(value = "districtCommitteeNumber") int districtCommitteeNumber,
            @PathVariable(value = "teritorialCode") String teritorialCode,
            @PathVariable(value = "peripheralCommitteeNumber") int peripheralCommitteeNumber
    ) {
        ResponseEntity<VotesAnswerDTO> result;

        try{
            result = new ResponseEntity<>(
                    restService.getVotesAnswer(districtCommitteeNumber,teritorialCode,peripheralCommitteeNumber),
                    HttpStatus.OK);
        }catch (NullPointerException nex){
            String errorMsg = "Can't get districtsVotes [NullPointerException]";
            LOGGER.warn(errorMsg, nex);
            result = new ResponseEntity<>(new VotesAnswerDTO(), HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
