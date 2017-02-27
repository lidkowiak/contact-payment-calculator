package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import org.springframework.stereotype.Repository;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.salarycalculator.DefaultMonthlyNetSalaryCalculationPolicy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;

@Repository
class ConcurrentHashMapCountryRepository implements CountryTaxSystemRepository {

    private final Map<String, CountryTaxSystem> store;

    ConcurrentHashMapCountryRepository() {
        this.store = Stream.of(
                CountryTaxSystem.builder()
                        .countryCode("UK")
                        .countryName("United Kingdom")
                        .currency(Currencies.GBP)
                        .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                                new BigDecimal("0.25"), Money.gbp(BigDecimal.valueOf(600)))
                        )
                        .build(),
                CountryTaxSystem.builder()
                        .countryCode("DE")
                        .countryName("Germany")
                        .currency(Currencies.EUR)
                        .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                                new BigDecimal("0.20"), Money.eur(BigDecimal.valueOf(800)))
                        )
                        .build(),
                CountryTaxSystem.builder()
                        .countryCode("PL")
                        .countryName("Poland")
                        .currency(Currencies.PLN)
                        .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                                new BigDecimal("0.19"), Money.pln(BigDecimal.valueOf(1200)))
                        )
                        .build())
                .collect(toConcurrentMap(CountryTaxSystem::getCountryCode, c -> c));
    }

    @Override
    public List<CountryTaxSystem> findAll() {
        return store.values().stream()
                .sorted(comparing(CountryTaxSystem::getCountryName))
                .collect(toList());
    }

    @Override
    public Optional<CountryTaxSystem> findByCountryCode(String countryCode) {
        return Optional.ofNullable(store.get(countryCode));
    }

}
