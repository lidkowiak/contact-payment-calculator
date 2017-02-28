package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api;

import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MoneyDto {

    private BigDecimal amount;
    private String currency;

}
