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
    private long protocolNumber;
    private long peripheralsNumber;

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

    public List<PeripheralCommitteeDTO> getPeripherals() {
        return peripherals;
    }

    public void setPeripherals(List<PeripheralCommitteeDTO> peripherals) {
        this.peripherals = peripherals;
    }

    public long getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(long protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public long getPeripheralsNumber() {
        return peripheralsNumber;
    }

    public void setPeripheralsNumber(long peripheralsNumber) {
        this.peripheralsNumber = peripheralsNumber;
    }
}
