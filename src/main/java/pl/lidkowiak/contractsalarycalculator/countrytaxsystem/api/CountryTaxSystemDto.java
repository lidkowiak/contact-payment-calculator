package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryTaxSystemDto {

    private String countryCode;
    private String countryName;
    private String currencyCode;

}
