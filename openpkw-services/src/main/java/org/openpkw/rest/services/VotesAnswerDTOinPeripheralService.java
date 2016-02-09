package org.openpkw.rest.services;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.ElectionCommitteeVoteRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.repositories.VoteRepository;
import org.openpkw.rest.dto.CandidateDTO;
import org.openpkw.rest.dto.VoteCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by kamil on 08.02.16.
 */
@Component
public class VotesAnswerDTOinPeripheralService {

    @Qualifier("peripheralCommitteeRepository")
    @Autowired
    PeripheralCommitteeRepository peripheralCommittee;

    @Qualifier("protocolRepository")
    @Autowired
    ProtocolRepository protocol;

    @Autowired
    ElectionCommitteeVoteRepository voteRepository;

    @Autowired
    VoteRepository voteCandidate;

    public VotesAnswerDTO generate(
            int districtCommitteeNumber,
            String teritorialCode,
            int peripheralCommitteeNumber
    ) {
        VotesAnswerDTO answerDTO = new VotesAnswerDTO();
        PeripheralCommittee committee;

        Optional<PeripheralCommittee> found = peripheralCommittee.findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(
                peripheralCommitteeNumber, teritorialCode, districtCommitteeNumber);

        if (found.isPresent()) {
            committee = found.get();

            answerDTO.setProtocolAllNumber(1);
            answerDTO.setAllVotersNumber(committee.getAllowedToVote());

            List<Protocol> protocolList = protocol.findByPeripheralCommittee(committee.getPeripheralCommitteeNumber());
            Protocol protocol = protocolList.get(1);

            List<ElectionCommitteeVote> electionCommitteeList = voteRepository.findByProtocolId(protocol.getProtocolID());

            for (ElectionCommitteeVote vote : electionCommitteeList) {
                VoteCommitteeDTO voteCommitteeDTO = new VoteCommitteeDTO();
                voteCommitteeDTO.setVotes(vote.getVoteNumber());

                ElectionCommitteeDistrict electionCommitteeDistrict = vote.getElectionCommitteeDistrict();

                voteCommitteeDTO.setNumber(electionCommitteeDistrict.getListNumber());
                voteCommitteeDTO.setName(electionCommitteeDistrict.getElectionCommittee().getName());

                Collection<Candidate> candidateCollection = electionCommitteeDistrict.getCandidateCollection();

                for (Candidate candidate : candidateCollection) {
                    CandidateDTO candidateDTO = new CandidateDTO();
                    candidateDTO.setNames(candidate.getName());
                    candidateDTO.setSurname(candidate.getSurname());
                    candidateDTO.setNumber(candidate.getPositionOnList());

                    Vote voteOnCandidate = voteCandidate.findByProtocolAndCandidate(protocol.getProtocolID(), candidate.getId());
                    candidateDTO.setVotes(voteOnCandidate.getCandidatesVotesNumber());

                    voteCommitteeDTO.getCandidates().add(candidateDTO);
                }

                answerDTO.getAllVoteCommittees().add(voteCommitteeDTO);
            }
        }
        return answerDTO;
    }

}
