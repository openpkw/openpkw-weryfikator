package org.openpkw.utils.csv.json;

public class CorrespondenceVoting {
    private long issuedPackages;
    private long receivedReplyEnvelopes;
    private long missingStatement;
    private long missingSignatureOnStatement;
    private long missingEnvelopeForVotingCard;
    private long unsealedEnvelope;
    private long envelopesThrownToBallotBox;

    public long getIssuedPackages() {
        return issuedPackages;
    }

    public void setIssuedPackages(long issuedPackages) {
        this.issuedPackages = issuedPackages;
    }

    public long getReceivedReplyEnvelopes() {
        return receivedReplyEnvelopes;
    }

    public void setReceivedReplyEnvelopes(long receivedReplyEnvelopes) {
        this.receivedReplyEnvelopes = receivedReplyEnvelopes;
    }

    public long getMissingStatement() {
        return missingStatement;
    }

    public void setMissingStatement(long missingStatement) {
        this.missingStatement = missingStatement;
    }

    public long getMissingSignatureOnStatement() {
        return missingSignatureOnStatement;
    }

    public void setMissingSignatureOnStatement(long missingSignatureOnStatement) {
        this.missingSignatureOnStatement = missingSignatureOnStatement;
    }

    public long getMissingEnvelopeForVotingCard() {
        return missingEnvelopeForVotingCard;
    }

    public void setMissingEnvelopeForVotingCard(long missingEnvelopeForVotingCard) {
        this.missingEnvelopeForVotingCard = missingEnvelopeForVotingCard;
    }

    public long getUnsealedEnvelope() {
        return unsealedEnvelope;
    }

    public void setUnsealedEnvelope(long unsealedEnvelope) {
        this.unsealedEnvelope = unsealedEnvelope;
    }

    public long getEnvelopesThrownToBallotBox() {
        return envelopesThrownToBallotBox;
    }

    public void setEnvelopesThrownToBallotBox(long envelopesThrownToBallotBox) {
        this.envelopesThrownToBallotBox = envelopesThrownToBallotBox;
    }

}
