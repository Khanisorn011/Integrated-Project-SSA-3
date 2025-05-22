package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageRequestDTO {
    private ArrayList<String> filterBrands;
    private Integer page;
    private Integer size;
    private String sortField;
    private String sortDirection;

    public void setFilterBrands(ArrayList<String> filterBrands) {
        this.filterBrands = filterBrands != null ? filterBrands : new ArrayList<String>();
    }

    public void setSize(Integer size) {
        this.size = size != null && size > 0 ? size : 10;
    }

    public void setSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.isEmpty()) {
            this.sortDirection = "ASC";
            return;
        }

        String direction = sortDirection.toUpperCase();
        if (direction.equals("ASC") || direction.equals("DESC")) {
            this.sortDirection = direction;
        } else {
            this.sortDirection = "ASC";
        }
    }


}
