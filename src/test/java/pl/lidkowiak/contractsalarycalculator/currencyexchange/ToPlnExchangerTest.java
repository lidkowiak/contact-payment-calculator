package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import org.junit.Before;
import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToPlnExchangerTest {

    ExchangeRateTable mockedExchangeTableRate = null;
    ToPlnExchanger cut = new ToPlnExchanger(() -> mockedExchangeTableRate);

    @Before
    public void clear() {
        mockedExchangeTableRate = null;
    }

    @Test
    public void should_return_not_changed_PLN_money() {
        //given
        Money toExchange = Money.pln(BigDecimal.valueOf(100));
        //when
        Money exchanged = cut.exchange(toExchange);
        //then
        assertThat(exchanged).isEqualTo(Money.pln(BigDecimal.valueOf(100)));
    }

    @Test
    public void should_exchange_EUR_to_PLN() {
        //given
        mockedExchangeTableRate = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.FEBRUARY, 27))
                .table(Collections.singletonMap(Currencies.EUR, new BigDecimal("4.3135")))
                .build();
        Money toExchange = Money.eur(BigDecimal.valueOf(100));
        //when
        Money exchanged = cut.exchange(toExchange);
        //then
        assertThat(exchanged).isEqualTo(Money.pln(new BigDecimal("431.35")));
    }

    @Test
    public void should_throw_exception_when_try_to_currency_that_is_not_defined_in_exchange_table() {
        //given
        mockedExchangeTableRate = ExchangeRateTable.builder()
                .publicationDate(LocalDate.of(2017, Month.FEBRUARY, 27))
                .table(Collections.singletonMap(Currencies.EUR, new BigDecimal("4.3135")))
                .build();
        Money toExchange = Money.gbp(BigDecimal.valueOf(100));
        //when
        //then
        assertThatThrownBy(() -> cut.exchange(toExchange))
                .isInstanceOf(ExchangeNotSupportedException.class)
                .hasMessage("Cannot exchange GBP to PLN")
                .hasNoCause();
    }
}