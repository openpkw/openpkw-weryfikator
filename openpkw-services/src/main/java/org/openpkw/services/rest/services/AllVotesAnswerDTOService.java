package org.openpkw.services.rest.services;

import org.openpkw.model.entity.ElectionCommittee;
import org.openpkw.model.entity.ElectionCommitteeDistrict;
import org.openpkw.model.entity.ElectionCommitteeVote;
import org.openpkw.repositories.*;
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

    @Inject
    private VoteRepository voteRepository;

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

        long totalNumberOfPeripheralCommittees = peripheralCommitteeRepository.getTotalNumberOfPeripheralCommittees();
        long totalNumberOfProtocolsFromPeripheralCommittees = protocolRepository.getTotalCount();

        allVotesAnswerDTO.setAllVotersNumber(voteRepository.getTotalNumberOfAllowedToVote().orElse(0L));
        allVotesAnswerDTO.setVotersVoteNumber(voteRepository.getTotalNumberOfActualVoters().orElse(0L));

        allVotesAnswerDTO.setProtocolAllNumber(totalNumberOfPeripheralCommittees);
        allVotesAnswerDTO.setProtocolNumber(totalNumberOfProtocolsFromPeripheralCommittees);

        return allVotesAnswerDTO;
    }
}