package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import java.text.MessageFormat;
import java.util.Currency;

public class ExchangeNotSupportedException extends RuntimeException {

    public ExchangeNotSupportedException(Currency from, Currency to) {
        super(MessageFormat.format("Can not exchange {0} to {1}", from.getCurrencyCode(), to.getCurrencyCode()));
    }

}
