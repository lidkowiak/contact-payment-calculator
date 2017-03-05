package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

/**
 * Signals error using NBP Web API.
 */
public class NbpWebApiException extends RuntimeException {

    public NbpWebApiException(String message, Exception cause) {
        super(message, cause);
    }
}
