package org.openpkw.qr.dto;

import java.util.List;
import org.openpkw.model.entity.DistrictCommittee;

/**
 * @author SzestKam (Kamil Szestowicki)
 */
public class DistrictCommitteeDTO {

    private String errorMessage;
    private List<DistrictCommittee> districtCommittee = null;

    public DistrictCommitteeDTO() {
    }

    public DistrictCommitteeDTO(List<DistrictCommittee> districtCommittee) {
        this.districtCommittee = districtCommittee;
    }

    public DistrictCommitteeDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DistrictCommittee> getDistrictCommittee() {
        return districtCommittee;
    }

    public void setDistrictCommittee(List<DistrictCommittee> districtCommittee) {
        this.districtCommittee = districtCommittee;
    }

}
