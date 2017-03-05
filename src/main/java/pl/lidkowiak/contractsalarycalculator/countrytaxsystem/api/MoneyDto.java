package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.math.BigDecimal;

/**
 * Data transfer object for money (amount with currency).
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ApiModel("Represents money (amount with ISO 4217 standard currency code)")
public class MoneyDto {

    private BigDecimal amount;
    private String currency;

}
