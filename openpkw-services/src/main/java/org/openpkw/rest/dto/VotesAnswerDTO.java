package org.openpkw.rest.dto;

import java.util.List;

/**
 *
 * @author kamil
 */
public class VotesAnswerDTO {
    private String errorMessage;
    private int protocolNumber;
    private int protocolAllNumber;
    private int votersVoteNumber;
    private int allVotersNumber;
    private List<VoteCommitteeDTO> voteCommittees;

    public VotesAnswerDTO() {
    }

    public VotesAnswerDTO(String errorMessage, int protocolNumber, int protocolAllNumber, int votersVoteNumber, int allVotersNumber, List<VoteCommitteeDTO> voteCommittees) {
        this.errorMessage = errorMessage;
        this.protocolNumber = protocolNumber;
        this.protocolAllNumber = protocolAllNumber;
        this.votersVoteNumber = votersVoteNumber;
        this.allVotersNumber = allVotersNumber;
        this.voteCommittees = voteCommittees;
    }

    public List<VoteCommitteeDTO> getVoteCommittees() {
        return voteCommittees;
    }

    public void setVoteCommittees(List<VoteCommitteeDTO> voteCommittees) {
        this.voteCommittees = voteCommittees;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(int protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public int getProtocolAllNumber() {
        return protocolAllNumber;
    }

    public void setProtocolAllNumber(int protocolAllNumber) {
        this.protocolAllNumber = protocolAllNumber;
    }

    public int getVotersVoteNumber() {
        return votersVoteNumber;
    }

    public void setVotersVoteNumber(int votersVoteNumber) {
        this.votersVoteNumber = votersVoteNumber;
    }

    public int getAllVotersNumber() {
        return allVotersNumber;
    }

    public void setAllVotersNumber(int allVotersNumber) {
        this.allVotersNumber = allVotersNumber;
    }
    
}
