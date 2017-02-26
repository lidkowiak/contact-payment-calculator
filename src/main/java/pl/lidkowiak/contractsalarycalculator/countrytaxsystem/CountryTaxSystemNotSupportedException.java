package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import java.text.MessageFormat;

public class CountryTaxSystemNotSupportedException extends RuntimeException {

    public CountryTaxSystemNotSupportedException(String countryCode) {
        super(MessageFormat.format("{0} is not supported!", countryCode));
    }

}
