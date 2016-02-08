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

    @Inject
    private ProtocolRepository protocolRepository;

    @Inject
    private PeripheralCommitteeRepository peripheralCommittee;

    @Inject
    private PeripheralCommitteeRepository getPeripheralCommittee;

    @Autowired
    private ElectionCommitteeDistrictRepository electionCommitteeDistrict;

    @Override
    public AllVotesAnswerDTO getAllVotesAnswer() {
        return new AllVotesAnswerDTOService().generate();
    }

    //return answer to "/districtVotes/{districtCommitteeNumber}" request
    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber) {
        return new VotesAnswerDTOinDistrictService().generate(districtCommitteeNumber);
    }

    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber, String teritorialCode, String peripheralCommitteeNumber) {
        return null;
    }

    @Override
    public DistrictsDTO getDistricts() {
        return new DistrictsDTOService().generate();
    }

    @Override
    public PeripheralCommitteeDTO getPeripherals(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return null;
    }

}
