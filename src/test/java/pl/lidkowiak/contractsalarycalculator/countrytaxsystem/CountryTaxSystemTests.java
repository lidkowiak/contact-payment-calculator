package pl.lidkowiak.contractsalarycalculator.countrytaxsystem;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CountryTaxSystemTests {

    @LocalServerPort
    int port;

    @Before
    public void setUpRestAssuredWithRandomPort() {
        RestAssured.port = port;
    }

    @Test
    public void should_get_all_three_defined_country_tax_system() {
        given()
                .contentType(JSON)
                .accept(JSON)

                .when()

                .log().all()
                .get("/country-tax-systems")

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
    public void should_return_error_when_try_to_perform_calculation_for_not_supported_country() {
        given()
                .contentType(JSON)
                .accept(JSON)
.body(MonthlyNetContractSalaryCalculationRequest.builder().amount(BigDecimal.valueOf(1000)).currency("PLN").build())
                .when()

                .log().all()
                .post("/country-tax-systems/{countryCode}/monthly-net-contract-salary-calculation", "FR")


                .then()

                .log().all()
                .assertThat()

                .statusCode(500)
                .contentType(JSON)
                .body("message", equalTo("FR is not supported!"));
    }

}
