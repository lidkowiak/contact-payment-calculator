package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;

class InMemoryCountryTaxSystemRepository implements CountryTaxSystemRepository {

    private final Map<String, CountryTaxSystem> store;

    InMemoryCountryTaxSystemRepository(Collection<CountryTaxSystem> supportedCountryTaxSystems) {
        this.store = supportedCountryTaxSystems.stream()
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
