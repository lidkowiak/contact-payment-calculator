package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

@Slf4j
@Ignore("Depends on http://api.nbp.pl/api/exchangerates/tables/A service")
public class NbpWebApiClientManualTest {

    @Test
    public void should_invoke_nbp_api_service_for_fetching_exchange_rates() throws Exception {
        NbpWebApiClient cut = new NbpWebApiClient("http://api.nbp.pl/api/exchangerates/tables/A");
        log.info("{}", cut.getCurrentExchangeRatesTableA());
    }
}