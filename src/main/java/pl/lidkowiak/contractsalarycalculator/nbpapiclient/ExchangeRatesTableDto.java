package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

/**
 * NBP exchange rates table
 * {@see http://api.nbp.pl/#kursyOdp}
 */
@Data
@Builder
@AllArgsConstructor
public class ExchangeRatesTableDto {

    /**
     * Table type, i.e. A, B, C
     */
    private String table;

    /**
     * Table number
     */
    private String no;

    /**
     * Date of publication (format YYYY-MM-DD)
     */
    private String effectiveDate;

    /**
     * List of exchange rates for individual currencies in the table
     */
    private List<RateDto> rates;

    public Optional<RateDto> rateFor(Currency currency) {
        return rates.stream()
                .filter(r -> r.isForCurrency(currency))
                .findFirst();
    }

    @Data
    @AllArgsConstructor
    public static class RateDto {

        /**
         * Currency name
         */
        private String currency;

        /**
         * Currency code (ISO 4217 standard)
         */
        private String code;

        /**
         * Calculated average exchange rate for currency (applies to table A and B)
         */
        private BigDecimal mid;

        boolean isForCurrency(Currency currency) {
            return currency.getCurrencyCode().equals(code);
        }
    }
}
