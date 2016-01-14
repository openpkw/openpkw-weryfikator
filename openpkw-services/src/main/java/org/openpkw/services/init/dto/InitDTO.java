package org.openpkw.services.init.dto;

/**
 * Created by mrozi on 14.01.16.
 */
public class InitDTO {
    private String errorMessage;
    private long candidate;
    private long peripheralCommitte;
    private long peripheralCommitteeAddress;
    private long districtCommittee;
    private long districtCommitteeAddres;
    private long electionCommittee;
    private long electionCommitteeDistrict;


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

    public long getCandidate() {
        return candidate;
    }

    public void setCandidate(long candidate) {
        this.candidate = candidate;
    }


    public long getPeripheralCommitte() {
        return peripheralCommitte;
    }

    public void setPeripheralCommitte(long peripheralCommitte) {
        this.peripheralCommitte = peripheralCommitte;
    }

    public long getPeripheralCommitteeAddress() {
        return peripheralCommitteeAddress;
    }

    public void setPeripheralCommitteeAddress(long peripheralCommitteeAddress) {
        this.peripheralCommitteeAddress = peripheralCommitteeAddress;
    }

    public long getDistrictCommittee() {
        return districtCommittee;
    }

    public void setDistrictCommittee(long districtCommittee) {
        this.districtCommittee = districtCommittee;
    }

    public long getDistrictCommitteeAddres() {
        return districtCommitteeAddres;
    }

    public void setDistrictCommitteeAddres(long districtCommitteeAddres) {
        this.districtCommitteeAddres = districtCommitteeAddres;
    }

    public long getElectionCommittee() {
        return electionCommittee;
    }

    public void setElectionCommittee(long electionCommittee) {
        this.electionCommittee = electionCommittee;
    }

    public long getElectionCommitteeDistrict() {
        return electionCommitteeDistrict;
    }

    public void setElectionCommitteeDistrict(long electionCommitteeDistrict) {
        this.electionCommitteeDistrict = electionCommitteeDistrict;
    }
}
