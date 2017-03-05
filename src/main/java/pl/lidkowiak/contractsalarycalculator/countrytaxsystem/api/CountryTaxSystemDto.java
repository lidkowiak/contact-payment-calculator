package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Data transfer object for country tax system.
 */
@Getter
@Builder
@ApiModel("Represents country tax system")
public class CountryTaxSystemDto {

    @ApiModelProperty("Country code that identifies country tax system")
    private String countryCode;

    @ApiModelProperty("Country code name")
    private String countryName;

    @ApiModelProperty("Currency code of (ISO 4217 standard)")
    private String currencyCode;

}
