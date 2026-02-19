package com.project.jwt.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConnection {
    private List<ProductEdge> edges;
    private PageInfo pageInfo;
    private Long totalCount;
}
