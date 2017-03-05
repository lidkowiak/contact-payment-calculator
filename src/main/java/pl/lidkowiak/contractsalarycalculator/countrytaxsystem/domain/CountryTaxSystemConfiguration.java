package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.NbpRateTableAToPlnExchanger;
import pl.lidkowiak.contractsalarycalculator.currencyexchange.ToPlnExchanger;
import pl.lidkowiak.contractsalarycalculator.money.Currencies;
import pl.lidkowiak.contractsalarycalculator.money.Money;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpApiBaseUrl;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpWebApiClient;
import pl.lidkowiak.contractsalarycalculator.salarycalculations.DefaultMonthlyNetSalaryCalculationPolicy;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

@Configuration
class CountryTaxSystemConfiguration {

    private static final List<CountryTaxSystem> SUPPORTED_COUNTRY_TAX_SYSTEMS = asList(
            CountryTaxSystem.builder()
                    .countryCode("UK")
                    .countryName("United Kingdom")
                    .currency(Currencies.GBP)
                    .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                            new BigDecimal("0.25"), Money.gbp(BigDecimal.valueOf(600))))
                    .build(),
            CountryTaxSystem.builder()
                    .countryCode("DE")
                    .countryName("Germany")
                    .currency(Currencies.EUR)
                    .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                            new BigDecimal("0.20"), Money.eur(BigDecimal.valueOf(800))))
                    .build(),
            CountryTaxSystem.builder()
                    .countryCode("PL")
                    .countryName("Poland")
                    .currency(Currencies.PLN)
                    .monthlyNetContractSalaryCalculationPolicy(new DefaultMonthlyNetSalaryCalculationPolicy(
                            new BigDecimal("0.19"), Money.pln(BigDecimal.valueOf(1200)))
                    )
                    .build()
    );

    @Bean
    CountryTaxSystemRepository countryTaxSystemRepository() {
        return new InMemoryCountryTaxSystemRepository(SUPPORTED_COUNTRY_TAX_SYSTEMS);
    }

    @Bean
    CountryTaxSystemFacade countryTaxSystemFacade(CountryTaxSystemRepository countryTaxSystemRepository, @NbpApiBaseUrl String nbpApiBaseUrl) {
        final ToPlnExchanger toPlnExchanger = new NbpRateTableAToPlnExchanger(new NbpWebApiClient(nbpApiBaseUrl));
        return new CountryTaxSystemFacade(countryTaxSystemRepository, toPlnExchanger);
    }
}
