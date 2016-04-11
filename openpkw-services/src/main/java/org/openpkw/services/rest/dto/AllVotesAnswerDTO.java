package org.openpkw.services.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kamil
 */
public class AllVotesAnswerDTO {
    private String errorMessage;
    private long protocolNumber;
    private long protocolAllNumber;
    private long votersVoteNumber;
    private long allVotersNumber;
    private List<AllVoteCommitteeDTO> voteCommittees = new ArrayList<>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(long protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public long getProtocolAllNumber() {
        return protocolAllNumber;
    }

    public void setProtocolAllNumber(long protocolAllNumber) {
        this.protocolAllNumber = protocolAllNumber;
    }

    public long getVotersVoteNumber() {
        return votersVoteNumber;
    }

    public void setVotersVoteNumber(long votersVoteNumber) {
        this.votersVoteNumber = votersVoteNumber;
    }

    public long getAllVotersNumber() {
        return allVotersNumber;
    }

    public void setAllVotersNumber(long allVotersNumber) {
        this.allVotersNumber = allVotersNumber;
    }

    public List<AllVoteCommitteeDTO> getVoteCommittees() {
        return voteCommittees;
    }

    public void setVoteCommittees(List<AllVoteCommitteeDTO> voteCommittees) {
        this.voteCommittees = voteCommittees;
    }
}
