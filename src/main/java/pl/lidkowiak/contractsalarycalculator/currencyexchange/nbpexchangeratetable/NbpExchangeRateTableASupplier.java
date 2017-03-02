package pl.lidkowiak.contractsalarycalculator.currencyexchange.nbpexchangeratetable;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class NbpExchangeRateTableASupplier implements Supplier<ExchangeRatesTableDto> {

    private static final String EXCHANGE_RATES_TABLE_URL = "/api/exchangerates/tables/A";

    private static final ParameterizedTypeReference<List<ExchangeRatesTableDto>> TYPE_REF = new ParameterizedTypeReference<List<ExchangeRatesTableDto>>() {
    };

    private final String nbpApiExchangeRatesTableUrl;
    private final RestOperations restOperations;

    public NbpExchangeRateTableASupplier(String nbpApiBaseUrl) {
        this.nbpApiExchangeRatesTableUrl = nbpApiBaseUrl + EXCHANGE_RATES_TABLE_URL;
        this.restOperations = new RestTemplate();
    }

    @Override
    public ExchangeRatesTableDto get() {
        return getExchangeRatesTable().get(0);
    }

    private List<ExchangeRatesTableDto> getExchangeRatesTable() {
        try {
            final ResponseEntity<List<ExchangeRatesTableDto>> responseEntity =
                    restOperations.exchange(nbpApiExchangeRatesTableUrl, HttpMethod.GET, requestEntity(), TYPE_REF);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            throw new FetchingExchangeRatesTableException(e);
        }
    }

    private HttpEntity<Void> requestEntity() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(asList(MediaType.APPLICATION_JSON_UTF8));
        return new HttpEntity<>(httpHeaders);
    }

}
