package org.openpkw.qr.parser;

import org.openpkw.qr.dto.CandidateVoteDTO;

import java.util.ArrayList;
import java.util.List;

import static org.openpkw.qr.parser.QrIndex.*;

/**
 * Wrapper for QR string. Split qr to String array
 * @author Sebastian Pogorzelski
 */
public class DefaultQrWrapper implements QrWrapper {
    private final String[] tokens;

    public DefaultQrWrapper(String code) {
        this.tokens = code.split(",");
    }


    @Override
    public String getTerritorialCode() {
        return tokens[TERRITORIAL_CODE.getIndex()];
    }

    @Override
    public String getPeripheryNumber() { return tokens[PERIPHERY_NUMBER.getIndex()]; }

    @Override
    public String getVotingCardsTotalEntitledToVote() { return tokens[VOTINGCARDS_TOTALENTITLEDTOVOTE.getIndex()]; }

    @Override
    public String getVotingCardsTotalCards() { return tokens[VOTINGCARDS_TOTALCARDS.getIndex()]; }

    @Override
    public String getVotingCardsUnusedCards() { return tokens[VOTINGCARDS_UNUSEDCARDS.getIndex()]; }

    @Override
    public String getVotingCardsRegularVoters() { return tokens[VOTINGCARDS_REGULARVOTERS.getIndex()]; }

    @Override
    public String getVotingCardsRepresentativeVoters() { return tokens[VOTINGCARDS_REPRESENTATIVEVOTERS.getIndex()]; }

    @Override
    public String getVotingCardsCertificateVoters() { return tokens[VOTINGCARDS_CERTIFICATEVOTERS.getIndex()]; }

    @Override
    public String getVotingCardsFromBallotBox() { return tokens[VOTINGCARDS_CARDSFROMBALLOTBOX.getIndex()]; }

    @Override
    public String getVotingCardsFromEnvelopes() { return tokens[VOTINGCARDS_CARDSFROMENVELOPES.getIndex()]; }

    @Override
    public String getVotingCardsInvalidCards() { return tokens[VOTINGCARDS_INVALIDCARDS.getIndex()]; }

    @Override
    public String getVotingCardsValidCards() { return tokens[VOTINGCARDS_VALIDCARDS.getIndex()]; }

    @Override
    public String getVotingCardsInvalidVotes() { return tokens[VOTINGCARDS_INVALIDVOTES.getIndex()]; }

    @Override
    public String getVotingCardsValidVotes() { return tokens[VOTINGCARDS_VALIDVOTES.getIndex()]; }

    @Override
    public String getCorrespondenceVotingIssuedPackages() { return tokens[CORRESPONDENCEVOTING_ISSUEDPACKAGES.getIndex()]; }

    @Override
    public String getCorrespondenceVotingReceivedReplyEnvelopes() { return tokens[CORRESPONDENCEVOTING_RECEIVEDREPLYENVELOPES.getIndex()]; }

    @Override
    public String getCorrespondenceVotingMissingStatement() { return tokens[CORRESPONDENCEVOTING_MISSINGSTATEMENT.getIndex()]; }

    @Override
    public String getCorrespondenceVotingMissingSignatureOnStatement() { return tokens[CORRESPONDENCEVOTING_MISSINGSIGNATUREONSTATEMENT.getIndex()]; }

    @Override
    public String getCorrespondenceVotingMissingEnvelopeForVotingCard() { return tokens[CORRESPONDENCEVOTING_MISSINGENVELOPEFORVOTINGCARD.getIndex()]; }

    @Override
    public String getCorrespondenceVotingUnsealedEnvelope() { return tokens[CORRESPONDENCEVOTING_UNSEALEDENVELOPE.getIndex()]; }

    @Override
    public String getCorrespondenceVotingEnvelopesThrownToBallotBox() { return tokens[CORRESPONDENCEVOTING_ENVELOPESTHROWNTOBALLOTBOX.getIndex()]; }


    @Override
    public List<CandidateVoteDTO> getCandidates() {
        List<CandidateVoteDTO> candidates = new ArrayList<>();
        for (int i = QrIndex.CANDIDATES_START.getIndex(); i < tokens.length; i++) {
            String[] candidateData = tokens[i].split(";");
            Integer listNumber = Integer.parseInt(candidateData[0].substring(0, 2));
            Integer positionOnList = Integer.parseInt(candidateData[0].substring(2, 4));
            candidates.add(new CandidateVoteDTO(listNumber, positionOnList, Integer.valueOf(candidateData[1])));
        }
        return candidates;
    }
}
