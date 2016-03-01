package org.openpkw.services.rest.dto;

import java.util.List;

/**
 * @author SzestKam (Kamil Szestowicki)
 */
public class DistrictCommitteeDTO {

    private String errorMessage;
    private int number;
    private List<String> cities;
    private String name;
    private List<PeripheralCommitteeDTO> peripherals;
    private int protocoloNumber;

    public DistrictCommitteeDTO() {
    }

    public DistrictCommitteeDTO(String errorMessage, int number, List<String> cities, String name, List<PeripheralCommitteeDTO> peripherals,int protocolNumber) {
        this.errorMessage = errorMessage;
        this.number = number;
        this.cities = cities;
        this.name = name;
        this.peripherals = peripherals;
        this.protocoloNumber = protocolNumber;
    }

    public List<PeripheralCommitteeDTO> getPeripherals() {
        return peripherals;
    }

    public void setPeripherals(List<PeripheralCommitteeDTO> peripherals) {
        this.peripherals = peripherals;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProtocoloNumber() {
        return protocoloNumber;
    }

    public void setProtocoloNumber(int protocoloNumber) {
        this.protocoloNumber = protocoloNumber;
    }
}
