package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class CountryTaxSystemDto {

    private String countryCode;
    private String countryName;
    private String currencyCode;

}
