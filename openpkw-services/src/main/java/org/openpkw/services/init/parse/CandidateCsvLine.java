package org.openpkw.services.init.parse;

/**
 * Created by mrozi on 12.01.16.
 */
public enum CandidateCsvLine {
    DistrictNumber(0),
    ElectionCommitteeListNumber(1),
    ElectionCommiteeListName(2),
    Number(3),
    Surname(4),
    Name(5);
    private int lineNumber;
    private CandidateCsvLine(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}