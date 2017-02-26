package pl.lidkowiak.contractsalarycalculator.core;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Currency;

public class Money {

    public static class IncompatibleCurrenciesException extends RuntimeException {

        public IncompatibleCurrenciesException(Currency currency1, Currency currency2) {
            super(MessageFormat.format("Currencies {0} and {1} are incompatible!", currency1, currency2));
        }

    }

    private BigDecimal amount;
    private Currency currency;

    public static Money pln(BigDecimal amount) {
        return new Money(amount, Currencies.PLN);
    }

    public static Money eur(BigDecimal amount) {
        return new Money(amount, Currencies.EUR);
    }

    public static Money gbr(BigDecimal amount) {
        return new Money(amount, Currencies.GBP);
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
    }

    public Money multiplyBy(int multiplier) {
        return multiplyBy(BigDecimal.valueOf(multiplier));
    }

    public Money multiplyBy(BigDecimal multiplicand) {
        return new Money(amount.multiply(multiplicand), currency);
    }

    public Money add(Money money) {
        assertCompatibleCurrencies(money);
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(Money money) {
        assertCompatibleCurrencies(money);
        return new Money(amount.subtract(money.amount), currency);
    }

    private void assertCompatibleCurrencies(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IncompatibleCurrenciesException(currency, money.currency);
        }
    }

    public Currency getCurrency() {
        return currency;
    }

}
