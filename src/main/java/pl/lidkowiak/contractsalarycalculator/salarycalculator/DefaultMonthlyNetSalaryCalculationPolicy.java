package pl.lidkowiak.contractsalarycalculator.salarycalculator;

import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;

public class DefaultMonthlyNetSalaryCalculationPolicy implements MonthlyNetContractSalaryCalculationPolicy {

    private final BigDecimal incomeTaxRatio;
    private final Money fixedCost;

    public DefaultMonthlyNetSalaryCalculationPolicy(BigDecimal incomeTaxRatio, Money fixedCost) {
        this.incomeTaxRatio = incomeTaxRatio;
        this.fixedCost = fixedCost;
    }

    @Override
    public Money calculate(Money monthlyNetSalary) {
        return monthlyNetSalary
                .multiplyBy(WORKING_DAYS_IN_MONTH)
                .subtract(fixedCost)
                .multiplyBy(BigDecimal.ONE.subtract(incomeTaxRatio));
    }
}
