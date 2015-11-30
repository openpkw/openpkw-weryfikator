package org.openpkw.qr.dto;

import java.io.Serializable;

/**
 * QR DTO using for receive document form mobile
 * @author Sebastian Pogorzelski
 */
public class QrDTO implements Serializable {

    private String token;

    private String qr;

    public QrDTO() {
    }

    public QrDTO(String qr, String token) {
        this.token = token;
        this.qr = qr;
    }

    public String getToken() {
        return token;
    }


    public String getQr() {
        return qr;
    }

}
