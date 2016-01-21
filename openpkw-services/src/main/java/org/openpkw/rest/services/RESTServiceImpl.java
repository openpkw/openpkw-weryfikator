package org.openpkw.rest.services;

import javax.inject.Inject;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.rest.dto.AllVoteCommitteeDTO;
import org.openpkw.rest.dto.AllVotesAnswerDTO;
import org.openpkw.rest.dto.DistrictsDTO;
import org.openpkw.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;

/**
 *
 * @author kamil
 */
public class RESTServiceImpl implements RESTService {

    @Inject
    private ProtocolRepository protocolRepository;
    
    @Inject
    private PeripheralCommitteeRepository peripheralCommittee;

    @Override
    public AllVotesAnswerDTO getAllVotesAnswer() {
        int protocolNumber;                         //liczba obecnie przeslanych protokolow
        int protocolAllNumber;                      //liczba oczekiwanych protokow
        int votersVoteNumber;                       //liczba obecnie oddanych glosow
        int allVotersNumber;                        //liczba uprawnionych do glosowania
        AllVoteCommitteeDTO allVoteCommittee;       //

        protocolNumber = getActualCountProtocol();
        protocolAllNumber = getAllCountProtocols();
        votersVoteNumber = getActualCountVote();
        allVotersNumber = getAllEntitledToVote();
        
        
        
        return null;
    }

    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber) {
        return null;
    }

    @Override
    public VotesAnswerDTO getVotesAnswer(int districtCommitteeNumber, String teritorialCode, String peripheralCommitteeNumber) {
        return null;
    }

    @Override
    public DistrictsDTO getDistricts() {
        return null;
    }

    @Override
    public PeripheralCommitteeDTO getPeripherals(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        return null;
    }

    private int getActualCountProtocol() {
        return (int) protocolRepository.count();
    }

    private int getAllCountProtocols() {
        return (int) peripheralCommittee.count();
    }

    private int getActualCountVote() {
        return protocolRepository.getVotersVoteNumber();
    }

    private int getAllEntitledToVote() {
        return protocolRepository.getAllEntitledToVote();
    }

}
