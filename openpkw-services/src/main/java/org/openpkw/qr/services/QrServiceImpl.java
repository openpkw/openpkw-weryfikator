package org.openpkw.qr.services;

import org.openpkw.model.entity.*;
import org.openpkw.qr.dto.CandidateVoteDTO;
import org.openpkw.qr.dto.QrDTO;
import org.openpkw.qr.dto.QrResultDTO;
import org.openpkw.qr.parser.QrParserService;
import org.openpkw.qr.parser.QrWrapper;
import org.openpkw.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Service using for save data from QR
 * @author Sebastian Pogorzelski
 */
@Service
public class QrServiceImpl implements QrService {

    @Inject
    private QrParserService parserService;

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

    private final static Logger LOGGER = LoggerFactory.getLogger(QrServiceImpl.class);

    @Override
    public QrResultDTO saveResult(QrDTO qrDTO) {

        //TODO add necessary validations
        if (qrDTO.getQr() == null) {
            throw new NullPointerException();
        }

        QrWrapper qrWrapper = parserService.parse(qrDTO.getQr());

        Protocol protocol = createAndSaveProtocol(qrWrapper);

        countAndSaveVotes(qrWrapper, protocol);


        return new QrResultDTO();
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
