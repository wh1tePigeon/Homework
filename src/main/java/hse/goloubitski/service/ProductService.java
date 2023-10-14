package hse.goloubitski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import hse.goloubitski.repositories.ProductRepository;
import hse.goloubitski.entity.Product;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id) {
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }
}