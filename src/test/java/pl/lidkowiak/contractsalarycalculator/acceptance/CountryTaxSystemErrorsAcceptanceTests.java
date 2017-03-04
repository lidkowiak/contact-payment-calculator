package pl.lidkowiak.contractsalarycalculator.acceptance;

import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api.MoneyDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

public class CountryTaxSystemErrorsAcceptanceTests extends AbstractIntegrationTest {

    @Test
    public void should_return_error_when_try_to_perform_calculation_for_not_supported_country() {
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(1000), "PLN"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "FR")

                .then()

                .log().all()
                .assertThat()

                .statusCode(500)
                .contentType(JSON)
                .body("message", equalTo("FR not found!"));
    }

    @Test
    public void should_return_error_when_try_to_perform_calculation_for_country_with_different_currency() {
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(1000), "PLN"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "DE")

                .then()

                .log().all()
                .assertThat()

                .statusCode(500)
                .contentType(JSON)
                .body("message", equalTo("Currencies PLN and EUR are incompatible!"));
    }

    @Test
    public void should_return_error_when_try_to_perform_calculation_without_amount() {
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(null, "PLN"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "DE")

                .then()

                .log().all()
                .assertThat()

                .statusCode(400)
                .contentType(JSON)
                .body("message", equalTo("Amount is required!"));
    }

    @Test
    public void should_return_error_when_try_to_perform_calculation_without_currency() {
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(1000), null))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "DE")

                .then()

                .log().all()
                .assertThat()

                .statusCode(400)
                .contentType(JSON)
                .body("message", equalTo("Currency is required!"));
    }

    @Test
    public void should_return_error_when_try_to_perform_calculation_with_bad_currency_code() {
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(MoneyDto.of(BigDecimal.valueOf(1000), "Euro"))

                .when()

                .log().all()
                .post("/api/country-tax-systems/{countryCode}/monthly-pln-net-contract-salary-calculation", "DE")

                .then()

                .log().all()
                .assertThat()

                .statusCode(400)
                .contentType(JSON)
                .body("message", equalTo("Euro is not valid ISO 4217 code of the currency!"));
    }
}
