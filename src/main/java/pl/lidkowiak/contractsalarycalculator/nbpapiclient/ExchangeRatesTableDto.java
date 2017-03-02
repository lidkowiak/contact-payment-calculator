package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class ExchangeRatesTableDto {
    /**
     * Typ tabeli
     */
    private String table;

    /**
     * Numer tabeli
     */
    private String no;

    /**
     * Data publikacji (format RRRR-MM-DD)
     */
    private String effectiveDate;

    /**
     * Lista kursów poszczególnych walut w tabeli
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
         * Nazwa waluty
         */
        private String currency;

        /**
         * Kod waluty (standard ISO 4217)
         */
        private String code;

        /**
         * Przeliczony kurs średni waluty (dotyczy tabel A oraz B)
         */
        private BigDecimal mid;

        boolean isForCurrency(Currency currency) {
            return currency.getCurrencyCode().equals(code);
        }
    }
}
