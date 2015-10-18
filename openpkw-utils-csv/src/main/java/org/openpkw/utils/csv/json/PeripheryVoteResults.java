package org.openpkw.utils.csv.json;

import java.util.List;

public class PeripheryVoteResults {
    private int teritorialCode;
    private int peripheryNumber;
    private VotingCards votingCards;
    private CorrespondenceVoting correspondenceVoting;
    private List<Committee> committeesList;

    public int getTeritorialCode() {
        return teritorialCode;
    }

    public void setTeritorialCode(int teritorialCode) {
        this.teritorialCode = teritorialCode;
    }

    public int getPeripheryNumber() {
        return peripheryNumber;
    }

    public void setPeripheryNumber(int peripheryNumber) {
        this.peripheryNumber = peripheryNumber;
    }

    public VotingCards getVotingCards() {
        return votingCards;
    }

    public void setVotingCards(VotingCards votingCards) {
        this.votingCards = votingCards;
    }

    public CorrespondenceVoting getCorrespondenceVoting() {
        return correspondenceVoting;
    }

    public void setCorrespondenceVoting(CorrespondenceVoting correspondenceVoting) {
        this.correspondenceVoting = correspondenceVoting;
    }

    public List<Committee> getCommitteesList() {
        return committeesList;
    }

    public void setCommitteesList(List<Committee> committeesList) {
        this.committeesList = committeesList;
    }

}
