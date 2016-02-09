package org.openpkw.rest.services;

import javax.inject.Inject;

import org.openpkw.repositories.ElectionCommitteeDistrictRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.rest.dto.AllVotesAnswerDTO;
import org.openpkw.rest.dto.DistrictsDTO;
import org.openpkw.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kamil
 */
@Service
public class RESTServiceImpl implements RESTService {

    //+ /votes
    @Override
    public AllVotesAnswerDTO getAllVotesAnswer() {
        return new AllVotesAnswerDTOService().generate();
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
        return new DistrictsDTOService().generate();
    }

    // /peripheral...
    @Override
    public PeripheralCommitteeDTO getPeripherals(
            int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return new PeripheralCommitteeDTOService().generate(districtCommitteeNumber,
                peripheralCommitteeNumber,
                teritorialCode,
                town,
                street);
    }

}
