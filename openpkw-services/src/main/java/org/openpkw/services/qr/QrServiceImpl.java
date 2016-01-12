package org.openpkw.services.qr;

import org.openpkw.model.entity.*;
import org.openpkw.services.qr.dto.CandidateVoteDTO;
import org.openpkw.services.qr.dto.QrDTO;
import org.openpkw.services.qr.dto.QrResultDTO;
import org.openpkw.services.qr.parser.QrParserServiceImpl;
import org.openpkw.services.qr.parser.QrWrapper;
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
    private QrParserServiceImpl parserService;

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

        PeripheralCommittee peripheralCommittee = findPeripheralCommittee(qrWrapper.getPeripheryNumber(), qrWrapper.getTerritorialCode(), qrWrapper.getDistrictNumber());
        Protocol protocol = createAndSaveProtocol(qrWrapper, peripheralCommittee);


        countAndSaveVotes(qrWrapper, peripheralCommittee, protocol);


        return new QrResultDTO();
    }

    private void countAndSaveVotes(QrWrapper qrWrapper, PeripheralCommittee peripheralCommittee, Protocol protocol) {
        List<CandidateVoteDTO> candidateVoteDTOs = qrWrapper.getCandidates();
        Map<Integer, ElectionCommitteeVote> electionCommitteeVotes = new HashMap<>();

        for (CandidateVoteDTO candidateVoteDTO : candidateVoteDTOs) {
            ElectionCommitteeDistrict electionCommitteeDistrict = findCommiteeDistrict(peripheralCommittee.getDistrictCommittee(), candidateVoteDTO.getListNumber());

            createAndSaveVote(protocol, candidateVoteDTO, electionCommitteeDistrict);

            addElectionCommitteeVote(electionCommitteeVotes, electionCommitteeDistrict, protocol, candidateVoteDTO);
        }

        for (ElectionCommitteeVote electionCommitteeVote : electionCommitteeVotes.values()) {
            electionCommitteeVoteRepository.save(electionCommitteeVote);
        }
    }

    private void createAndSaveVote(Protocol protocol, CandidateVoteDTO candidateVoteDTO, ElectionCommitteeDistrict electionCommitteeDistrict) {
        Vote vote = new Vote();
        vote.setProtocol(protocol);
        vote.setCandidate(findCandidate(candidateVoteDTO.getPositionOnList(), electionCommitteeDistrict));
        vote.setCandidatesVotesNumber(candidateVoteDTO.getVotesNumber());
        voteRepository.save(vote);
    }

    private void addElectionCommitteeVote(Map<Integer, ElectionCommitteeVote> electionCommitteeVotes, ElectionCommitteeDistrict electionCommitteeDistrict, Protocol protocol, CandidateVoteDTO candidateVoteDTO) {
        if (!electionCommitteeVotes.containsKey(electionCommitteeDistrict.getElectionCommitteeDistrictId())) {

            ElectionCommitteeVote electionCommitteeVote = new ElectionCommitteeVote();
            electionCommitteeVote.setProtocol(protocol);
            electionCommitteeVote.setElectionCommitteeDistrict(electionCommitteeDistrict);
            electionCommitteeVote.setVoteNumber(candidateVoteDTO.getVotesNumber());
            electionCommitteeVotes.put(electionCommitteeDistrict.getElectionCommitteeDistrictId(), electionCommitteeVote);
        } else {
            ElectionCommitteeVote electionCommitteeVote = electionCommitteeVotes.get(electionCommitteeDistrict.getElectionCommitteeDistrictId());
            Integer currentVotes = electionCommitteeVote.getVoteNumber();
            electionCommitteeVote.setVoteNumber(currentVotes + candidateVoteDTO.getVotesNumber());
        }

    }

    private ElectionCommitteeDistrict findCommiteeDistrict(DistrictCommittee districtCommittee, Integer listNumber) {
        Optional<ElectionCommitteeDistrict> electionCommitteeDistrict = electionCommitteeDistrictRepository.findByDistrictCommitteeAndListNumber(districtCommittee, listNumber);
        if (electionCommitteeDistrict.isPresent()) {
            return electionCommitteeDistrict.get();
        }
        throw new IllegalArgumentException("Candidate not found");
    }



    private Candidate findCandidate(Integer positionOnList, ElectionCommitteeDistrict committeeDistrict) {
        Optional<Candidate> candidate = candidateRepository.findByPositionOnListAndElectionCommitteeDistrict(positionOnList, committeeDistrict);
        if (candidate.isPresent()) {
            return candidate.get();
        }
        throw new IllegalArgumentException("Candidate not found");
    }

    private Protocol createAndSaveProtocol(QrWrapper qrWrapper, PeripheralCommittee peripheralCommittee) {

        Protocol protocol = new Protocol();
        protocol.setReceivedDate(new Date());
        protocol.setPeripheralCommittee(peripheralCommittee);

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

    private PeripheralCommittee findPeripheralCommittee(String peripheryNumber, String territorialCode, String districtNumber) {
        Optional<PeripheralCommittee> committee = peripheralCommitteeRepository.findByPeripheralCommitteeNumberAndTerritorialCodeAndDistrictCommittee_districtCommitteeNumber(Integer.parseInt(peripheryNumber),
                                                                                                                                    territorialCode, Integer.parseInt(districtNumber));
        if (committee.isPresent()) {
            return committee.get();
        }
        throw new IllegalArgumentException("PeripheralCommittee not found");
    }

}
