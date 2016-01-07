package org.openpkw.weryfikator.rest;

public class Configuration {

    public static final String TEST_SERVER_ADDRESS = "testServerAddress";


    public static String getHost() {
        return System.getProperty(TEST_SERVER_ADDRESS);
    }
}