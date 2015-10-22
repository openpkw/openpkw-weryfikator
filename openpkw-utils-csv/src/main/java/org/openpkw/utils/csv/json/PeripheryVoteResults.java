package org.openpkw.utils.csv.json;

import java.util.List;

public class PeripheryVoteResults {
    private String territorialCode;
    private long peripheryNumber;
    private String peripheralCommissionAddress;
    private String commune;
    private String county;
    private String province;
    private String districtNumber;
    private String districtCommissionAddress;

    private ElectoralCampaign electoralCampaign;
    private VotingCards votingCards;
    private CorrespondenceVoting correspondenceVoting;
    private List<Committee> committeesList;

    public String getTerritorialCode() {
        return territorialCode;
    }

    public void setTerritorialCode(String territorialCode) {
        this.territorialCode = territorialCode;
    }

    public long getPeripheryNumber() {
        return peripheryNumber;
    }

    public void setPeripheryNumber(long peripheryNumber) {
        this.peripheryNumber = peripheryNumber;
    }

    public String getPeripheralCommissionAddress() {
        return peripheralCommissionAddress;
    }

    public void setPeripheralCommissionAddress(String peripheralCommissionAddress) {
        this.peripheralCommissionAddress = peripheralCommissionAddress;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(String districtNumber) {
        this.districtNumber = districtNumber;
    }

    public String getDistrictCommissionAddress() {
        return districtCommissionAddress;
    }

    public void setDistrictCommissionAddress(String districtCommissionAddress) {
        this.districtCommissionAddress = districtCommissionAddress;
    }

    public ElectoralCampaign getElectoralCampaign() {
        return electoralCampaign;
    }

    public void setElectoralCampaign(ElectoralCampaign electoralCampaign) {
        this.electoralCampaign = electoralCampaign;
    }

    public VotingCards getVotingCards() {
        return votingCards;
    }

    public void setVotingCards(VotingCards votingCards) {
        this.votingCards = votingCards;
    }

    public CorrespondenceVoting getCorrespondenceVoting() {
        return correspondenceVoting;
    }

    public void setCorrespondenceVoting(CorrespondenceVoting correspondenceVoting) {
        this.correspondenceVoting = correspondenceVoting;
    }

    public List<Committee> getCommitteesList() {
        return committeesList;
    }

    public void setCommitteesList(List<Committee> committeesList) {
        this.committeesList = committeesList;
    }
}