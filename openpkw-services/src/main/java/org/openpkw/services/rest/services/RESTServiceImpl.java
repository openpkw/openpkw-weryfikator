package org.openpkw.services.rest.services;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author kamil
 */
@Service
public class RESTServiceImpl implements RESTService {

    @Inject
    DistrictsDTOService districtsDTOService;

    @Inject
    AllVotesAnswerDTOService allVotesAnswerDTOService;

    //+ /votes
    @Override
    public AllVotesAnswerDTO getAllVotesAnswer() {
        return allVotesAnswerDTOService.generate();
    }

    //+  /districtVotes/{districtCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber) {
        return new VotesAnswerDTOinDistrictService().generate(districtCommitteeNumber);
    }

    //+ /peripheralVotes/{districtCommitteeNumber}/{teritorialCode}/{peripheralCommitteeNumber}
    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber, String teritorialCode, int peripheralCommitteeNumber) {
        return new VotesAnswerDTOinPeripheralService().
                generate(districtCommitteeNumber, teritorialCode, peripheralCommitteeNumber);
    }

    //+ /districts
    @Override
    public DistrictsDTO getDistricts() {
        return districtsDTOService.generate();
    }

    // /peripheral...
    @Override
    public List<PeripheralCommitteeDTO> getPeripherals(
            int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return new PeripheralCommitteeDTOService().generate(districtCommitteeNumber,
                peripheralCommitteeNumber,
                teritorialCode,
                town,
                street);
    }

}
