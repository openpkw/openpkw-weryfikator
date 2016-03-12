package org.openpkw.weryfikator.rest;

public class Configuration {

    public static final String TEST_SERVER_ADDRESS = "targetServerAddress";

    public static String getHost() {
        String testServerAddress = System.getProperty(TEST_SERVER_ADDRESS);

        if (testServerAddress == null || testServerAddress.trim().length() == 0) {
            throw new RuntimeException("Test server address is not configured. End-to-end have to be run against a running instance of OpenPKW Weryfikator. Provide address of the server in " + TEST_SERVER_ADDRESS + " property.");
        }

        return testServerAddress;
    }
}