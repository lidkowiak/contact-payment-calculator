package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyNetContractSalaryCalculationRequest {

    private BigDecimal amount;
    private String currency;


}
