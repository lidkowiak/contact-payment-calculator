package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import java.util.List;

import static java.util.stream.Collectors.toList;

class DtoAssembler {

    List<CountryTaxSystemDto> toDto(List<CountryTaxSystem> countryTaxSystems) {
        return countryTaxSystems.stream()
                .map(this::toDto)
                .collect(toList());
    }

    CountryTaxSystemDto toDto(CountryTaxSystem c) {
        return CountryTaxSystemDto.builder()
                .countryCode(c.getCountryCode())
                .countryName(c.getCountryName())
                .currencyCode(c.getCurrencyCode())
                .build();
    }

}
