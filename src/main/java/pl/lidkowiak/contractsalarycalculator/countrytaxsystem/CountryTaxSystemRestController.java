package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.CountryTaxSystemDto;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.MoneyDto;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain.CountryTaxSystemFacade;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.text.MessageFormat;
import java.util.Currency;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
class CountryTaxSystemRestController {

    private final CountryTaxSystemFacade countryTaxSystemFacade;

    CountryTaxSystemRestController(CountryTaxSystemFacade countryTaxSystemFacade) {
        this.countryTaxSystemFacade = countryTaxSystemFacade;
    }

    @GetMapping(value = "/api/country-tax-systems", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Returns all supported country tax system")
    List<CountryTaxSystemDto> getAllCountryTasSystems() {
        return countryTaxSystemFacade.getAllSupportedCountryTaxSystems();
    }

    @PostMapping(value = "/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation",
            consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("Calculates monthly bet contract salary in PLN based on daily net salary in provided country currency")
    MoneyDto calculateMonthlyNetContractSalaryInPln(@PathVariable @ApiParam("Country code") String countryCode,
                                                    @RequestBody @ApiParam("Daily net salary in provided country currency") MoneyDto dailyNetSalaryDto) {
        final Money dailyNetSalary = validateAndGetMoney(dailyNetSalaryDto);
        final Money monthlyNetContractSalaryInPln = countryTaxSystemFacade
                .calculateMonthlyNetContractSalaryInPln(countryCode, dailyNetSalary);

        return MoneyDto.of(monthlyNetContractSalaryInPln.getAmount(), monthlyNetContractSalaryInPln.getCurrencyCode());
    }

    Money validateAndGetMoney(MoneyDto moneyDto) {
        if (isNull(moneyDto.getAmount())) {
            throw new BadRequestException("Amount is required!");
        }
        if (isNull(moneyDto.getCurrency())) {
            throw new BadRequestException("Currency is required!");
        }

        final Currency currency = Currencies.getSafeCurrency(moneyDto.getCurrency())
                .orElseThrow(() -> new BadRequestException("{0} is not valid ISO 4217 code of the currency!", moneyDto.getCurrency()));

        return Money.of(moneyDto.getAmount(), currency);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequestException extends RuntimeException {
        BadRequestException(String message) {
            super(message);
        }

        BadRequestException(String messagePattern, Object... messageArgs) {
            super(MessageFormat.format(messagePattern, messageArgs));
        }
    }

}
