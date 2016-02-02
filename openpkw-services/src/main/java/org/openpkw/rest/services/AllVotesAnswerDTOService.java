package org.openpkw.rest.services;

import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.openpkw.repositories.ElectionCommitteeDistrictRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.rest.dto.AllVoteCommitteeDTO;
import org.openpkw.rest.dto.AllVotesAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author kamil on 02.02.16.
 */
@Service
public class AllVotesAnswerDTOService {

    @Autowired
    ProtocolRepository protocolRepository;

    @Autowired
    PeripheralCommitteeRepository peripheralCommittee;

    @Autowired
    ElectionCommitteeDistrictRepository electionCommitteeDistrict;

    public AllVotesAnswerDTOService() {
    }

    public AllVotesAnswerDTO generate(){
        AllVoteCommitteeDTO allVoteCommittee;
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
            String name = electionCommiteeInDistrinct.getElectionCommittee().getName();
            Integer listNumber = electionCommiteeInDistrinct.getListNumber();

            allVoteCommittee.setName(name);
            allVoteCommittee.setNumber(listNumber);
            allVoteCommittee.setVotes(voteSum);

            allVotesAnswerDTO.getVoteCommittees().add(allVoteCommittee);
        }

        return allVotesAnswerDTO;
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
