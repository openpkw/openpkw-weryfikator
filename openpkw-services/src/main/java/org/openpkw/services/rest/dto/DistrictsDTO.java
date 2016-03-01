package org.openpkw.services.rest.dto;

import java.util.List;

/**
 *
 * @author kamil
 */
public class DistrictsDTO {

    private String errorMessage;
    private List<DistrictCommitteeDTO> districts;

    public DistrictsDTO() {
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setDistricts(List<DistrictCommitteeDTO> districts) {
        this.districts = districts;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<DistrictCommitteeDTO> getDistricts() {
        return districts;
    }

}
