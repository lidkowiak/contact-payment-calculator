package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.ExchangeRatesTableDto;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpApiClient;

import java.util.Currency;

public class NbpRateTableAToPlnExchanger implements ToPlnExchanger {

    private NbpApiClient nbpApiClient;

    public NbpRateTableAToPlnExchanger(NbpApiClient nbpApiClient) {
        this.nbpApiClient = nbpApiClient;
    }

    @Override
    public Money exchange(Money toExchange) {
        if (toExchange.hasCurrency(Currencies.PLN)) {
            return toExchange;
        }

        final Currency sourceCurrency = toExchange.getCurrency();

        final ExchangeRatesTableDto exchangeRatesTable = nbpApiClient.getExchangeRatesTableA();
        ExchangeRatesTableDto.RateDto rate = exchangeRatesTable.rateFor(sourceCurrency)
                .orElseThrow(() -> new ExchangeNotSupportedException(sourceCurrency, Currencies.PLN));

        return Money.pln(toExchange.getAmount().multiply(rate.getMid()));
    }

}
