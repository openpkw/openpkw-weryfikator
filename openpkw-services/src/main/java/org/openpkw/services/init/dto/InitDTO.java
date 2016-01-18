package org.openpkw.services.init.dto;

/**
 * Created by mrozi on 14.01.16.
 */
public class InitDTO {
    private String errorMessage;
    private long candidateCount;
    private long peripheralCommitteCount;
    private long peripheralCommitteeAddressCount;
    private long districtCommitteeCount;
    private long districtCommitteeAddresCount;
    private long electionCommitteeCount;
    private long electionCommitteeDistrictCount;

    public InitDTO()
    {

    }

    public InitDTO(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getCandidateCount() {
        return candidateCount;
    }

    public void setCandidateCount(long candidateCount) {
        this.candidateCount = candidateCount;
    }

    public long getPeripheralCommitteCount() {
        return peripheralCommitteCount;
    }

    public void setPeripheralCommitteCount(long peripheralCommitteCount) {
        this.peripheralCommitteCount = peripheralCommitteCount;
    }

    public long getPeripheralCommitteeAddressCount() {
        return peripheralCommitteeAddressCount;
    }

    public void setPeripheralCommitteeAddressCount(long peripheralCommitteeAddressCount) {
        this.peripheralCommitteeAddressCount = peripheralCommitteeAddressCount;
    }

    public long getDistrictCommitteeCount() {
        return districtCommitteeCount;
    }

    public void setDistrictCommitteeCount(long districtCommitteeCount) {
        this.districtCommitteeCount = districtCommitteeCount;
    }

    public long getDistrictCommitteeAddresCount() {
        return districtCommitteeAddresCount;
    }

    public void setDistrictCommitteeAddresCount(long districtCommitteeAddresCount) {
        this.districtCommitteeAddresCount = districtCommitteeAddresCount;
    }

    public long getElectionCommitteeCount() {
        return electionCommitteeCount;
    }

    public void setElectionCommitteeCount(long electionCommitteeCount) {
        this.electionCommitteeCount = electionCommitteeCount;
    }

    public long getElectionCommitteeDistrictCount() {
        return electionCommitteeDistrictCount;
    }

    public void setElectionCommitteeDistrictCount(long electionCommitteeDistrictCount) {
        this.electionCommitteeDistrictCount = electionCommitteeDistrictCount;
    }
}
