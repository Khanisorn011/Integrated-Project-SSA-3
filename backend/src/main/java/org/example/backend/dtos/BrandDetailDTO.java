package org.example.backend.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BrandDetailDTO {
    private Integer id;
    private String name;
    private String websiteUrl;
    private String countryOfOrigin;
    private Boolean isActive;
    private Instant createdOn;
    private Instant updatedOn;
}
