package org.openpkw.rest.dto;

import java.util.List;

/**
 *
 * @author kamil
 */
public class PeripheralsDistrinctDTO {

    private String errorMesage;
    private List<PeripheralCommitteeDTO> peripherals;

    public PeripheralsDistrinctDTO() {
    }

    public PeripheralsDistrinctDTO(String errorMesage, List<PeripheralCommitteeDTO> peripherals) {
        this.errorMesage = errorMesage;
        this.peripherals = peripherals;
    }

    public List<PeripheralCommitteeDTO> getPeripherals() {
        return peripherals;
    }

    public void setPeripherals(List<PeripheralCommitteeDTO> peripherals) {
        this.peripherals = peripherals;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }

}
