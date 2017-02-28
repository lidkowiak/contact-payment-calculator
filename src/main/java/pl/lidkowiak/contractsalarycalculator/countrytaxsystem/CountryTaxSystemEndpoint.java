package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import org.springframework.web.bind.annotation.*;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.CountryTaxSystemDto;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.MoneyDto;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.util.Currency;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
class CountryTaxSystemEndpoint {

    private final CountryTaxSystemFacade countryTaxSystemFacade;

    CountryTaxSystemEndpoint(CountryTaxSystemFacade countryTaxSystemFacade) {
        this.countryTaxSystemFacade = countryTaxSystemFacade;
    }

    @GetMapping("/country-tax-systems")
    List<CountryTaxSystemDto> getAllCountryTasSystems() {
        return countryTaxSystemFacade.getAllSupportedCountryTaxSystems();
    }

    @PostMapping("/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation")
    MoneyDto calculateMonthlyNetContractSalary(@PathVariable String countryCode, @RequestBody MoneyDto dailyNetSalaryDto) {

        final Money dailyNetSalary = new Money(dailyNetSalaryDto.getAmount(), Currency.getInstance(dailyNetSalaryDto.getCurrency()));
        final Money monthlyNetContractSalaryInPln = countryTaxSystemFacade.calculateMonthlyNetContractSalaryInPln(countryCode, dailyNetSalary);

        return MoneyDto.of(monthlyNetContractSalaryInPln.getAmount(), monthlyNetContractSalaryInPln.getCurrencyCode());

    }

}
