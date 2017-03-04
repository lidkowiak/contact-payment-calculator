package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.ExchangeRatesTableDto;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpWebApiClient;

import java.util.Currency;

/**
 * Exchanges currencies to PLN using current NBP exchange rate A table.
 * Refer to {@see https://www.nbp.pl/Kursy/KursyA.html} in order to check supported currencies.
 */
public class NbpRateTableAToPlnExchanger implements ToPlnExchanger {

    private NbpWebApiClient nbpWebApiClient;

    public NbpRateTableAToPlnExchanger(NbpWebApiClient nbpWebApiClient) {
        this.nbpWebApiClient = nbpWebApiClient;
    }

    @Override
    public Money exchange(Money toExchange) {
        if (toExchange.hasCurrency(Currencies.PLN)) {
            return toExchange;
        }

        final Currency sourceCurrency = toExchange.getCurrency();

        final ExchangeRatesTableDto exchangeRatesTable = nbpWebApiClient.getCurrentExchangeRatesTableA();
        ExchangeRatesTableDto.RateDto rate = exchangeRatesTable.rateFor(sourceCurrency)
                .orElseThrow(() -> new ExchangeNotSupportedException(sourceCurrency, Currencies.PLN));

        return Money.pln(toExchange.getAmount().multiply(rate.getMid()));
    }

}
