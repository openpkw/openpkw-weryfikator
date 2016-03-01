package org.openpkw.services.rest.dto;

/**
 *
 * @author kamil
 */
public class CandidateDTO {

    private String errorMessage;
    private String names;
    private String surname;
    private int number;
    private int votes;

    public CandidateDTO() {
    }

    public CandidateDTO(String errorMessage, String names, String surname, int number, int votes) {
        this.errorMessage = errorMessage;
        this.names = names;
        this.surname = surname;
        this.number = number;
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
