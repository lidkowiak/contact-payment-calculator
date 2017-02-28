package pl.lidkowiak.contractsalarycalculator.money;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Getter
@EqualsAndHashCode
@ToString
public class Money {

    private final BigDecimal amount;
    private final Currency currency;

    public static Money pln(BigDecimal amount) {
        return new Money(amount, Currencies.PLN);
    }

    public static Money eur(BigDecimal amount) {
        return new Money(amount, Currencies.EUR);
    }

    public static Money gbp(BigDecimal amount) {
        return new Money(amount, Currencies.GBP);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
    }

    public Money multiplyBy(int multiplicand) {
        return multiplyBy(BigDecimal.valueOf(multiplicand));
    }

    public Money multiplyBy(BigDecimal multiplicand) {
        return new Money(amount.multiply(multiplicand), currency);
    }

    public Money add(Money money) {
        assertSameCurrencyAs(money);
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(Money money) {
        assertSameCurrencyAs(money);
        return new Money(amount.subtract(money.amount), currency);
    }

    private void assertSameCurrencyAs(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IncompatibleCurrenciesOperationException(currency, money.currency);
        }
    }

    public boolean hasCurrency(Currency currency) {
        return this.currency.equals(currency);
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

}
