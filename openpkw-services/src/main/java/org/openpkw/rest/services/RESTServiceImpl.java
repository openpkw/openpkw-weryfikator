package org.openpkw.rest.services;

import javax.inject.Inject;

import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.openpkw.repositories.ElectionCommitteeDistrictRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.rest.dto.AllVoteCommitteeDTO;
import org.openpkw.rest.dto.AllVotesAnswerDTO;
import org.openpkw.rest.dto.DistrictsDTO;
import org.openpkw.rest.dto.PeripheralCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @author kamil
 */
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
        AllVoteCommitteeDTO allVoteCommittee;       //

        AllVotesAnswerDTO allVotesAnswerDTO = new AllVotesAnswerDTO();
        allVotesAnswerDTO.setProtocolNumber(getActualCountProtocol());
        allVotesAnswerDTO.setAllVotersNumber(getAllCountProtocols());
        allVotesAnswerDTO.setVotersVoteNumber(getActualCountVote());
        allVotesAnswerDTO.setAllVotersNumber(getAllEntitledToVote());

        List<ElectionCommitteeDistrict> electionCommitteeDistrictAll = (List<ElectionCommitteeDistrict>) electionCommitteeDistrict.findAll();
        for (ElectionCommitteeDistrict electionCommiteeInDistrinct : electionCommitteeDistrictAll) {

            Collection<ElectionCommitteeVote> voteCollection = electionCommiteeInDistrinct.getElectionCommitteeVoteCollection();
            Integer voteSum = 0;
            for (ElectionCommitteeVote ecv : voteCollection
                    ) {
                voteSum += ecv.getVoteNumber();
            }

            allVoteCommittee = new AllVoteCommitteeDTO();
            //TODO zmiana nazwy na nazwe obiektu
            String name = electionCommiteeInDistrinct.getElectionCommitteeId().getName();
            Integer listNumber = electionCommiteeInDistrinct.getListNumber();

            allVoteCommittee.setName(name);
            allVoteCommittee.setNumber(listNumber);
            allVoteCommittee.setVotes(voteSum);

            allVotesAnswerDTO.getVoteCommittees().add(allVoteCommittee);
        }

        return allVotesAnswerDTO;
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
        //tutaj zmiana, trzeba zliczyc liczbe uprawnionych z tabeli PERIPHERAL_COMMITTEE.allowed_to_vote
        return protocolRepository.getAllEntitledToVote();
    }

}
