package org.openpkw.services.rest.services;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Service
public class RESTServiceImpl implements RESTService {

    @Inject
    private DistrictsDTOService districtsDTOService;

    @Inject
    private AllVotesAnswerDTOService allVotesAnswerDTOService;

    @Inject
    private VotesAnswerDTOinDistrictService votesAnswerDTOinDistrictService;

    @Inject
    private VotesAnswerDTOinPeripheralService votesAnswerDTOinPeripheralService;

    @Inject
    private PeripheralCommitteeDTOService peripheralCommitteeDTOService;

    //+ /votes
    @Override
    public AllVotesAnswerDTO getAllVotes() {
        return allVotesAnswerDTOService.getAllVotes();
    }

    //+  /districtVotes/{districtCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotes(int districtCommitteeNumber) {
        return votesAnswerDTOinDistrictService.generate(districtCommitteeNumber);
    }

    //+ /peripheralVotes/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotes(int districtCommitteeNumber, String teritorialCode, int peripheralCommitteeNumber) {
        return votesAnswerDTOinPeripheralService.generate(districtCommitteeNumber, teritorialCode, peripheralCommitteeNumber);
    }

    //+ /districts
    @Override
    public DistrictsDTO getDistricts() {
        return districtsDTOService.generate();
    }

    // /peripheral...
    @Override
    public List<PeripheralCommitteeDTO> getPeripherals(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return peripheralCommitteeDTOService.generate(districtCommitteeNumber, peripheralCommitteeNumber, teritorialCode, town, street);
    }
}