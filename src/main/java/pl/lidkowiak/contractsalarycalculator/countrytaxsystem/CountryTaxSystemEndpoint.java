package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import org.springframework.web.bind.annotation.*;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.util.Currency;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
class CountryTaxSystemEndpoint {

    private final CountryTaxSystemRepository countryTaxSystemRepository;
    private final DtoAssembler dtoAssembler;

    CountryTaxSystemEndpoint(CountryTaxSystemRepository countryTaxSystemRepository) {
        this.countryTaxSystemRepository = countryTaxSystemRepository;
        this.dtoAssembler = new DtoAssembler();
    }

    @GetMapping("/country-tax-systems")
    List<CountryTaxSystemDto> getAllCountryTasSystems() {
        final List<CountryTaxSystem> countryTaxSystems = countryTaxSystemRepository.findAll();
        return dtoAssembler.toDto(countryTaxSystems);
    }

    @PostMapping("/country-tax-systems/{countryCode}/monthly-net-contract-salary-calculation")
    MonthlyNetContractSalaryCalculationResult calculateMonthlyNetContractSalary(@PathVariable String countryCode,
                                                                                @RequestBody MonthlyNetContractSalaryCalculationRequest calculationRequest) {
        final CountryTaxSystem countryTaxSystem = countryTaxSystemRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new CountryTaxSystemNotSupportedException(countryCode));

        countryTaxSystem.calculateMonthlyNetSalary(new Money(calculationRequest.getAmount(), Currency.getInstance(calculationRequest.getCurrency())));
        return null;
    }


}
