package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

class FetchingExchangeRatesTableException extends RuntimeException {

    FetchingExchangeRatesTableException(Exception cause) {
        super("Error occurred during fetching NBP exchange rates!", cause);
    }
}
