package org.openpkw.qr.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Result for qr service
 * @author Sebastian Pogorzelski
 */
public class QrResultDTO {

    private String errorMessage;

    private ProtocolDTO protocol;

    private List<CandidateVoteDTO> candidates = new LinkedList<>();

    public QrResultDTO() {
    }

    public QrResultDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ProtocolDTO getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolDTO protocol) {
        this.protocol = protocol;
    }

    public List<CandidateVoteDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateVoteDTO> candidates) {
        this.candidates = candidates;
    }
}
