package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Default strategy for calculation monthly net contract salary based on daily net salary
 * that takes into consideration fixed cost and income tax.
 */
class DefaultMonthlyNetSalaryCalculationPolicy implements MonthlyNetContractSalaryCalculationPolicy {

    private final BigDecimal incomeTaxRatio;
    private final Money fixedCost;

    DefaultMonthlyNetSalaryCalculationPolicy(BigDecimal incomeTaxRatio, Money fixedCost) {
        this.incomeTaxRatio = requireNonNull(incomeTaxRatio, "Income tax ratio is required.");
        this.fixedCost = requireNonNull(fixedCost, "Fixed cost is required.");
    }

    @Override
    public Money calculate(Money dailyNetSalary) {
        requireNonNull(dailyNetSalary, "Daily net salary is required.");
        if (dailyNetSalary.isAmountLessOrEqualTo(BigDecimal.ZERO)) {
            throw new NegativeDailyNetSalaryException();
        }
        final Money incomeBeforeTaxation = dailyNetSalary.multiplyBy(WORKING_DAYS_IN_MONTH)
                .subtract(fixedCost);

        return shouldPayTax(incomeBeforeTaxation)
                ? incomeBeforeTaxation.multiplyBy(BigDecimal.ONE.subtract(incomeTaxRatio))
                : incomeBeforeTaxation;
    }

    private boolean shouldPayTax(Money incomeBeforeTaxation) {
        return incomeBeforeTaxation.isAmountGreaterThan(BigDecimal.ZERO);
    }
}
