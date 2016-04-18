package org.openpkw.services.rest.services;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Service
public class RESTServiceFacadeImpl implements RESTServiceFacade {

    @Inject
    private DistrictsService districtsService;

    @Inject
    private VotesService votesService;

    @Inject
    private PeripheralCommitteeService peripheralCommitteeService;

    //+ /votes
    @Override
    public AllVotesAnswerDTO getAllVotes() {
        return votesService.getAllVotes();
    }

    //+  /districtVotes/{districtCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotes(int districtCommitteeNumber) {
        return votesService.getVotesInDistrict(districtCommitteeNumber);
    }

    //+ /peripheralVotes/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotes(int districtCommitteeNumber, String teritorialCode, int peripheralCommitteeNumber) {
        return votesService.getVotesInPeripheral(districtCommitteeNumber, teritorialCode, peripheralCommitteeNumber);
    }

    //+ /districts
    @Override
    @Transactional
    public DistrictsDTO getDistricts() {
        return districtsService.getDistricts();
    }

    // /peripheral...
    @Override
    public List<PeripheralCommitteeDTO> getPeripherals(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return peripheralCommitteeService.generate(districtCommitteeNumber, peripheralCommitteeNumber, teritorialCode, town, street);
    }
}