package org.openpkw.utils.csv.json;

public class PeripheryVote {
    private String userName;
    private String token;
    private String okwJsonLink;
    private java.util.Date sendDate;
    private PeripheryVoteResults results;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOkwJsonLink() {
        return okwJsonLink;
    }

    public void setOkwJsonLink(String okwJsonLink) {
        this.okwJsonLink = okwJsonLink;
    }

    public java.util.Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(java.util.Date sendDate) {
        this.sendDate = sendDate;
    }

    public PeripheryVoteResults getResults() {
        return results;
    }

    public void setResults(PeripheryVoteResults results) {
        this.results = results;
    }

}
