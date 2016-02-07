package org.openpkw.rest.services;

import org.openpkw.model.entity.*;
import org.openpkw.repositories.CandidateRepository;
import org.openpkw.repositories.DistrictCommitteeRepository;
import org.openpkw.repositories.ElectionCommitteeDistrictRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.rest.dto.CandidateDTO;
import org.openpkw.rest.dto.VoteCommitteeDTO;
import org.openpkw.rest.dto.VotesAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kamil on 07.02.16.
 */
@Component
public class VotesAnswerDTOinDistrictService {

    @Autowired
    DistrictCommitteeRepository districtCommittee;

    @Autowired
    ProtocolRepository protocol;

    @Autowired
    ElectionCommitteeDistrictRepository electionCommitteeDistrict;

    @Autowired
    CandidateRepository candidate;

    public VotesAnswerDTO generate(int districtCommitteeNumber) {
        int protocolNumber;             //liczba przsłanych protokołów
        int protocolAllNumber;          //liczba oczekiwana
        int votersVoteNumber;           //liczba oddanych glosow
        int allVotersNumber;            //liczba wszystich uprawnionych do glosowania
        List<VoteCommitteeDTO> listVoteCommitteeDTO;

        DistrictCommittee district = districtCommittee.findByDistrictCommitteeNumber(districtCommitteeNumber);

        Collection<PeripheralCommittee> peripherals = district.getPeripheralCommitteeCollection();
        protocolAllNumber = getProtocolAllNumber(peripherals);

        allVotersNumber = getAllAllowedToVoteBy(peripherals);

        protocolNumber = getProtocolCountBy(peripherals);

        votersVoteNumber = getAllVotersVoteBy(peripherals);

        listVoteCommitteeDTO = getListVoteCommittee(districtCommitteeNumber);

        VotesAnswerDTO votesAnswerDTO = new VotesAnswerDTO();
        votesAnswerDTO.setAllVotersNumber(allVotersNumber);
        votesAnswerDTO.setVotersVoteNumber(votersVoteNumber);
        votesAnswerDTO.setProtocolAllNumber(protocolAllNumber);
        votesAnswerDTO.setProtocolNumber(protocolNumber);
        votesAnswerDTO.setAllVoteCommittees(listVoteCommitteeDTO);

        return votesAnswerDTO;
    }

    private List<VoteCommitteeDTO> getListVoteCommittee(int districtCommitteeNumber) {
        List<VoteCommitteeDTO> voteCommitteeDTOs = new ArrayList<>();
        VoteCommitteeDTO voteCommitteeDTO;

        List<ElectionCommitteeDistrict> electionCommitteeDistricts =
                electionCommitteeDistrict.findByDistrictCommitteeNumber(districtCommitteeNumber);

        for (ElectionCommitteeDistrict committeeDistrict : electionCommitteeDistricts) {
            voteCommitteeDTO = new VoteCommitteeDTO();

            voteCommitteeDTO.setName(committeeDistrict.getElectionCommittee().getName());
            voteCommitteeDTO.setNumber(committeeDistrict.getListNumber());

            voteCommitteeDTO.setVotes(getVotesElectionCommittee(committeeDistrict));

            voteCommitteeDTO.setCandidates(getCandidates(committeeDistrict));

            voteCommitteeDTOs.add(voteCommitteeDTO);
        }


        return voteCommitteeDTOs;
    }

    private List<CandidateDTO> getCandidates(ElectionCommitteeDistrict committeeDistrict) {
        List<CandidateDTO> candidateDTOs = new ArrayList<>();

        List<Candidate> candidates = candidate.findByElectionCommitteeDistrict(
                committeeDistrict.getElectionCommitteeDistrictId());

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

    private int getAllVotersVoteBy(Collection<PeripheralCommittee> peripherals) {
        int votersVoteCount = 0;

        for (PeripheralCommittee peripheral : peripherals) {
            List<Protocol> protocolList = protocol.findByPeripheralCommittee(
                    peripheral.getPeripheralCommitteeNumber());
            for (Protocol protocol : protocolList) {
                votersVoteCount += Integer.parseInt(protocol.getValidVotes());
            }
        }

        return votersVoteCount;
    }

    private int getProtocolCountBy(Collection<PeripheralCommittee> peripherals) {
        int protocolCount = 0;

        for (PeripheralCommittee peripheral : peripherals) {
            List<Protocol> protocolList = protocol.findByPeripheralCommittee(
                    peripheral.getPeripheralCommitteeNumber());
            protocolCount += protocolList.size();
        }

        return protocolCount;
    }

    private int getAllAllowedToVoteBy(Collection<PeripheralCommittee> peripherals) {
        int allAllowedToVote = 0;

        for (PeripheralCommittee peripheral : peripherals) {
            allAllowedToVote += peripheral.getAllowedToVote();
        }

        return allAllowedToVote;
    }

    private int getProtocolAllNumber(Collection<PeripheralCommittee> peripherals) {
        return peripherals.size();
    }
}
