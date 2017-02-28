package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import java.text.MessageFormat;

class CountryTaxSystemNotFoundException extends RuntimeException {

    CountryTaxSystemNotFoundException(String countryCode) {
        super(MessageFormat.format("{0} not found!", countryCode));
    }

}
