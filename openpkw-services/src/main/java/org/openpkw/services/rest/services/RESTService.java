package org.openpkw.services.rest.services;

import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;

import java.util.List;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
public interface RESTService {

    AllVotesAnswerDTO getAllVotes();

    VotesAnswerDTO getVotes(int districtCommitteeNumber);

    VotesAnswerDTO getVotes(int districtCommitteeNumber, String teritorialCode, int peripheralCommitteeNumber);

    DistrictsDTO getDistricts();

    List<PeripheralCommitteeDTO> getPeripherals(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street);
}