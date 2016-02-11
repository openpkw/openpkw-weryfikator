package org.openpkw.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PROTOCOL")
public class Protocol {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "PROTOCOL_ID")
    private Long protocolId;

    @Column(name = "name")
    private String name;

    @Column(name = "cards_given")
    private Long cardsGiven;

    @Temporal(TemporalType.DATE)
    @Column(name = "received_date")
    private Date receivedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peripheral_committee_id")
    private PeripheralCommittee peripheralCommittee;

    @Column(name = "total_entitled_to_vote")
    private String totalEntitledToVote;

    @Column(name = "total_cards")
    private String totalCards;

    @Column(name = "unused_cards")
    private String unusedCards;

    @Column(name = "regular_voters")
    private String regularVoters;

    @Column(name = "representative_voters")
    private String representativeVoters;

    @Column(name = "certificate_voters")
    private String certificateVoters;

    @Column(name = "from_ballot_box")
    private String fromBallotBox;

    @Column(name = "from_envelopes")
    private String fromEnvelopes;

    @Column(name = "invalid_cards")
    private String invalidCards;

    @Column(name = "valid_cards")
    private String validCards;

    @Column(name = "invalid_votes")
    private String invalidVotes;

    @Column(name = "valid_votes")
    private String validVotes;

    @Column(name = "voting_issued_packages")
    private String votingIssuedPackages;

    @Column(name = "received_reply_envelopes")
    private String receivedReplyEnvelopes;

    @Column(name = "missing_statement")
    private String missingStatement;

    @Column(name = "missing_signature_on_statement")
    private String missingSignatureOnStatement;

    @Column(name = "missing_envelope_for_voting_card")
    private String missingEnvelopeForVotingCard;

    @Column(name = "unsealed_envelope")
    private String unsealedEnvelope;

    @Column(name = "envelopes_thrown_to_ballot_box")
    private String envelopesThrownToBallotBox;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardsGiven() {
        return cardsGiven;
    }

    public void setCardsGiven(Long cardsGiven) {
        this.cardsGiven = cardsGiven;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public PeripheralCommittee getPeripheralCommittee() {
        return peripheralCommittee;
    }

    public void setPeripheralCommittee(PeripheralCommittee peripheralCommittee) {
        this.peripheralCommittee = peripheralCommittee;
    }

    public String getTotalEntitledToVote() {
        return totalEntitledToVote;
    }

    public void setTotalEntitledToVote(String totalEntitledToVote) {
        this.totalEntitledToVote = totalEntitledToVote;
    }

    public String getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(String totalCards) {
        this.totalCards = totalCards;
    }

    public String getUnusedCards() {
        return unusedCards;
    }

    public void setUnusedCards(String unusedCards) {
        this.unusedCards = unusedCards;
    }

    public String getRegularVoters() {
        return regularVoters;
    }

    public void setRegularVoters(String regularVoters) {
        this.regularVoters = regularVoters;
    }

    public String getRepresentativeVoters() {
        return representativeVoters;
    }

    public void setRepresentativeVoters(String representativeVoters) {
        this.representativeVoters = representativeVoters;
    }

    public String getCertificateVoters() {
        return certificateVoters;
    }

    public void setCertificateVoters(String certificateVoters) {
        this.certificateVoters = certificateVoters;
    }

    public String getFromBallotBox() {
        return fromBallotBox;
    }

    public void setFromBallotBox(String fromBallotBox) {
        this.fromBallotBox = fromBallotBox;
    }

    public String getFromEnvelopes() {
        return fromEnvelopes;
    }

    public void setFromEnvelopes(String fromEnvelopes) {
        this.fromEnvelopes = fromEnvelopes;
    }

    public String getInvalidCards() {
        return invalidCards;
    }

    public void setInvalidCards(String invalidCards) {
        this.invalidCards = invalidCards;
    }

    public String getValidCards() {
        return validCards;
    }

    public void setValidCards(String validCards) {
        this.validCards = validCards;
    }

    public String getInvalidVotes() {
        return invalidVotes;
    }

    public void setInvalidVotes(String invalidVotes) {
        this.invalidVotes = invalidVotes;
    }

    public String getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(String validVotes) {
        this.validVotes = validVotes;
    }

    public String getVotingIssuedPackages() {
        return votingIssuedPackages;
    }

    public void setVotingIssuedPackages(String votingIssuedPackages) {
        this.votingIssuedPackages = votingIssuedPackages;
    }

    public String getReceivedReplyEnvelopes() {
        return receivedReplyEnvelopes;
    }

    public void setReceivedReplyEnvelopes(String receivedReplyEnvelopes) {
        this.receivedReplyEnvelopes = receivedReplyEnvelopes;
    }

    public String getMissingStatement() {
        return missingStatement;
    }

    public void setMissingStatement(String missingStatement) {
        this.missingStatement = missingStatement;
    }

    public String getMissingSignatureOnStatement() {
        return missingSignatureOnStatement;
    }

    public void setMissingSignatureOnStatement(String missingSignatureOnStatement) {
        this.missingSignatureOnStatement = missingSignatureOnStatement;
    }

    public String getMissingEnvelopeForVotingCard() {
        return missingEnvelopeForVotingCard;
    }

    public void setMissingEnvelopeForVotingCard(String missingEnvelopeForVotingCard) {
        this.missingEnvelopeForVotingCard = missingEnvelopeForVotingCard;
    }

    public String getUnsealedEnvelope() {
        return unsealedEnvelope;
    }

    public void setUnsealedEnvelope(String unsealedEnvelope) {
        this.unsealedEnvelope = unsealedEnvelope;
    }

    public String getEnvelopesThrownToBallotBox() {
        return envelopesThrownToBallotBox;
    }

    public void setEnvelopesThrownToBallotBox(String envelopesThrownToBallotBox) {
        this.envelopesThrownToBallotBox = envelopesThrownToBallotBox;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }
}
