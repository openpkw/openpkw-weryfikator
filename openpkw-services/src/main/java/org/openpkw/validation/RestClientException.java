package org.openpkw.validation;

/**
 * Wyjątek rzucany w sytuacji, kiedy aplikacja stwierdzi, że nie może wykonać zadania ze względu na błąd klienta web serwisu.
 * 
 * <p>
 * Przykładowe sytuacje: błąd składniowy przysłanych danych (brak jakiejś wartości, nieprawidłowa wartość), błąd semantyczny przysłanych danych (np. próba utworzenia użytkownika z adresem e-mail, który już istnieje w bazie danycy). itd.
 * </p>
 */
public class RestClientException extends Exception { // extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private RestClientErrorMessage errorCode;

    public RestClientException(RestClientErrorMessage errorCode) {
        this.errorCode = errorCode;
    }

    public RestClientErrorMessage getErrorCode() {
        return errorCode;
    }

}