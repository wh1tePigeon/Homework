package hse.goloubitski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hse.goloubitski.entity.Product;
import hse.goloubitski.entity.Review;
import hse.goloubitski.service.ProductService;
import hse.goloubitski.service.ReviewService;
import hse.goloubitski.service.ProductNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        double averageRating = reviewService.getAverageRating(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("average rating", averageRating);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/review")
    public ResponseEntity<Review> addReview(@PathVariable Long productId, @RequestBody Review review) {
        Review createdReview = reviewService.createReview(productId, review);
        return ResponseEntity.ok(createdReview);
    }
}
