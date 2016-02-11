package org.openpkw.services.init.parse;

/**
 * Created by mrozi on 12.01.16.
 */
public enum DistrictCsvLine {
    City(2),
    Number(1),
    Name(3);

    private int lineNumber;

    private DistrictCsvLine(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}