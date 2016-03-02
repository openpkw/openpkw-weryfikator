package org.openpkw.services.rest.services;

import org.openpkw.model.entity.ElectionCommittee;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.openpkw.repositories.ElectionCommitteeDistrictRepository;
import org.openpkw.repositories.ElectionCommitteeRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.services.rest.dto.AllVoteCommitteeDTO;
import org.openpkw.services.rest.dto.AllVotesAnswerDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

/**
 * @author kamil on 02.02.16.
 */
@Component
public class AllVotesAnswerDTOService {

    @Qualifier("protocolRepository")
    @Inject
    private ProtocolRepository protocolRepository;

    @Qualifier("peripheralCommitteeRepository")
    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;


    @Qualifier("electionCommitteeRepository")
    @Inject
    private ElectionCommitteeRepository  electionCommitteeRepository;

    @Qualifier("electionCommitteeDistrictRepository")
    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    public AllVotesAnswerDTOService() {
    }

    public AllVotesAnswerDTO generate(){
        Random r = new Random();
        AllVotesAnswerDTO allVotesAnswerDTO = new AllVotesAnswerDTO();
        allVotesAnswerDTO.setProtocolAllNumber(100000+r.nextInt(100000));
        allVotesAnswerDTO.setProtocolNumber(r.nextInt( (int)allVotesAnswerDTO.getProtocolAllNumber()));
        allVotesAnswerDTO.setVoteCommittees(new ArrayList<>());
        List<ElectionCommittee> electionCommitteeList = electionCommitteeRepository.findAll();
        int i=0;
        int votes=0;
        for(ElectionCommittee electionCommittee:electionCommitteeList)
        {
            AllVoteCommitteeDTO allVoteCommittee  = new AllVoteCommitteeDTO();
            allVoteCommittee.setName(electionCommittee.getName());
            allVoteCommittee.setNumber(i++);
            allVoteCommittee.setVotes(r.nextInt(1000000));
            allVotesAnswerDTO.getVoteCommittees().add(allVoteCommittee);
            votes+=allVoteCommittee.getVotes();
        }
        allVotesAnswerDTO.setVotersVoteNumber(votes);
        allVotesAnswerDTO.setAllVotersNumber(allVotesAnswerDTO.getVotersVoteNumber()+r.nextInt(1000000));


        /*

        allVotesAnswerDTO.setProtocolNumber(getActualCountProtocol());
        allVotesAnswerDTO.setAllVotersNumber(getAllCountProtocols());
        allVotesAnswerDTO.setVotersVoteNumber(getActualCountVote());
        allVotesAnswerDTO.setAllVotersNumber(getAllEntitledToVote());
        allVotesAnswerDTO.setVoteCommittees(new ArrayList<>());
        List<ElectionCommitteeDistrict> electionCommitteeDistrictAll =  electionCommitteeDistrictRepository.findAll();



        for (ElectionCommitteeDistrict electionCommiteeInDistrinct : electionCommitteeDistrictAll) {
            Integer voteSum = 0;

            Collection<ElectionCommitteeVote> voteCollection = electionCommiteeInDistrinct.getElectionCommitteeVoteCollection();

            for (ElectionCommitteeVote ecv : voteCollection
                    ) {
                voteSum += ecv.getVoteNumber();
            }

            allVoteCommittee = new AllVoteCommitteeDTO();
            String name = electionCommiteeInDistrinct.getElectionCommitteeId().getName();
            Integer listNumber = electionCommiteeInDistrinct.getListNumber();

            allVoteCommittee.setName(name);
            allVoteCommittee.setNumber(listNumber);
            allVoteCommittee.setVotes(voteSum);

            allVotesAnswerDTO.getVoteCommittees().add(allVoteCommittee);
        }
        */

        return allVotesAnswerDTO;
    }


    private long getActualCountProtocol() {
        return  protocolRepository.count();
    }

    private long getAllCountProtocols() {
        return  peripheralCommitteeRepository.count();
    }

    private long getActualCountVote() {
        Optional<Long> votersVoteNumber = protocolRepository.getVotersVoteNumber();
        if (votersVoteNumber.isPresent())
            return votersVoteNumber.get();
        return 0;
    }

    private long getAllEntitledToVote() {
        //tutaj zmiana, trzeba zliczyc liczbe uprawnionych z tabeli PERIPHERAL_COMMITTEE.allowed_to_vote
        Optional<Long> allEntitledToVote = protocolRepository.getAllEntitledToVote();
        if (allEntitledToVote.isPresent())
            return allEntitledToVote.get();
        return 0;
    }
}