package org.openpkw.services.init.parse;

/**
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
public enum PeripheralCsvLine {

    DistrictNumber(0),
    TeritorialCode(1),
    Community(2),
    Number(3),
    Address(4),
    AllowedToVote(5);

    private int columnNumber;

    PeripheralCsvLine(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
