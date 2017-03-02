package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.ExchangeRatesTableDto;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.FetchingExchangeRatesTableException;
import pl.lidkowiak.contractsalarycalculator.nbpapiclient.NbpApiClient;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NbpApiClientTest {

    static WireMockServer wireMockServer;
    static NbpApiClient cut;

    @BeforeClass
    public static void setUpWireMock() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();

        cut = new NbpApiClient("http://localhost:" + wireMockServer.port());
    }

    @AfterClass
    public static void tearDownWireMock() {
        wireMockServer.stop();
    }

    @Before
    public void resetWireMock() {
        wireMockServer.resetAll();
    }

    @Test
    public void should_invoke_nbp_api_service_for_fetching_exchange_rates() throws Exception {
        //given
        wireMockServer.stubFor(get(urlEqualTo("/api/exchangerates/tables/A"))
                .withHeader("Accept", equalTo("application/json;charset=UTF-8"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("api_exchangerates_tables_A_27_02_2017.json")));

        //when
        ExchangeRatesTableDto exchangeRatesTable = cut.getExchangeRatesTableA();

        //then
        assertThat(exchangeRatesTable).isEqualTo(ExchangeRatesTableDto.builder()
                .table("A")
                .no("040/A/NBP/2017")
                .effectiveDate("2017-02-27")
                .rates(asList(
                        rate("bat (Tajlandia)", "THB", "0.1169"),
                        rate("dolar amerykański", "USD", "4.0745"),
                        rate("dolar australijski", "AUD", "3.1291"),
                        rate("dolar Hongkongu", "HKD", "0.525"),
                        rate("dolar kanadyjski", "CAD", "3.1066"),
                        rate("dolar nowozelandzki", "NZD", "2.9335"),
                        rate("dolar singapurski", "SGD", "2.8978"),
                        rate("euro", "EUR", "4.3135"),
                        rate("forint (Węgry)", "HUF", "0.014004"),
                        rate("frank szwajcarski", "CHF", "4.0474"),
                        rate("funt szterling", "GBP", "5.0646"),
                        rate("hrywna (Ukraina)", "UAH", "0.1506"),
                        rate("jen (Japonia)", "JPY", "0.036286"),
                        rate("korona czeska", "CZK", "0.1597"),
                        rate("korona duńska", "DKK", "0.5803"),
                        rate("korona islandzka", "ISK", "0.037854"),
                        rate("korona norweska", "NOK", "0.4878"),
                        rate("korona szwedzka", "SEK", "0.4516"),
                        rate("kuna (Chorwacja)", "HRK", "0.5802"),
                        rate("lej rumuński", "RON", "0.9558"),
                        rate("lew (Bułgaria)", "BGN", "2.2055"),
                        rate("lira turecka", "TRY", "1.1348"),
                        rate("nowy izraelski szekel", "ILS", "1.1055"),
                        rate("peso chilijskie", "CLP", "0.0063"),
                        rate("peso filipińskie", "PHP", "0.081"),
                        rate("peso meksykańskie", "MXN", "0.2051"),
                        rate("rand (Republika Południowej Afryki)", "ZAR", "0.3144"),
                        rate("real (Brazylia)", "BRL", "1.3098"),
                        rate("ringgit (Malezja)", "MYR", "0.918"),
                        rate("rubel rosyjski", "RUB", "0.0706"),
                        rate("rupia indonezyjska", "IDR", "0.00030539"),
                        rate("rupia indyjska", "INR", "0.061085"),
                        rate("won południowokoreański", "KRW", "0.003592"),
                        rate("yuan renminbi (Chiny)", "CNY", "0.5932"),
                        rate("SDR (MFW)", "XDR", "5.5092")
                ))
                .build()
        );
    }

    @Test
    public void should_throw_exception_when_service_responded_with_error() throws Exception {
        //given
        wireMockServer.stubFor(get(urlEqualTo("/api/exchangerates/tables/A"))
                .withHeader("Accept", equalTo("application/json;charset=UTF-8"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Not found")));

        //when
        //then
        assertThatThrownBy(() -> cut.getExchangeRatesTableA())
                .isInstanceOf(FetchingExchangeRatesTableException.class)
                .hasMessage("Error occurred during fetching NBP exchange rates!")
                .hasCauseInstanceOf(HttpClientErrorException.class);
    }


    private ExchangeRatesTableDto.RateDto rate(String currency, String code, String mid) {
        return new ExchangeRatesTableDto.RateDto(currency, code, new BigDecimal(mid));

    }
}