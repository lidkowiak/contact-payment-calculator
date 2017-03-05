package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.CountryTaxSystemDto;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.ToPlnExchanger;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.salarycalculations.NegativeDailyNetSalaryException;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Public facade for country tax system domain.
 */
public class CountryTaxSystemFacade {

    private final CountryTaxSystemRepository repository;
    private final ToPlnExchanger toPlnExchanger;

    CountryTaxSystemFacade(CountryTaxSystemRepository repository, ToPlnExchanger toPlnExchanger) {
        this.repository = repository;
        this.toPlnExchanger = toPlnExchanger;
    }

    public List<CountryTaxSystemDto> getAllSupportedCountryTaxSystems() {
        final List<CountryTaxSystem> countryTaxSystems = repository.findAll();
        return toDto(countryTaxSystems);
    }

    /**
     * @throws CountryTaxSystemNotFoundException
     * @throws NegativeDailyNetSalaryException
     */
    public Money calculateMonthlyNetContractSalaryInPln(String countryCode, Money dailyNetSalary) {
        final Money calculateMonthlyNetSalary = calculateMonthlyNetContractSalary(countryCode, dailyNetSalary);
        final Money calculateMonthlyNetSalaryPln = toPlnExchanger.exchange(calculateMonthlyNetSalary);
        return calculateMonthlyNetSalaryPln;
    }

    /**
     * @throws CountryTaxSystemNotFoundException
     * @throws NegativeDailyNetSalaryException
     */
    public Money calculateMonthlyNetContractSalary(String countryCode, Money dailyNetSalary) {
        final CountryTaxSystem countryTaxSystem = repository.findByCountryCode(countryCode)
                .orElseThrow(() -> new CountryTaxSystemNotFoundException(countryCode));

        final Money calculateMonthlyNetSalary = countryTaxSystem.calculateMonthlyNetSalary(dailyNetSalary);
        final Money calculateMonthlyNetSalaryPln = toPlnExchanger.exchange(calculateMonthlyNetSalary);
        return calculateMonthlyNetSalaryPln;
    }

    private List<CountryTaxSystemDto> toDto(List<CountryTaxSystem> countryTaxSystems) {
        return countryTaxSystems.stream()
                .map(this::toDto)
                .collect(toList());
    }

    private CountryTaxSystemDto toDto(CountryTaxSystem c) {
        return CountryTaxSystemDto.builder()
                .countryCode(c.getCountryCode())
                .countryName(c.getCountryName())
                .currencyCode(c.getCurrencyCode())
                .build();
    }
}
