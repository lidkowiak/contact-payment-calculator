package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.function.Supplier;

/**
 * Exchanges currencies to PLN using provided exchange rates table supplier.
 */
public class ToPlnExchanger {

    private Supplier<ExchangeRateTable> exchangeRateTableSupplier;

    public ToPlnExchanger(Supplier<ExchangeRateTable> exchangeRateTableSupplier) {
        this.exchangeRateTableSupplier = exchangeRateTableSupplier;
    }

    /**
     * @throws  ExchangeNotSupportedException
     */
    public Money exchange(Money toExchange) {
        if (toExchange.hasCurrency(Currencies.PLN)) {
            return toExchange;
        }

        final Currency sourceCurrency = toExchange.getCurrency();

        final ExchangeRateTable exchangeRatesTable = exchangeRateTableSupplier.get();
        BigDecimal exchangeRate = exchangeRatesTable.exchangeRateFor(sourceCurrency)
                .orElseThrow(() -> new ExchangeNotSupportedException(sourceCurrency, Currencies.PLN));

        return Money.pln(toExchange.getAmount().multiply(exchangeRate));
    }

}
