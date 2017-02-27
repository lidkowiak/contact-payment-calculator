package pl.lidkowiak.contractsalarycalculator.money;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    public void should_multiple_money() throws Exception {
        assertThat(Money.pln(BigDecimal.valueOf(100)).multiplyBy(10))
                .isEqualTo(Money.pln(BigDecimal.valueOf(1000)));

        assertThat(Money.pln(BigDecimal.valueOf(100)).multiplyBy(new BigDecimal("1.1")))
                .isEqualTo(Money.pln(BigDecimal.valueOf(110)));
    }

    @Test
    public void should_add_money_with_the_same_currency() throws Exception {
        assertThat(Money.eur(BigDecimal.valueOf(100)).add((Money.eur(BigDecimal.valueOf(200)))))
                .isEqualTo(Money.eur(BigDecimal.valueOf(300)));
    }

    @Test
    public void should_throw_exception_when_adding_money_with_the_different_currencies() throws Exception {
        assertThatThrownBy(() -> Money.eur(BigDecimal.valueOf(100))
                .add((Money.gbp(BigDecimal.valueOf(200)))))
                .isExactlyInstanceOf(IncompatibleCurrenciesOperationException.class)
                .hasMessage("Currencies EUR and GBP are incompatible!")
                .hasNoCause();
    }

    @Test
    public void should_subtract_money_with_the_same_currency() throws Exception {
        assertThat(Money.eur(BigDecimal.valueOf(100)).subtract((Money.eur(BigDecimal.valueOf(200)))))
                .isEqualTo(Money.eur(BigDecimal.valueOf(-100)));
    }

    @Test
    public void should_throw_exception_when_subtracting_money_with_the_different_currencies() throws Exception {
        assertThatThrownBy(() -> Money.eur(BigDecimal.valueOf(100))
                .subtract((Money.pln(BigDecimal.valueOf(200)))))
                .isExactlyInstanceOf(IncompatibleCurrenciesOperationException.class)
                .hasMessage("Currencies EUR and PLN are incompatible!")
                .hasNoCause();
    }

    @Test
    public void hasCurrency_method_truisms() throws Exception {
        assertThat(Money.eur(BigDecimal.valueOf(100)).hasCurrency(Currencies.EUR)).isTrue();
        assertThat(Money.eur(BigDecimal.valueOf(100)).hasCurrency(Currencies.PLN)).isFalse();
    }
}