package org.openpkw.services.rest.services;


import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;

import java.util.List;

/**
 *
 * @author kamil
 */
public interface RESTService {

    AllVotesAnswerDTO getAllVotesAnswer();

    VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber);

    VotesAnswerDTO getVotesAnswer(
            int districtCommitteeNumber,
            String teritorialCode,
            int peripheralCommitteeNumber
    );

    DistrictsDTO getDistricts();

    List<PeripheralCommitteeDTO> getPeripherals(
            int districtCommitteeNumber,
            int peripheralCommitteeNumber,
            String teritorialCode,
            String town,
            String street
    );
}
