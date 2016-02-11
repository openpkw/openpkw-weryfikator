package org.openpkw.rest.services;

import org.openpkw.rest.dto.*;

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
