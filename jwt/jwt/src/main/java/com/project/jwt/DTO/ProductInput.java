package com.project.jwt.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    
    // Helper method to check if field has value (for updates)
    public boolean hasName() {
        return name != null;
    }
    
    public boolean hasDescription() {
        return description != null;
    }
    
    public boolean hasPrice() {
        return price != null;
    }
    
    public boolean hasStock() {
        return stock != null;
    }
}
