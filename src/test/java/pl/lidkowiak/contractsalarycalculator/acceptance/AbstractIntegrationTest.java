package pl.lidkowiak.contractsalarycalculator.acceptance;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lidkowiak.contractsalarycalculator.Application;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = AbstractIntegrationTest.Config.class)
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    int port;

    @Before
    public void setUpRestAssuredWithRandomPort() {
        RestAssured.port = port;
    }

    static WireMockServer wireMockServer;

    @BeforeClass
    public static void setUpWireMock() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8888));
        wireMockServer.start();
    }

    @AfterClass
    public static void tearDownWireMock() {
        wireMockServer.stop();
    }

    @Before
    public void resetWireMock() {
        wireMockServer.resetAll();
    }

    @Configuration
    @Import(Application.class)
    public static class Config {

        /**
         * Forces to use Tomcat as a servlet container instead of Jetty that comes from Wiremock
         */
        @Bean
        TomcatEmbeddedServletContainerFactory tomcat() {
            return new TomcatEmbeddedServletContainerFactory();
        }
    }

}
