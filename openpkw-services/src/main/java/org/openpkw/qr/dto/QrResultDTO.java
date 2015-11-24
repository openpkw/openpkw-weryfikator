package org.openpkw.qr.dto;

/**
 * Result for qr service
 * @author Sebastian Pogorzelski
 */
public class QrResultDTO {

    private String error;

    public QrResultDTO() {
    }

    public QrResultDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
