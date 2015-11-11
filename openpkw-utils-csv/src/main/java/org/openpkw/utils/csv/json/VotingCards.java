package org.openpkw.utils.csv.json;

public class VotingCards {

    private long totalEntitledToVote;
    private long totalCards;
    private long unusedCards;
    private long regularVoters;
    private long representativeVoters;
    private long certificateVoters;

    private long cardsFromBallotBox;
    private long cardsFromEnvelopes;
    private long invalidCards;
    private long validCards;
    private long invalidVotes;
    private long invalidVotesToManyTicks;
    private long invalidVotesNoTicks;
    private long invalidVotesInvalidCandidate;
    private long validVotes;

    public long getTotalEntitledToVote() {
        return totalEntitledToVote;
    }

    public void setTotalEntitledToVote(long totalEntitledToVote) {
        this.totalEntitledToVote = totalEntitledToVote;
    }

    public long getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(long totalCards) {
        this.totalCards = totalCards;
    }

    public long getUnusedCards() {
        return unusedCards;
    }

    public void setUnusedCards(long unusedCards) {
        this.unusedCards = unusedCards;
    }

    public long getRegularVoters() {
        return regularVoters;
    }

    public void setRegularVoters(long regularVoters) {
        this.regularVoters = regularVoters;
    }

    public long getRepresentativeVoters() {
        return representativeVoters;
    }

    public void setRepresentativeVoters(long representativeVoters) {
        this.representativeVoters = representativeVoters;
    }

    public long getCertificateVoters() {
        return certificateVoters;
    }

    public void setCertificateVoters(long certificateVoters) {
        this.certificateVoters = certificateVoters;
    }

    public long getCardsFromBallotBox() {
        return cardsFromBallotBox;
    }

    public void setCardsFromBallotBox(long cardsFromBallotBox) {
        this.cardsFromBallotBox = cardsFromBallotBox;
    }

    public long getCardsFromEnvelopes() {
        return cardsFromEnvelopes;
    }

    public void setCardsFromEnvelopes(long cardsFromEnvelopes) {
        this.cardsFromEnvelopes = cardsFromEnvelopes;
    }

    public long getInvalidCards() {
        return invalidCards;
    }

    public void setInvalidCards(long invalidCards) {
        this.invalidCards = invalidCards;
    }

    public long getValidCards() {
        return validCards;
    }

    public void setValidCards(long validCards) {
        this.validCards = validCards;
    }

    public long getInvalidVotes() {
        return invalidVotes;
    }

    public void setInvalidVotes(long invalidVotes) {
        this.invalidVotes = invalidVotes;
    }

    public long getInvalidVotesToManyTicks() {
        return invalidVotesToManyTicks;
    }

    public void setInvalidVotesToManyTicks(long invalidVotesToManyTicks) {
        this.invalidVotesToManyTicks = invalidVotesToManyTicks;
    }

    public long getInvalidVotesNoTicks() {
        return invalidVotesNoTicks;
    }

    public void setInvalidVotesNoTicks(long invalidVotesNoTicks) {
        this.invalidVotesNoTicks = invalidVotesNoTicks;
    }

    public long getInvalidVotesInvalidCandidate() {
        return invalidVotesInvalidCandidate;
    }

    public void setInvalidVotesInvalidCandidate(long invalidVotesInvalidCandidate) {
        this.invalidVotesInvalidCandidate = invalidVotesInvalidCandidate;
    }

    public long getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(long validVotes) {
        this.validVotes = validVotes;
    }
}