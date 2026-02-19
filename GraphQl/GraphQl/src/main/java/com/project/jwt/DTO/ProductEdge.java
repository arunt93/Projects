package com.project.jwt.DTO;

import com.project.jwt.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEdge {
    private String cursor;
    private Product node;
}
