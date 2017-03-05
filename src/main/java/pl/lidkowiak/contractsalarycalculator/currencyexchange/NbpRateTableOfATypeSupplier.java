package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.nbpapiclient.ExchangeRatesTableDto;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpWebApiClient;

import java.util.Currency;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toMap;

/**
 * Supplies current NBP exchange rate A table.
 * Refer to {@see https://www.nbp.pl/Kursy/KursyA.html} in order to check supported currencies.
 */
public class NbpRateTableOfATypeSupplier implements Supplier<ExchangeRateTable> {

    private NbpWebApiClient nbpWebApiClient;

    public NbpRateTableOfATypeSupplier(NbpWebApiClient nbpWebApiClient) {
        this.nbpWebApiClient = nbpWebApiClient;
    }

    @Override
    public ExchangeRateTable get() {
        final ExchangeRatesTableDto exchangeRatesTable = nbpWebApiClient.getCurrentExchangeRatesTableA();

        return ExchangeRateTable.builder()
                .publicationDate(exchangeRatesTable.getEffectiveDate())
                .table(exchangeRatesTable.getRates().stream()
                        .collect(toMap(r -> Currency.getInstance(r.getCode()), r -> r.getMid())))
                .build();

    }
}
