package org.openpkw.services.rest.services;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.*;
import org.openpkw.services.rest.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Service
@Transactional
public class VotesService {

    @Inject
    private ProtocolRepository protocolRepository;

    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    @Inject
    private ElectionCommitteeRepository electionCommitteeRepository;

    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    @Inject
    private VoteRepository voteRepository;

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;

    @Inject
    private ElectionCommitteeVoteRepository electionCommitteeVoteRepository;

    public VotesService() {
    }

    public AllVotesAnswerDTO getAllVotes() {
        AllVotesAnswerDTO allVotesAnswerDTO = new AllVotesAnswerDTO();
        List<ElectionCommitteeDistrict> electionCommitteeDistrictAll = electionCommitteeDistrictRepository.findAll();

        Map<ElectionCommittee, Integer> votesForElectionCommittees = new HashMap<>();

        Integer totalVotes = 0;
        for (ElectionCommitteeDistrict electionCommiteeInDistrinct : electionCommitteeDistrictAll) {

            ElectionCommittee electionCommittee = electionCommiteeInDistrinct.getElectionCommittee();

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
            String longName = electionCommittee.getLongName();
            String shortName = electionCommittee.getShortName();
            String symbol = electionCommittee.getSymbol();

            int electionCommitteeVotes = votesForElectionCommittees.get(electionCommittee);

            allVoteCommittee.setLongName(longName);
            allVoteCommittee.setShortName(shortName);
            allVoteCommittee.setSymbol(symbol);
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

    @Transactional
    public VotesAnswerDTO getVotesInDistrict(int districtCommitteeNumber) {
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

    public VotesAnswerDTO getVotesInPeripheral(int districtCommitteeNumber, String teritorialCode, int peripheralCommitteeNumber) {
        VotesAnswerDTO answerDTO = new VotesAnswerDTO();
        PeripheralCommittee committee;

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
                    voteCommitteeDTO.setLongName(electionCommitteeDistrict.getElectionCommittee().getLongName());
                    voteCommitteeDTO.setShortName(electionCommitteeDistrict.getElectionCommittee().getShortName());
                    voteCommitteeDTO.setSymbol(electionCommitteeDistrict.getElectionCommittee().getSymbol());

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

    private List<VoteCommitteeDTO> getListVoteCommittee(int districtCommitteeNumber) {
        List<VoteCommitteeDTO> voteCommitteeDTOs = new ArrayList<>();
        VoteCommitteeDTO voteCommitteeDTO;

        Optional<DistrictCommittee> districtCommitee = districtCommitteeRepository.findByDistrictCommitteeNumber(districtCommitteeNumber);
        if (districtCommitee.isPresent()) {
            List<ElectionCommitteeDistrict> electionCommitteeDistricts = electionCommitteeDistrictRepository.findByDistrictCommittee(districtCommitee.get());

            for (ElectionCommitteeDistrict committeeDistrict : electionCommitteeDistricts) {
                voteCommitteeDTO = new VoteCommitteeDTO();

                voteCommitteeDTO.setLongName(committeeDistrict.getElectionCommittee().getLongName());
                voteCommitteeDTO.setShortName(committeeDistrict.getElectionCommittee().getShortName());
                voteCommitteeDTO.setSymbol(committeeDistrict.getElectionCommittee().getSymbol());
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