package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import org.junit.Before;
import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.ExchangeRatesTableDto;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpWebApiClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class NbpRateTableOfATypeSupplierTest {

    ExchangeRatesTableDto mockedExchangeRatesTableDto = null;

    NbpWebApiClient mockNbpWebApiClient = new NbpWebApiClient("") {
        @Override
        public ExchangeRatesTableDto getCurrentExchangeRatesTableA() {
            return mockedExchangeRatesTableDto;
        }
    };

    NbpRateTableOfATypeSupplier cut = new NbpRateTableOfATypeSupplier(mockNbpWebApiClient);

    @Before
    public void clear() {
        mockedExchangeRatesTableDto = null;
    }

    @Test
    public void should_return_exchange_rates_table() {
        //given
        mockedExchangeRatesTableDto = ExchangeRatesTableDto.builder()
                .table("A")
                .no("040/A/NBP/2017")
                .effectiveDate("2017-02-27")
                .rates(singletonList(rate("euro", "EUR", "4.3135")))
                .build();
        //when
        //then
        assertThat(cut.get()).isEqualTo(ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.FEBRUARY, 27))
                .table(Collections.singletonMap(Currencies.EUR, new BigDecimal("4.3135")))
                .build());
    }

    private ExchangeRatesTableDto.RateDto rate(String currency, String code, String mid) {
        return new ExchangeRatesTableDto.RateDto(currency, code, new BigDecimal(mid));
    }

}