package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;
import java.util.Optional;

/**
 * Value object represents exchange rate table
 */
@Builder
@EqualsAndHashCode
@ToString
public class ExchangeRateTable {

    @Getter
    private LocalDate publicationDate;
    private Map<Currency, BigDecimal> table;

    public Optional<BigDecimal> exchangeRateFor(Currency currency) {
        return Optional.ofNullable(table.get(currency));
    }


}
