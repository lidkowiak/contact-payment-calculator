package pl.lidkowiak.contractsalarycalculator.money;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Immutable value object representing money, i.e. amount and currency.
 * Currency is represented using {@link Currency}.
 * It can perform basic operation, i.e. adding, subtracting and multiplying.
 */
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

    /**
     * @throws IncompatibleCurrenciesOperationException
     */
    public Money add(Money money) {
        assertSameCurrencyAs(money);
        return new Money(amount.add(money.amount), currency);
    }

    /**
     * @throws IncompatibleCurrenciesOperationException
     */
    public Money subtract(Money money) {
        assertSameCurrencyAs(money);
        return new Money(amount.subtract(money.amount), currency);
    }

    public boolean isAmountGreaterThan(BigDecimal toCompare) {
        return amount.compareTo(toCompare) > 0;
    }

    public boolean hasCurrency(Currency currency) {
        return this.currency.equals(currency);
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    private void assertSameCurrencyAs(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IncompatibleCurrenciesOperationException(currency, money.currency);
        }
    }

}
