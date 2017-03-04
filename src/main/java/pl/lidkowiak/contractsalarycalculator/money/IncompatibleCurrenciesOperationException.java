package pl.lidkowiak.contractsalarycalculator.money;

import java.text.MessageFormat;
import java.util.Currency;

/**
 * Signals that operation on money cannot be completed because of different currencies.
 */
public class IncompatibleCurrenciesOperationException extends RuntimeException {

    public IncompatibleCurrenciesOperationException(Currency currency1, Currency currency2) {
        super(MessageFormat.format("Currencies {0} and {1} are incompatible!", currency1, currency2));
    }

}
