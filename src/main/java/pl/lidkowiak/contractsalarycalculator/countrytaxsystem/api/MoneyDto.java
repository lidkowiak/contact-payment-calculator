package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api;

import lombok.*;

import java.math.BigDecimal;

/**
 * Data transfer object for money (amount with currency).
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MoneyDto {

    private BigDecimal amount;
    private String currency;

}
