package org.openpkw.services.rest.dto;

import java.util.List;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
public class VotesAnswerDTO {

    private String errorMessage;
    private long protocolNumber;
    private long protocolAllNumber;
    private long votersVoteNumber;
    private long allVotersNumber;
    private List<VoteCommitteeDTO> allVoteCommittees;

    public List<VoteCommitteeDTO> getAllVoteCommittees() {
        return allVoteCommittees;
    }

    public void setAllVoteCommittees(List<VoteCommitteeDTO> allVoteCommittees) {
        this.allVoteCommittees = allVoteCommittees;
    }

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

    public void setAllVotersNumber(Long allVotersNumber) {
        this.allVotersNumber = allVotersNumber;
    }

}