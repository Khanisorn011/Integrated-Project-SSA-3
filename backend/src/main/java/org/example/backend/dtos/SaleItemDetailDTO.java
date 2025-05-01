package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemDetailDTO {
    private String model;
    private String brandName;
    private String description;
    private Integer price;
    private Integer ramGb;
    private BigDecimal screenSizeInch;
    private Integer quantity;
    private Integer storageGb;
    private String color;

    @JsonProperty("screenSizeInch")
    private String getScreenSizeInInch() {
        return screenSizeInch + "inches";
    }

    @JsonProperty("storageGb")
    private String getStorageInGb() {
        return storageGb + "Gb";
    }

    @JsonProperty("storageGb")
    private String getRamInGb() {
        return ramGb + "Gb";
    }
}
