package org.openpkw.weryfikator.rest;

public class Configuration {

    private final static String LOCALHOST = "http://localhost:8080/openpkw-rest";
    private final static String VAGRANT = "http://localhost:4500/openpkw";
    private final static String TEST = "http://dobromir.openpkw.pl:9080/openpkw";
    private final static String UAT = "http://pat.openpkw.pl:9080/openpkw";
    
    /**
     * TODO: Zwracać konfigurację w zależności od parametrów przekazanych do Mavena
     * np. mvn verivy -Dopenpkw-env=DEV
     */
    public static String getHost() {
        return TEST;
    }
}