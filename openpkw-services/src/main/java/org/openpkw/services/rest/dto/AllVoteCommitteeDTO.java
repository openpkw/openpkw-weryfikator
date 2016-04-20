package org.openpkw.services.rest.dto;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
public class AllVoteCommitteeDTO {

    private String errorMessage;
    private String longName;
    private String shortName;
    private String symbol;
    private int number;
    private int votes;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() { return shortName; }

    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getSymbol() { return symbol; }

    public void setSymbol(String symbol) { this.symbol = symbol; }

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

