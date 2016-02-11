package org.openpkw.rest.dto;

import java.util.List;

/**
 *
 * @author kamil
 */
public class AllVoteCommitteeDTO {

    private String errorMessage;
    private String name;
    private int number;
    private int votes;

    public AllVoteCommitteeDTO() {
    }

    public AllVoteCommitteeDTO(String errorMessage, String name, int number, int votes) {
        this.errorMessage = errorMessage;
        this.name = name;
        this.number = number;
        this.votes = votes;
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
