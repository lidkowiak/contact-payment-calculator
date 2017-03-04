package pl.lidkowiak.contractsalarycalculator.acceptance;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.MoneyDto;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class CountryTaxSystemHappyPathAcceptanceTests extends AbstractIntegrationTest {

    @Test
    public void should_get_all_three_defined_country_tax_system() {
        given()
                .contentType(JSON)
                .accept(JSON)

                .when()

                .log().all()
                .get("/api/country-tax-systems")

                .then()

                .log().all()
                .assertThat()

                .statusCode(200)
                .contentType(JSON)

                .body("$", hasSize(3))

                .body("[0].countryCode", equalTo("DE"))
                .body("[0].countryName", equalTo("Germany"))
                .body("[0].currencyCode", equalTo("EUR"))

                .body("[1].countryCode", equalTo("PL"))
                .body("[1].countryName", equalTo("Poland"))
                .body("[1].currencyCode", equalTo("PLN"))

                .body("[2].countryCode", equalTo("UK"))
                .body("[2].countryName", equalTo("United Kingdom"))
                .body("[2].currencyCode", equalTo("GBP"));
    }

    @Test
    public void should_calculate_monthly_net_salary_for_United_Kingdom() {
        wireMockServer.stubFor(get(urlEqualTo("/api/exchangerates/tables/A"))
                .withHeader("Accept", WireMock.equalTo("application/json;charset=UTF-8"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("api_exchangerates_tables_A_27_02_2017_EUR_GBR.json")));

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(200), "GBP"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "UK")

                .then()

                .log().all()
                .assertThat()

                .statusCode(200)
                .contentType(JSON)
                .body("amount", equalTo(new BigDecimal("14434.11")))
                .body("currency", equalTo("PLN"));
    }

    @Test
    public void should_calculate_monthly_net_salary_for_Poland() {
        wireMockServer.stubFor(get(urlEqualTo("/api/exchangerates/tables/A"))
                .withHeader("Accept", WireMock.equalTo("application/json;charset=UTF-8"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("api_exchangerates_tables_A_27_02_2017_EUR_GBR.json")));

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(1000), "PLN"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "PL")


                .then()

                .log().all()
                .assertThat()

                .statusCode(200)
                .contentType(JSON)
                .body("amount", equalTo(new BigDecimal("16848.00")))
                .body("currency", equalTo("PLN"));
    }

    @Test
    public void should_calculate_monthly_net_salary_for_Germany() {
        wireMockServer.stubFor(get(urlEqualTo("/api/exchangerates/tables/A"))
                .withHeader("Accept", WireMock.equalTo("application/json;charset=UTF-8"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("api_exchangerates_tables_A_27_02_2017_EUR_GBR.json")));

        given()
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(300), "EUR"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "DE")


                .then()

                .log().all()
                .assertThat()

                .statusCode(200)
                .contentType(JSON)
                .body("amount", equalTo(new BigDecimal("20014.64")))
                .body("currency", equalTo("PLN"));
    }
    
}
