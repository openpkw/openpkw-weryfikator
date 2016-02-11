package org.openpkw.services.qr.parser;

import org.openpkw.services.qr.dto.CandidateVoteDTO;

import java.util.List;

/**
 * Interface contains all methods to retrieve data from qr
 * @author Sebastian Pogorzelski
 */
public interface QrWrapper {

    String getTerritorialCode();

    String getPeripheryNumber();

    String getDistrictNumber();

    String getVotingCardsTotalEntitledToVote();

    String getVotingCardsTotalCards();

    String getVotingCardsUnusedCards();

    String getVotingCardsRegularVoters();

    String getVotingCardsRepresentativeVoters();

    String getVotingCardsCertificateVoters();

    String getVotingCardsFromBallotBox();

    String getVotingCardsFromEnvelopes();

    String getVotingCardsInvalidCards();

    String getVotingCardsValidCards();

    String getVotingCardsInvalidVotes();

    String getVotingCardsValidVotes();

    String getCorrespondenceVotingIssuedPackages();

    String getCorrespondenceVotingReceivedReplyEnvelopes();

    String getCorrespondenceVotingMissingStatement();

    String getCorrespondenceVotingMissingSignatureOnStatement();

    String getCorrespondenceVotingMissingEnvelopeForVotingCard();

    String getCorrespondenceVotingUnsealedEnvelope();

    String getCorrespondenceVotingEnvelopesThrownToBallotBox();

    List<CandidateVoteDTO> getCandidates();
}
