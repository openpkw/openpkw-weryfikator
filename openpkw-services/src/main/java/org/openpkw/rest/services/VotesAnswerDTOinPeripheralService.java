package org.openpkw.rest.services;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.*;
import org.openpkw.rest.dto.CandidateDTO;
import org.openpkw.rest.dto.VoteCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by kamil on 08.02.16.
 */
@Component
public class VotesAnswerDTOinPeripheralService {


    @Qualifier("districtCommitteeRepository")
    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;


    @Qualifier("peripheralCommitteeRepository")
    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    @Qualifier("protocolRepository")
    @Inject
    private ProtocolRepository protocolRepository;

    @Qualifier("electionCommitteeVoteRepository")
    @Inject
    private ElectionCommitteeVoteRepository electionCommitteeVoteRepository;

    @Qualifier("voteRepository")
    @Inject
    private VoteRepository voteRepository;

    public VotesAnswerDTO generate(
            int districtCommitteeNumber,
            String teritorialCode,
            int peripheralCommitteeNumber
    ) {
        VotesAnswerDTO answerDTO = new VotesAnswerDTO();
        PeripheralCommittee committee;

        //Optional<PeripheralCommittee> found = peripheralCommitteeRepository.findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(
        //        peripheralCommitteeNumber, teritorialCode, districtCommitteeNumber);

        Optional<DistrictCommittee> districtCommittee = districtCommitteeRepository.findByDistrictCommitteeNumber(districtCommitteeNumber);
        if (districtCommittee.isPresent()) {
            Optional<PeripheralCommittee> found = peripheralCommitteeRepository.findByDistrictCommitteeAndPeripheralCodeAndPeripheralCommitteeNumber(districtCommittee.get(), teritorialCode, peripheralCommitteeNumber);

            if (found.isPresent()) {
                committee = found.get();

                answerDTO.setProtocolAllNumber(1);
                answerDTO.setAllVotersNumber(committee.getAllowedToVote());

                List<Protocol> protocolList = protocolRepository.findByPeripheralCommittee(committee);
                Protocol protocol = protocolList.get(1);

                List<ElectionCommitteeVote> electionCommitteeList = electionCommitteeVoteRepository.findByProtocol(protocol);

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

                        Vote voteOnCandidate = voteRepository.findByProtocolAndCandidate(protocol, candidate);
                        candidateDTO.setVotes(voteOnCandidate.getCandidatesVotesNumber());

                        voteCommitteeDTO.getCandidates().add(candidateDTO);
                    }

                    answerDTO.getAllVoteCommittees().add(voteCommitteeDTO);
                }
            }
        }
        return answerDTO;
    }

}
