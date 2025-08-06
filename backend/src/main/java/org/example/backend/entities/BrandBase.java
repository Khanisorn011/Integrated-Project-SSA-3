package org.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BRAND_BASE")
public class BrandBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Size(max = 40)
    @Column(name = "websiteUrl", length = 40)
    private String websiteUrl;

    @Column(name = "isActive")
    private Boolean isActive;

    @Size(max = 80)
    @Column(name = "countryOfOrigin", length = 80)
    private String countryOfOrigin;

    @Column(name = "createdOn", nullable = false, updatable = false, insertable = false)
    private Instant createdOn;

    @Column(name = "updatedOn", nullable = false, updatable = false, insertable = false)
    private Instant updatedOn;

    @OneToMany(mappedBy = "brand")
    private Set<SaleItemBase> saleItemBases = new LinkedHashSet<>();


    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl != null ? websiteUrl.trim() : null;
    }
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin != null ? countryOfOrigin.trim() : null;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = (isActive != null) ? isActive : true;
    }

}
