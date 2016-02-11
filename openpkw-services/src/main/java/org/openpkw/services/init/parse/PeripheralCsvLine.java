package org.openpkw.services.init.parse;

/**
 * Created by mrozi on 12.01.16.
 */
public enum PeripheralCsvLine {
    DistrictNumber(0),
    Name(4),
    Number(3),
    TeritorialCode(1),
    AddressName(4);

    private int lineNumber;

    private PeripheralCsvLine(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
