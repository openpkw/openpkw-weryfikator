package org.openpkw.qr.dto;

/**
 * Candidate DTO using for parse candidates from qr string
 * @author Sebastian Pogorzelski
 */
public class CandidateVoteDTO {

    private Integer positionOnList;

    private Integer votesNumber;

    private Integer listNumber;

    private String name;

    public CandidateVoteDTO(Integer listNumber, Integer positionOnList, Integer votesNumber) {
        this.positionOnList = positionOnList;
        this.votesNumber = votesNumber;
        this.listNumber = listNumber;
    }

    public Integer getPositionOnList() {
        return positionOnList;
    }

    public Integer getVotesNumber() {
        return votesNumber;
    }

    public Integer getListNumber() {
        return listNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
