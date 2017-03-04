package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import java.text.MessageFormat;
import java.util.Currency;

/**
 * Signals that exchanging one currency to another is not supported.
 */
public class ExchangeNotSupportedException extends RuntimeException {

    public ExchangeNotSupportedException(Currency from, Currency to) {
        super(MessageFormat.format("Cannot exchange {0} to {1}", from.getCurrencyCode(), to.getCurrencyCode()));
    }

}
