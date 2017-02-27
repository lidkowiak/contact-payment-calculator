package pl.lidkowiak.contractsalarycalculator.currencyexchange.nbpexchangeratetable;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

class NbpExchangeRateTableASupplier implements Supplier<ExchangeRatesTableDto> {

    private static final ParameterizedTypeReference<List<ExchangeRatesTableDto>> TYPE_REF = new ParameterizedTypeReference<List<ExchangeRatesTableDto>>() {
    };

    private final String nbpApiExchangeRatesTableUrl;
    private final RestOperations restOperations;

    NbpExchangeRateTableASupplier() {
        this("http://api.nbp.pl/api/exchangerates/tables/A");
    }

    NbpExchangeRateTableASupplier(String nbpApiExchangeRatesTableUrl) {
        this.nbpApiExchangeRatesTableUrl = nbpApiExchangeRatesTableUrl;
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
