package pl.lidkowiak.contractsalarycalculator.currencyexchange.nbpexchangeratetable;

import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.ExchangeNotSupportedException;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.ToPlnExchanger;

import java.util.Currency;
import java.util.function.Supplier;

public class NbpRateTableAToPlnExchanger implements ToPlnExchanger {

    private Supplier<ExchangeRatesTableDto> exchangeRatesTableADto;

    public NbpRateTableAToPlnExchanger(String nbpApiExchangeRatesTableUrl) {
        this(new NbpExchangeRateTableASupplier(nbpApiExchangeRatesTableUrl));
    }

    public NbpRateTableAToPlnExchanger(Supplier<ExchangeRatesTableDto> exchangeRatesTableADto) {
        this.exchangeRatesTableADto = exchangeRatesTableADto;
    }

    @Override
    public Money exchange(Money toExchange) {
        if (toExchange.hasCurrency(Currencies.PLN)) {
            return toExchange;
        }

        final Currency sourceCurrency = toExchange.getCurrency();

        final ExchangeRatesTableDto exchangeRatesTable = exchangeRatesTableADto.get();
        ExchangeRatesTableDto.RateDto rate = exchangeRatesTable.rateFor(sourceCurrency)
                .orElseThrow(() -> new ExchangeNotSupportedException(sourceCurrency, Currencies.PLN));

        return Money.pln(toExchange.getAmount().multiply(rate.getMid()));
    }

}
