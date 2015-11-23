package org.openpkw.qr.parser;

import org.openpkw.qr.dto.CandidateVoteDTO;

import java.util.ArrayList;
import java.util.List;

import static org.openpkw.qr.parser.QrIndex.*;

/**
 * Wrapper for QR string. Split qr to String array
 * @author Sebastian Pogorzelski
 */
public class QrWrapper {
    private final String[] tokens;

    public QrWrapper(String code) {
        this.tokens = code.split(",");
    }


    public String getTerritorialCode() {
        return tokens[TERRITORIAL_CODE.getIndex()];
    }

    public String getPeripheryNumber() { return tokens[PERIPHERY_NUMBER.getIndex()]; }

    public String getVotingCardsTotalEntitledToVote() { return tokens[VOTINGCARDS_TOTALENTITLEDTOVOTE.getIndex()]; }

    public String getVotingCardsTotalCards() { return tokens[VOTINGCARDS_TOTALCARDS.getIndex()]; }

    public String getVotingCardsUnusedCards() { return tokens[VOTINGCARDS_UNUSEDCARDS.getIndex()]; }

    public String getVotingCardsRegularVoters() { return tokens[VOTINGCARDS_REGULARVOTERS.getIndex()]; }

    public String getVotingCardsRepresentativeVoters() { return tokens[VOTINGCARDS_REPRESENTATIVEVOTERS.getIndex()]; }

    public String getVotingCardsCertificateVoters() { return tokens[VOTINGCARDS_CERTIFICATEVOTERS.getIndex()]; }

    public String getVotingCardsFromBallotBox() { return tokens[VOTINGCARDS_CARDSFROMBALLOTBOX.getIndex()]; }

    public String getVotingCardsFromEnvelopes() { return tokens[VOTINGCARDS_CARDSFROMENVELOPES.getIndex()]; }

    public String getVotingCardsInvalidCards() { return tokens[VOTINGCARDS_INVALIDCARDS.getIndex()]; }

    public String getVotingCardsValidCards() { return tokens[VOTINGCARDS_VALIDCARDS.getIndex()]; }

    public String getVotingCardsInvalidVotes() { return tokens[VOTINGCARDS_INVALIDVOTES.getIndex()]; }

    public String getVotingCardsValidVotes() { return tokens[VOTINGCARDS_VALIDVOTES.getIndex()]; }

    public String getCorrespondenceVotingIssuedPackages() { return tokens[CORRESPONDENCEVOTING_ISSUEDPACKAGES.getIndex()]; }

    public String getCorrespondenceVotingReceivedReplyEnvelopes() { return tokens[CORRESPONDENCEVOTING_RECEIVEDREPLYENVELOPES.getIndex()]; }

    public String getCorrespondenceVotingMissingStatement() { return tokens[CORRESPONDENCEVOTING_MISSINGSTATEMENT.getIndex()]; }

    public String getCorrespondenceVotingMissingSignatureOnStatement() { return tokens[CORRESPONDENCEVOTING_MISSINGSIGNATUREONSTATEMENT.getIndex()]; }

    public String getCorrespondenceVotingMissingEnvelopeForVotingCard() { return tokens[CORRESPONDENCEVOTING_MISSINGENVELOPEFORVOTINGCARD.getIndex()]; }

    public String getCorrespondenceVotingUnsealedEnvelope() { return tokens[CORRESPONDENCEVOTING_UNSEALEDENVELOPE.getIndex()]; }

    public String getCorrespondenceVotingEnvelopesThrownToBallotBox() { return tokens[CORRESPONDENCEVOTING_ENVELOPESTHROWNTOBALLOTBOX.getIndex()]; }


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
