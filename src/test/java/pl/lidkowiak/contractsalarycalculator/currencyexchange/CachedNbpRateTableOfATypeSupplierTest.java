package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import org.junit.Before;
import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CachedNbpRateTableOfATypeSupplierTest {

    ExchangeRateTable mockedExchangeRateTable;
    int invocationCount;
    LocalDateTime mockedCurrentTime;
    CachedNbpRateTableOfATypeSupplier cut;

    @Before
    public void cleanMocks() {
        mockedExchangeRateTable = null;
        invocationCount = 0;
        mockedCurrentTime = null;
        cut = new CachedNbpRateTableOfATypeSupplier(() -> {
            invocationCount++;
            return mockedExchangeRateTable;
        }, () -> mockedCurrentTime);
    }

    @Test
    public void should_invoke_decorated_supplier_when_there_is_no_exchange_rates_table() throws Exception {
        //given
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 5))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 15, 0, 0);

        //when
        ExchangeRateTable exchangeRateTable = cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(1);
    }

    @Test
    public void should_invoke_once_decorated_supplier_when_there_is_no_newest_table() throws Exception {
        //given
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 5))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 15, 0, 0);

        //when
        ExchangeRateTable exchangeRateTable = cut.get();
        exchangeRateTable = cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(1);
    }

    @Test
    public void should_invoke_decorated_supplier_when_cached_table_yesterday_and_it_is_after_11_45() throws Exception {
        // init
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 5))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 15, 0, 0);
        cut.get();

        //given
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 6))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 6, 15, 0, 0);

        // when
        ExchangeRateTable exchangeRateTable = cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(2);
    }

    @Test
    public void should_not_invoke_decorated_supplier_when_cached_table_yesterday_and_it_is_before_11_45() throws Exception {
        //init
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 5))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 15, 0, 0);
        cut.get();

        //given
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 6, 11, 44, 0);

        // when
        ExchangeRateTable exchangeRateTable = cut.get();
        cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(1);
    }

    @Test
    public void should_not_invoke_decorated_supplier_when_cached_table_day_before_yesterday_and_it_is_before_11_45() throws Exception {
        //init
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 5))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 15, 0, 0);
        cut.get();

        //given
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 7))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(5)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 7, 11, 44, 0);

        // when
        ExchangeRateTable exchangeRateTable = cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(2);
    }

    @Test
    public void should_invoke_once_underlying_supplier_in_case_of_not_working_day() throws Exception {
        //init
        mockedExchangeRateTable = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.MARCH, 3))
                .table(Collections.singletonMap(Currencies.EUR, BigDecimal.valueOf(4)))
                .build();
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 3, 15, 0, 0);
        cut.get();

        //given
        mockedCurrentTime = LocalDateTime.of(2017, Month.MARCH, 5, 11, 44, 0);

        // when
        ExchangeRateTable exchangeRateTable = cut.get();
        exchangeRateTable = cut.get();
        exchangeRateTable = cut.get();

        //then
        assertThat(exchangeRateTable).isEqualTo(mockedExchangeRateTable);
        assertThat(invocationCount).isEqualTo(2);
    }
}