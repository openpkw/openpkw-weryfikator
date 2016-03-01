package org.openpkw.services.rest.dto;

/**
 *
 * @author kamil
 */
public class PeripheralCommitteeDTO {
    private String errorMessage;
    private String name;
    private String teritorialCode;
    private int number;

    public PeripheralCommitteeDTO(String errorMessage, String name, String teritorialCode, int number) {
        this.errorMessage = errorMessage;
        this.name = name;
        this.teritorialCode = teritorialCode;
        this.number = number;
    }

    public PeripheralCommitteeDTO() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeritorialCode() {
        return teritorialCode;
    }

    public void setTeritorialCode(String teritorialCode) {
        this.teritorialCode = teritorialCode;
    }
    
    
}
