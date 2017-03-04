package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

/**
 * Signals error using NBP Web API.
 */
class NbpWebApiException extends RuntimeException {

    NbpWebApiException(String message, Exception cause) {
        super(message, cause);
    }
}
