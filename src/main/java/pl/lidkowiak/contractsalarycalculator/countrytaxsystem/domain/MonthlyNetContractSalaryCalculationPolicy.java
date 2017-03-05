package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import pl.lidkowiak.contractsalarycalculator.money.Money;

/**
 * Strategy for calculation monthly net contract salary based on daily net salary.
 */
interface MonthlyNetContractSalaryCalculationPolicy {

    int WORKING_DAYS_IN_MONTH = 22;

    /**
     * @throws NegativeDailyNetSalaryException
     */
    Money calculate(Money dailyNetSalary);

}
