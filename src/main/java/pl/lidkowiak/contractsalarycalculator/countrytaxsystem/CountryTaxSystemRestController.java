package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import org.springframework.web.bind.annotation.*;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.CountryTaxSystemDto;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.MoneyDto;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain.CountryTaxSystemFacade;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
class CountryTaxSystemRestController {

    private final CountryTaxSystemFacade countryTaxSystemFacade;

    CountryTaxSystemRestController(CountryTaxSystemFacade countryTaxSystemFacade) {
        this.countryTaxSystemFacade = countryTaxSystemFacade;
    }

    @GetMapping(value = "/country-tax-systems",
            consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    List<CountryTaxSystemDto> getAllCountryTasSystems() {
        return countryTaxSystemFacade.getAllSupportedCountryTaxSystems();
    }

    @PostMapping(value = "/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation",
            consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    MoneyDto calculateMonthlyNetContractSalaryInPln(@PathVariable String countryCode, @RequestBody MoneyDto dailyNetSalaryDto) {
        final Money dailyNetSalary = Money.of(dailyNetSalaryDto.getAmount(), dailyNetSalaryDto.getCurrency());
        final Money monthlyNetContractSalaryInPln = countryTaxSystemFacade
                .calculateMonthlyNetContractSalaryInPln(countryCode, dailyNetSalary);

        return MoneyDto.of(monthlyNetContractSalaryInPln.getAmount(), monthlyNetContractSalaryInPln.getCurrencyCode());
    }

}
