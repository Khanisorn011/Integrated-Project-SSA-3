package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class SaleItemDetailDTO {
    private Integer id;
    private String model;
    private String brandName;
    private String description;
    private Integer price;
    private Integer ramGb;
    private BigDecimal screenSizeInch;
    private Integer quantity;
    private Integer storageGb;
    private String color;

//    @JsonProperty("screenSizeInch")
//    public BigDecimal getScreenSizeInchRounded() {
//        if (screenSizeInch == null) {
//            return null;
//        }
//        return screenSizeInch.setScale(1, RoundingMode.HALF_UP);
//    }
}

