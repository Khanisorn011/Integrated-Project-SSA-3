package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandBaseCreateDTO {
    private Integer id;
    private String name;
    private String websiteUrl;
    private String countryOfOrigin;
    private Boolean isActive;
    public void setId(Integer id) {
        this.id =  null;
    }
}

