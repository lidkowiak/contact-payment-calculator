package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import java.text.MessageFormat;

/**
 * Signals that tax system for provided country code was not found (is not supported).
 */
public class CountryTaxSystemNotFoundException extends RuntimeException {

    CountryTaxSystemNotFoundException(String countryCode) {
        super(MessageFormat.format("{0} not found!", countryCode));
    }

}
