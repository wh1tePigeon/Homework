package hse.goloubitski.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.OptionalDouble;
import hse.goloubitski.repositories.ProductRepository;
import hse.goloubitski.repositories.ReviewRepository;
import hse.goloubitski.entity.Product;
import hse.goloubitski.entity.Review;
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public Review createReview(Long productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        review.setProduct(product);
        return reviewRepository.save(review);
    }

    public double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        OptionalDouble averageRating = reviews.stream().mapToInt(Review::getRating).average();
        return averageRating.orElse(0.0);
    }
}
