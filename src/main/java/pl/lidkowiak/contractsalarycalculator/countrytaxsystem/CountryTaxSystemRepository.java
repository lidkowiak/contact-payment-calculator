package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import java.util.List;
import java.util.Optional;

interface CountryTaxSystemRepository {

    List<CountryTaxSystem> findAll();

    Optional<CountryTaxSystem> findByCountryCode(String countryCode);

}
