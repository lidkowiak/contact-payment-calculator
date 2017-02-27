package pl.lidkowiak.contractsalarycalculator.salarycalculator;

import pl.lidkowiak.contractsalarycalculator.money.Money;

public interface MonthlyNetContractSalaryCalculationPolicy {

    int WORKING_DAYS_IN_MONTH = 22;

    Money calculate(Money dailyNetSalary);

}
