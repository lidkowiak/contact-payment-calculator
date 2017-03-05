package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * NBP exchange rates table
 * {@see http://api.nbp.pl/#kursyOdp}
 */
@Data
@Builder
@AllArgsConstructor
public class ExchangeRatesTableDto {

    private final static DateTimeFormatter EFFECTIVE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public LocalDate getEffectiveDate() {
        return LocalDate.parse(effectiveDate, EFFECTIVE_DATE_FORMATTER);
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
    }
}
