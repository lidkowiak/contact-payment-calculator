package pl.lidkowiak.contractsalarycalculator.exchangerate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Ignore("")
public class PlayWithNbpApiTest {

    @Test
    public void comsume_nbp_api() {
        RestOperations restOperations = new RestTemplate();
        ParameterizedTypeReference<List<ExchangeRatesTable>> ref = new ParameterizedTypeReference<List<ExchangeRatesTable>>() {
        };

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(asList(MediaType.APPLICATION_JSON_UTF8));
        ResponseEntity<List<ExchangeRatesTable>> result = restOperations.exchange("http://api.nbp.pl/api/exchangerates/tables/A", HttpMethod.GET,
                new HttpEntity<Void>(httpHeaders), ref);
        log.info("{}",result.getBody());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    static class ExchangeRatesTable {
        String table;
        String no;
        String effectiveDate;
        List<Rate> rates;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    static class Rate {
        String currency;
        String code;
        BigDecimal mid;
    }
}
