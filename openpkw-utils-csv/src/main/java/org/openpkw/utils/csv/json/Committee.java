package org.openpkw.utils.csv.json;

import java.util.List;

public class Committee {
    private String committeeName;
    private long committeeVotesNumber;
    private List<Candidate> candidate;
    
    public String getCommitteeName() {
        return committeeName;
    }
    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
    public long getCommitteeVotesNumber() {
        return committeeVotesNumber;
    }
    public void setCommitteeVotesNumber(long committeeVotesNumber) {
        this.committeeVotesNumber = committeeVotesNumber;
    }
    public List<Candidate> getCandidate() {
        return candidate;
    }
    public void setCandidate(List<Candidate> candidate) {
        this.candidate = candidate;
    }
    
    
}
