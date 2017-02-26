package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import lombok.Builder;
import lombok.Getter;
import pl.lidkowiak.contractsalarycalculator.core.Money;
import pl.lidkowiak.contractsalarycalculator.salarycalculator.MonthlyNetContractSalaryCalculationPolicy;

import java.util.Currency;

@Builder
class CountryTaxSystem {

    @Getter
    private final String countryCode;

    @Getter
    private final String countryName;

    @Getter
    private final Currency currency;

    private final MonthlyNetContractSalaryCalculationPolicy monthlyNetContractSalaryCalculationPolicy;

    Money calculateMonthlyNetSalary(Money dailyNetSalary) {
        return monthlyNetContractSalaryCalculationPolicy.calculate(dailyNetSalary);
    }

    String getCurrencyCode() {
        return currency.getCurrencyCode();
    }
}
