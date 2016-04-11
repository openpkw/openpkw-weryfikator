package org.openpkw.services.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Szestowicki
 * @author Sebastian Celejewski
 */
public class DistrictsDTO {

    private String errorMessage;

    private List<DistrictCommitteeDTO> districts = new ArrayList<>();

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setDistricts(List<DistrictCommitteeDTO> districts) {
        this.districts = districts;
    }

    public List<DistrictCommitteeDTO> getDistricts() {
        return districts;
    }
}