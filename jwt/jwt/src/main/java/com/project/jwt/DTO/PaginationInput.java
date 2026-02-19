package com.project.jwt.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInput {
    private Integer first;
    private Integer after;
    private Integer last;
    private Integer before;
    
    // For offset-based pagination
    private Integer offset = 0;
    private Integer limit = 10;
}
