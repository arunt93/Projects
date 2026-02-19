package com.project.jwt.graphql;

import com.project.jwt.DTO.ProductInput;
import com.project.jwt.DTO.ProductConnection;
import com.project.jwt.model.Product;
import com.project.jwt.service.ProductService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    
    private final ProductService productService;
    
    // Query methods - only GraphQL concerns
    public List<Product> allProducts() {
        return productService.getAllProducts();
    }

    public Product product(Long id) {
        return productService.getProductById(id);
    }
    
    // Cursor-based pagination
    public ProductConnection products(Integer first, String after) {
        return productService.getProducts(first, after);
    }
    
    // Offset-based pagination
    public ProductConnection productsPaginated(Integer offset, Integer limit) {
        return productService.getProductsPaginated(
            offset != null ? offset : 0, 
            limit != null ? limit : 10
        );
    }
    
    // Mutation methods - only GraphQL concerns
    public Product createProduct(ProductInput input) {
        return productService.createProduct(input);
    }
    
    public Product updateProduct(Long id, ProductInput input) {
        return productService.updateProduct(id, input);
    }
    
    public Boolean deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }
}
