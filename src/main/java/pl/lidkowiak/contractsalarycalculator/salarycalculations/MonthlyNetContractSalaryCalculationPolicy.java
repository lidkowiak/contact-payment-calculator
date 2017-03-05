package pl.lidkowiak.contractsalarycalculator.salarycalculations;

import pl.lidkowiak.contractsalarycalculator.money.Money;

/**
 * Strategy for calculation monthly net contract salary based on daily net salary.
 */
public interface MonthlyNetContractSalaryCalculationPolicy {

    int WORKING_DAYS_IN_MONTH = 22;

    /**
     * @throws NegativeDailyNetSalaryException
     */
    Money calculate(Money dailyNetSalary);

}
