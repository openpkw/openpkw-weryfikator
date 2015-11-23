package org.openpkw.web.controllers;

import org.openpkw.model.entity.*;
import org.openpkw.qr.dto.CandidateVoteDTO;
import org.openpkw.qr.dto.QrDTO;
import org.openpkw.qr.parser.QrWrapper;
import org.openpkw.qualifier.OpenPKWAPIController;
import org.openpkw.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * QR Controller responsible for
 * Created by Sebastian on 06.11.2015.
 */
@OpenPKWAPIController
@RequestMapping("/api")
public class QrResultController {

    @Inject
    private ProtocolRepository protocolRepository;

    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private VoteRepository voteRepository;

    @Inject
    private ElectionCommitteeVoteRepository electionCommitteeVoteRepository;

    @Inject
    private ElectionCommitteeDistrictRepository electionCommitteeDistrictRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(QrResultController.class);

    @RequestMapping(value = "/qr", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> saveResult(@RequestBody QrDTO result) {


        //TODO add necessary validations
        if (result.getQr() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        QrWrapper qrWrapper = new QrWrapper(result.getQr());

        try {

            Protocol protocol = createAndSaveProtocol(qrWrapper);

            countAndSaveVotes(qrWrapper, protocol);

            LOGGER.debug("Qr saved");
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Can't save result", ex);
            return new ResponseEntity<>("Can't save result", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Qr saved", HttpStatus.OK);
    }

    private void countAndSaveVotes(QrWrapper qrWrapper, Protocol protocol) {
        List<CandidateVoteDTO> candidateVoteDTOs = qrWrapper.getCandidates();
        Map<Integer, Integer> electionCommitteeVotesCounter = new HashMap<>();

        for (CandidateVoteDTO candidateVoteDTO : candidateVoteDTOs) {
            Vote vote = new Vote();
            vote.setProtocolProtocolId(protocol);
            vote.setCandidateCandidateId(findCandidate(candidateVoteDTO.getListNumber(), candidateVoteDTO.getPositionOnList()));
            vote.setCandidatesVotesNumber(candidateVoteDTO.getVotesNumber());
            voteRepository.save(vote);

            addVotesToCounter(electionCommitteeVotesCounter, candidateVoteDTO);
        }

        for (Map.Entry<Integer, Integer> entry : electionCommitteeVotesCounter.entrySet()) {
            ElectionCommitteeVote electionCommitteeVote = new ElectionCommitteeVote();
            electionCommitteeVote.setProtocolProtocolId(protocol);
            electionCommitteeVote.setElectionCommitteeDistrictId(findCommiteeDistrict(entry.getKey()));
            electionCommitteeVote.setElectionCommitteeVoteNumber(entry.getValue());

            electionCommitteeVoteRepository.save(electionCommitteeVote);
        }
    }

    private ElectionCommitteeDistrict findCommiteeDistrict(Integer listNumber) {
        Optional<ElectionCommitteeDistrict> electionCommitteeDistrict = electionCommitteeDistrictRepository.findByListNumber(listNumber);
        if (electionCommitteeDistrict.isPresent()) {
            return electionCommitteeDistrict.get();
        }
        throw new IllegalArgumentException("Candidate not found");
    }

    private void addVotesToCounter(Map<Integer, Integer> electionCommitteeVotesCounter, CandidateVoteDTO candidateVoteDTO) {
        Integer votesNumber = candidateVoteDTO.getVotesNumber();
        if (electionCommitteeVotesCounter.containsKey(candidateVoteDTO.getListNumber())) {
            votesNumber = electionCommitteeVotesCounter.get(candidateVoteDTO.getListNumber()) + candidateVoteDTO.getVotesNumber();
        }
        electionCommitteeVotesCounter.put(candidateVoteDTO.getListNumber(), votesNumber);
    }

    private Candidate findCandidate(Integer listNumber, Integer positionOnList) {
        Optional<Candidate> candidate = candidateRepository.findByPositionOnListAndElectionCommitteeDistrictId_ListNumber(positionOnList, listNumber);
        if (candidate.isPresent()) {
            return candidate.get();
        }
        throw new IllegalArgumentException("Candidate not found");
    }

    private Protocol createAndSaveProtocol(QrWrapper qrWrapper) {

        Protocol protocol = new Protocol();
        protocol.setReceivedDate(new Date());
        protocol.setPeripheralCommittee(findPeripheralCommittee(qrWrapper.getPeripheryNumber(), qrWrapper.getTerritorialCode()));

        protocol.setTotalEntitledToVote(qrWrapper.getVotingCardsTotalEntitledToVote());
        protocol.setTotalCards( qrWrapper.getVotingCardsTotalCards());
        protocol.setUnusedCards( qrWrapper.getVotingCardsUnusedCards());
        protocol.setRegularVoters( qrWrapper.getVotingCardsRegularVoters());
        protocol.setRepresentativeVoters( qrWrapper.getVotingCardsRepresentativeVoters());
        protocol.setCertificateVoters( qrWrapper.getVotingCardsCertificateVoters());
        protocol.setFromBallotBox( qrWrapper.getVotingCardsFromBallotBox());
        protocol.setFromEnvelopes( qrWrapper.getVotingCardsFromBallotBox());
        protocol.setInvalidCards( qrWrapper.getVotingCardsInvalidCards());
        protocol.setValidCards( qrWrapper.getVotingCardsValidCards());
        protocol.setInvalidVotes( qrWrapper.getVotingCardsValidVotes());
        protocol.setValidVotes( qrWrapper.getVotingCardsInvalidVotes());
        protocol.setVotingIssuedPackages( qrWrapper.getCorrespondenceVotingIssuedPackages());
        protocol.setReceivedReplyEnvelopes( qrWrapper.getCorrespondenceVotingReceivedReplyEnvelopes());
        protocol.setMissingStatement( qrWrapper.getCorrespondenceVotingMissingStatement());
        protocol.setMissingSignatureOnStatement( qrWrapper.getCorrespondenceVotingMissingSignatureOnStatement());
        protocol.setMissingEnvelopeForVotingCard( qrWrapper.getCorrespondenceVotingMissingEnvelopeForVotingCard());
        protocol.setUnsealedEnvelope( qrWrapper.getCorrespondenceVotingUnsealedEnvelope());
        protocol.setEnvelopesThrownToBallotBox( qrWrapper.getCorrespondenceVotingEnvelopesThrownToBallotBox());

        protocolRepository.save(protocol);
        return protocol;
    }

    private PeripheralCommittee findPeripheralCommittee(String peripheryNumber, String territorialCode) {
        Optional<PeripheralCommittee> committee = peripheralCommitteeRepository.findByPeripheralCommitteeNumberAndTerritorialCode(Integer.parseInt(peripheryNumber), territorialCode);
        if (committee.isPresent()) {
            return committee.get();
        }
        throw new IllegalArgumentException("PeripheralCommittee not found");
    }


}
