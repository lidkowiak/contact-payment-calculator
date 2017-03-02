package pl.lidkowiak.contractsalarycalculator.currencyexchange.nbpexchangeratetable;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${nbpApiBaseUrl:http://api.nbp.pl}")
public @interface NbpApiBaseUrl {
}