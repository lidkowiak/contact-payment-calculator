package pl.lidkowiak.contractsalarycalculator.nbpapiclient;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

/**
 * Injects NBP Web API base URL: default http://api.nbp.pl or using property nbpApiBaseUrl.
 * Inspired by {@link org.springframework.boot.context.embedded.LocalServerPort}
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${nbpApiBaseUrl:http://api.nbp.pl}")
public @interface NbpApiBaseUrl {
}