package pl.lidkowiak.contractsalarycalculator.money;

import java.text.MessageFormat;
import java.util.Currency;

public class IncompatibleCurrenciesOperationException extends RuntimeException {

    public IncompatibleCurrenciesOperationException(Currency currency1, Currency currency2) {
        super(MessageFormat.format("Currencies {0} and {1} are incompatible!", currency1, currency2));
    }

}
