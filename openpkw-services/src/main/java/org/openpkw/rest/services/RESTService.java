package org.openpkw.rest.services;

import org.openpkw.rest.dto.*;

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
            String peripheralCommitteeNumber
    );

    DistrictsDTO getDistricts();

    PeripheralCommitteeDTO getPeripherals(
            int districtCommitteeNumber,
            int peripheralCommitteeNumber,
            String teritorialCode,
            String town,
            String street
    );
}
