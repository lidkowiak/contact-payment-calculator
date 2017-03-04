package pl.lidkowiak.contractsalarycalculator.money;

import java.util.Currency;

/**
 * Utility class with shortcuts for common used currencies.
 */
public final class Currencies {

    public static final Currency PLN = Currency.getInstance("PLN");
    public static final Currency EUR = Currency.getInstance("EUR");
    public static final Currency GBP = Currency.getInstance("GBP");

    private Currencies() {
        throw new IllegalStateException("Don't instantiate utility class.");
    }

}
