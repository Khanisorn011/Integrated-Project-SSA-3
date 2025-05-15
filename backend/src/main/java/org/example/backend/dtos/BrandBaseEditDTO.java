package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandBaseEditDTO {
    private Integer id;
    private String name;
    private String websiteUrl;
    private String countryOfOrigin;
    private Boolean isActive;
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl != null ? websiteUrl.trim() : null;
    }
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin != null ? countryOfOrigin.trim() : null;
    }
}
