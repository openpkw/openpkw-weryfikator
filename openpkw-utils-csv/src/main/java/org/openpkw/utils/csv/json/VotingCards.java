package org.openpkw.utils.csv.json;

public class VotingCards {
    private long cardsFromBallotBox;
    private long cardsFromEnvelopes;
    private long invalidCards;
    private long validCards;
    private long invalidVotes;
    private long validVotes;

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

    public long getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(long validVotes) {
        this.validVotes = validVotes;
    }

}
