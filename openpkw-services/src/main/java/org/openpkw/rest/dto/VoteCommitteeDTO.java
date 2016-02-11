package org.openpkw.rest.dto;

import java.util.List;

/**
 *
 * @author kamil
 */
public class VoteCommitteeDTO {

    private String errorMessage;
    private String name;                //nazwa komitetu wyborczego
    private int number;                 //numer na liście
    private int votes;                  //liczba oddanych głosów
    private List<CandidateDTO> candidates;

    public VoteCommitteeDTO() {
    }

    public VoteCommitteeDTO(String errorMessage, String name, int number, int votes, List<CandidateDTO> candidates) {
        this.errorMessage = errorMessage;
        this.name = name;
        this.number = number;
        this.votes = votes;
        this.candidates = candidates;
    }

    public List<CandidateDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDTO> candidates) {
        this.candidates = candidates;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

}
