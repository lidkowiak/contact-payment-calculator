package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link CountryTaxSystem}
 */
interface CountryTaxSystemRepository {

    List<CountryTaxSystem> findAll();

    Optional<CountryTaxSystem> findByCountryCode(String countryCode);

}
