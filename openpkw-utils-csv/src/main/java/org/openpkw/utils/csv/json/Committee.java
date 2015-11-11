package org.openpkw.utils.csv.json;

import java.util.List;

public class Committee {

    private String committeeName;
    private long committeeNumber;
    private long committeeVotesNumber;
    private long totalCandidatesVotesNumber;
    private List<Candidate> candidate;

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public long getCommitteeNumber() {
        return committeeNumber;
    }

    public void setCommitteeNumber(long committeeNumber) {
        this.committeeNumber = committeeNumber;
    }

    public long getCommitteeVotesNumber() {
        return committeeVotesNumber;
    }

    public void setCommitteeVotesNumber(long committeeVotesNumber) {
        this.committeeVotesNumber = committeeVotesNumber;
    }

    public long getTotalCandidatesVotesNumber() {
        return totalCandidatesVotesNumber;
    }

    public void setTotalCandidatesVotesNumber(long totalCandidatesVotesNumber) {
        this.totalCandidatesVotesNumber = totalCandidatesVotesNumber;
    }

    public List<Candidate> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<Candidate> candidate) {
        this.candidate = candidate;
    }

}
