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
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Component
@Transactional
public class AllVotesAnswerDTOService {

    @Qualifier("protocolRepository")
    @Inject
    private ProtocolRepository protocolRepository;

    @Qualifier("peripheralCommitteeRepository")
    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;


    @Qualifier("electionCommitteeRepository")
    @Inject
    private ElectionCommitteeRepository electionCommitteeRepository;

    @Qualifier("electionCommitteeDistrictRepository")
    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    public AllVotesAnswerDTOService() {
    }

    public AllVotesAnswerDTO getAllVotes() {
        AllVotesAnswerDTO allVotesAnswerDTO = new AllVotesAnswerDTO();
        List<ElectionCommitteeDistrict> electionCommitteeDistrictAll = electionCommitteeDistrictRepository.findAll();

        Map<ElectionCommittee, Integer> votesForElectionCommittees = new HashMap<>();

        Integer totalVotes = 0;
        for (ElectionCommitteeDistrict electionCommiteeInDistrinct : electionCommitteeDistrictAll) {

            ElectionCommittee electionCommittee = electionCommiteeInDistrinct.getElectionCommitteeId();

            if (!votesForElectionCommittees.containsKey(electionCommittee)) {
                votesForElectionCommittees.put(electionCommittee, 0);
            }

            Collection<ElectionCommitteeVote> voteCollection = electionCommiteeInDistrinct.getElectionCommitteeVoteCollection();

            int electionCommitteeVotes = votesForElectionCommittees.get(electionCommittee);
            if (voteCollection.size() > 0) {
                for (ElectionCommitteeVote ecv : voteCollection) {
                    electionCommitteeVotes += ecv.getVoteNumber();
                    totalVotes += ecv.getVoteNumber();
                }
                votesForElectionCommittees.put(electionCommittee, electionCommitteeVotes);
            }
        }

        for (ElectionCommittee electionCommittee : votesForElectionCommittees.keySet()) {
            AllVoteCommitteeDTO allVoteCommittee = new AllVoteCommitteeDTO();
            String name = electionCommittee.getName();

            int electionCommitteeVotes = votesForElectionCommittees.get(electionCommittee);

            allVoteCommittee.setName(name);
            allVoteCommittee.setNumber(electionCommittee.getElectionCommitteeId());
            allVoteCommittee.setVotes(electionCommitteeVotes);

            allVotesAnswerDTO.getVoteCommittees().add(allVoteCommittee);
        }

        int totalperipheralCommittees = peripheralCommitteeRepository.findAll().size();
        int totalProtocolsFromPeripheralCommittees = protocolRepository.findAll().size();

        allVotesAnswerDTO.setAllVotersNumber(totalVotes);
        allVotesAnswerDTO.setVotersVoteNumber(totalVotes);

        allVotesAnswerDTO.setProtocolAllNumber(totalperipheralCommittees);
        allVotesAnswerDTO.setProtocolNumber(totalProtocolsFromPeripheralCommittees);

        return allVotesAnswerDTO;
    }

    private long getActualCountProtocol() {
        return protocolRepository.count();
    }

    private long getAllCountProtocols() {
        return peripheralCommitteeRepository.count();
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