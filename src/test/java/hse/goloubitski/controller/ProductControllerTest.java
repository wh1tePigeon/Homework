package hse.goloubitski.controller;

import hse.goloubitski.repositories.ProductRepository;
import hse.goloubitski.service.ProductService;
import hse.goloubitski.service.ReviewService;
import hse.goloubitski.entity.Product;
import hse.goloubitski.entity.Review;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ReviewService reviewService;


    @Test
    public void createProductTest() throws Exception {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setDescription("Description 1");
        Mockito.when(productService.createProduct(product)).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"description\": \"Description 1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByIdTest() throws Exception {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setDescription("Description 1");
        Mockito.when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception {
        Long productId = 1L;
        Mockito.doNothing().when(productService).deleteProduct(productId);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addReviewTest() throws Exception {
        Long id = 1L;
        Review review = new Review();
        review.setMessage("Review 1");
        review.setRating(5);
        review.setId(id);
        Mockito.when(reviewService.createReview(id, review)).thenReturn(review);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product/{productId}/review", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"rating\": 5, \"message\": \"Review 1\"}"))
                .andExpect(status().isOk());
    }

}
