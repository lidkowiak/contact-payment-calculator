package pl.lidkowiak.contractsalarycalculator.currencyexchange.nbpexchangeratetable;

import org.junit.Before;
import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.core.Money;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.ExchangeNotSupportedException;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NbpRateTableAToPlnExchangerTest {

    Supplier<ExchangeRatesTableDto> mockSupplier = null;

    NbpRateTableAToPlnExchanger cut = new NbpRateTableAToPlnExchanger(() -> mockSupplier.get());

    @Before
    public void clear() {
        mockSupplier = null;
    }

    @Test
    public void should_return_not_changed_PLN_money() {
        //given
        Money toExchange = Money.pln(BigDecimal.valueOf(100));
        //when
        Money exchanged = new NbpRateTableAToPlnExchanger(mockSupplier).exchange(toExchange);
        //then
        assertThat(exchanged).isEqualTo(Money.pln(BigDecimal.valueOf(100)));
    }

    @Test
    public void should_exchange_EUR_to_PLN() {
        //given
        mockSupplier = () -> ExchangeRatesTableDto.builder()
                .table("A")
                .no("040/A/NBP/2017")
                .effectiveDate("2017-02-27")
                .rates(asList(rate("euro", "EUR", "4.3135")))
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
        mockSupplier = () -> ExchangeRatesTableDto.builder()
                .table("A")
                .no("040/A/NBP/2017")
                .effectiveDate("2017-02-27")
                .rates(asList(rate("euro", "EUR", "4.3135")))
                .build();
        Money toExchange = Money.gbp(BigDecimal.valueOf(100));
        //when
        //then
        assertThatThrownBy(() -> cut.exchange(toExchange))
                .isInstanceOf(ExchangeNotSupportedException.class)
                .hasMessage("Can not exchange GBP to PLN")
                .hasNoCause();

    }

    private ExchangeRatesTableDto.RateDto rate(String currency, String code, String mid) {
        return new ExchangeRatesTableDto.RateDto(currency, code, new BigDecimal(mid));

    }

}