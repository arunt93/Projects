package com.project.jwt.service;

import com.project.jwt.DTO.*;
import com.project.jwt.model.Product;
import com.project.jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    public Product createProduct(ProductInput input) {
        validateProductInput(input, true);
        
        Product product = Product.builder()
                .name(input.getName())
                .description(input.getDescription())
                .price(input.getPrice())
                .stock(input.getStock())
                .build();
        
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, ProductInput input) {
        Product existingProduct = getProductById(id);
        
        if (input.hasName()) {
            existingProduct.setName(input.getName());
        }
        if (input.hasDescription()) {
            existingProduct.setDescription(input.getDescription());
        }
        if (input.hasPrice()) {
            existingProduct.setPrice(input.getPrice());
        }
        if (input.hasStock()) {
            existingProduct.setStock(input.getStock());
        }
        
        return productRepository.save(existingProduct);
    }
    
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }
    
    // Cursor-based pagination
    public ProductConnection getProducts(Integer first, String after) {
        int pageSize = first != null ? first : 10;
        int offset = 0;
        
        if (after != null) {
            offset = decodeCursor(after) + 1;
        }
        
        Pageable pageable = PageRequest.of(offset / pageSize, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        
        List<ProductEdge> edges = productPage.getContent().stream()
                .map(product -> new ProductEdge(
                    encodeCursor(product.getId()),
                    product
                ))
                .collect(Collectors.toList());
        
        PageInfo pageInfo = new PageInfo(
            productPage.hasNext(),
            productPage.hasPrevious(),
            edges.isEmpty() ? null : edges.get(0).getCursor(),
            edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor()
        );
        
        return new ProductConnection(edges, pageInfo, productPage.getTotalElements());
    }
    
    // Offset-based pagination
    public ProductConnection getProductsPaginated(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Product> productPage = productRepository.findAll(pageable);
        
        List<ProductEdge> edges = productPage.getContent().stream()
                .map(product -> new ProductEdge(
                    encodeCursor(product.getId()),
                    product
                ))
                .collect(Collectors.toList());
        
        PageInfo pageInfo = new PageInfo(
            productPage.hasNext(),
            productPage.hasPrevious(),
            edges.isEmpty() ? null : edges.get(0).getCursor(),
            edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor()
        );
        
        return new ProductConnection(edges, pageInfo, productPage.getTotalElements());
    }
    
    private String encodeCursor(Long id) {
        return Base64.getEncoder().encodeToString(id.toString().getBytes());
    }
    
    private int decodeCursor(String cursor) {
        byte[] decodedBytes = Base64.getDecoder().decode(cursor);
        return Integer.parseInt(new String(decodedBytes));
    }
    
    private void validateProductInput(ProductInput input, boolean isCreate) {
        if (isCreate) {
            if (input.getName() == null || input.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Product name is required");
            }
            if (input.getPrice() == null || input.getPrice() <= 0) {
                throw new IllegalArgumentException("Product price must be greater than 0");
            }
            if (input.getStock() == null || input.getStock() < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative");
            }
        } else {
            // For updates, validate only if values are provided
            if (input.hasPrice() && input.getPrice() <= 0) {
                throw new IllegalArgumentException("Product price must be greater than 0");
            }
            if (input.hasStock() && input.getStock() < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative");
            }
        }
    }
}
