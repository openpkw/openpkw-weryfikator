package org.openpkw.services.rest.services;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.*;
import org.openpkw.services.rest.dto.CandidateDTO;
import org.openpkw.services.rest.dto.VoteCommitteeDTO;
import org.openpkw.services.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Component
public class VotesAnswerDTOinDistrictService {

    @Qualifier("districtCommitteeRepository")
    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;

    @Qualifier("protocolRepository")
    @Inject
    private ProtocolRepository protocolRepository;

    @Qualifier("electionCommitteeDistrictRepository")
    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    @Qualifier("candidateRepository")
    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private VoteRepository voteRepository;

    @Transactional
    public VotesAnswerDTO getVotes(int districtCommitteeNumber) {
        VotesAnswerDTO result = new VotesAnswerDTO();

        Optional<DistrictCommittee> district = districtCommitteeRepository.findByDistrictCommitteeNumber(districtCommitteeNumber);
        if (district.isPresent()) {
            DistrictCommittee districtCommittee = district.get();

            long totalNumberOfProtocols = districtCommittee.getPeripheralCommitteeCollection().size();
            long actualNumberOfProtocols = protocolRepository.getCountByDistrictCommittee(districtCommittee);
            Optional<Long> totalNumberOfVoters = voteRepository.getNumberOfAllowedToVoteByDistrictCommittee(districtCommittee);
            Optional<Long> actualNumberOfVoters = voteRepository.getNumberOfActualVotersByDistrictCommittee(districtCommittee);

            List<VoteCommitteeDTO> listVoteCommitteeDTO = getListVoteCommittee(districtCommitteeNumber);

            if (totalNumberOfVoters.isPresent()) {
                result.setAllVotersNumber(totalNumberOfVoters.get());
            }
            if (actualNumberOfVoters.isPresent()) {
                result.setVotersVoteNumber(actualNumberOfVoters.get());
            }
            result.setProtocolAllNumber(totalNumberOfProtocols);
            result.setProtocolNumber(actualNumberOfProtocols);
            result.setAllVoteCommittees(listVoteCommitteeDTO);
        }
        return result;
    }

    private List<VoteCommitteeDTO> getListVoteCommittee(int districtCommitteeNumber) {
        List<VoteCommitteeDTO> voteCommitteeDTOs = new ArrayList<>();
        VoteCommitteeDTO voteCommitteeDTO;

        Optional<DistrictCommittee> districtCommitee = districtCommitteeRepository.findByDistrictCommitteeNumber(districtCommitteeNumber);
        if (districtCommitee.isPresent()) {
            List<ElectionCommitteeDistrict> electionCommitteeDistricts = electionCommitteeDistrictRepository.findByDistrictCommittee(districtCommitee.get());

            for (ElectionCommitteeDistrict committeeDistrict : electionCommitteeDistricts) {
                voteCommitteeDTO = new VoteCommitteeDTO();

                voteCommitteeDTO.setName(committeeDistrict.getElectionCommitteeId().getName());
                voteCommitteeDTO.setNumber(committeeDistrict.getListNumber());

                voteCommitteeDTO.setVotes(getVotesElectionCommittee(committeeDistrict));

                voteCommitteeDTO.setCandidates(getCandidates(committeeDistrict));

                voteCommitteeDTOs.add(voteCommitteeDTO);
            }
        }

        return voteCommitteeDTOs;
    }

    private List<CandidateDTO> getCandidates(ElectionCommitteeDistrict committeeDistrict) {
        List<CandidateDTO> candidateDTOs = new ArrayList<>();

        List<Candidate> candidates = candidateRepository.findByElectionCommitteeDistrict(
                committeeDistrict);

        for (Candidate candidate : candidates) {
            CandidateDTO candidateDTO = new CandidateDTO();
            candidateDTO.setNames(candidate.getName());
            candidateDTO.setSurname(candidate.getSurname());
            candidateDTO.setNumber(candidate.getPositionOnList());

            int candidateVotes = 0;
            Collection<Vote> voteList = candidate.getVotes();
            for (Vote vote : voteList) {
                candidateVotes = +vote.getCandidatesVotesNumber();
            }
            candidateDTO.setVotes(candidateVotes);
            candidateDTOs.add(candidateDTO);
        }

        return candidateDTOs;
    }

    private int getVotesElectionCommittee(ElectionCommitteeDistrict committeeDistrict) {
        int electionCommitteeVote = 0;

        Collection<ElectionCommitteeVote> voteCollection = committeeDistrict.getElectionCommitteeVoteCollection();

        for (ElectionCommitteeVote vote : voteCollection) {
            electionCommitteeVote += vote.getVoteNumber();
        }

        return electionCommitteeVote;
    }
}