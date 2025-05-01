package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.entities.BrandBase;
import org.example.backend.entities.SaleItemBase;

@Getter
@Setter
public class SaleItemDTO {
    private String model;
    private String brandName;
    private Integer price;
    private Integer storageGb;
    private String color;

    @JsonProperty("storageGb")
    private String getStorageInGb() {
        return storageGb + "Gb";
    }
}
