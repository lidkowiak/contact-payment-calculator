package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import lombok.Builder;
import lombok.Getter;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.util.Currency;

/**
 * Entity representing tax system of country, especially strategy for calculating contract monthly net salary.
 * Entity is identified by country code.
 */
@Builder
class CountryTaxSystem {

    @Getter
    private final String countryCode;

    @Getter
    private final String countryName;

    private final Currency currency;

    private final MonthlyNetContractSalaryCalculationPolicy monthlyNetContractSalaryCalculationPolicy;

    Money calculateMonthlyNetSalary(Money dailyNetSalary) {
        return monthlyNetContractSalaryCalculationPolicy.calculate(dailyNetSalary);
    }

    String getCurrencyCode() {
        return currency.getCurrencyCode();
    }
}
