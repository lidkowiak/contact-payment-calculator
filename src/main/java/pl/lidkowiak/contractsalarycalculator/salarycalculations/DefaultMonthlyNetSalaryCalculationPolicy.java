package pl.lidkowiak.contractsalarycalculator.salarycalculations;

import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;

/**
 * Default strategy for calculation monthly net contract salary based on daily net salary
 * that takes into consideration fixed cost and income tax.
 */
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
