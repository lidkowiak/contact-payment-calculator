package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Client for NBP Web API
 * API documentation: http://api.nbp.pl
 */
public class NbpWebApiClient {

    private static final ParameterizedTypeReference<List<ExchangeRatesTableDto>> TYPE_REF = new ParameterizedTypeReference<List<ExchangeRatesTableDto>>() {
    };

    private final String nbpApiBaseUrl;
    private final RestOperations restOperations;

    public NbpWebApiClient(String nbpApiBaseUrl) {
        this.nbpApiBaseUrl = nbpApiBaseUrl;
        this.restOperations = new RestTemplate(); // default RestTemplate is fine
    }

    /**
     * Current exchange rate A table
     */
    public ExchangeRatesTableDto getCurrentExchangeRatesTableA() {
        try {
            final ResponseEntity<List<ExchangeRatesTableDto>> responseEntity =
                    restOperations.exchange(nbpApiBaseUrl + "/api/exchangerates/tables/A", HttpMethod.GET,
                            acceptApplicationJsonUtf8RequestEntity(), TYPE_REF);
            // service returns list of exchange rates tables which always has single item
            return responseEntity.getBody().get(0);
        } catch (RestClientException e) {
            throw new NbpWebApiException("Error occurred during fetching NBP exchange rates!", e);
        }
    }

    private HttpEntity<Void> acceptApplicationJsonUtf8RequestEntity() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(asList(MediaType.APPLICATION_JSON_UTF8));
        return new HttpEntity<>(httpHeaders);
    }

}
