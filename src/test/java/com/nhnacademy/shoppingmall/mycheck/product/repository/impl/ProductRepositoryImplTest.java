package com.nhnacademy.shoppingmall.mycheck.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ProductRepositoryImplTest {
    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testProduct = new Product(1, 1, "123", "s123", "/resources/images/monitor1.jpg", 1234, "삼성모니터입니다");
        productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("product 조회 by productId")
    void findById() {
        Optional<Product> userOptional = productRepository.findById(testProduct.getProductID());
        Assertions.assertEquals(testProduct, userOptional.get());
    }


    @Test
    @Order(2)
    @DisplayName("Save a new product")
    void save() throws SQLException {
        Product newProduct = new Product(2, 2, "456", "s456", "/resources/images/monitor2.jpg", 5678, "새로운 모니터");
        int result = productRepository.save(newProduct);


        Assertions.assertEquals(1, result);

        Optional<Product> retrievedProduct = productRepository.findById(newProduct.getProductID());

        Assertions.assertTrue(retrievedProduct.isPresent());
        Assertions.assertEquals(newProduct, retrievedProduct.get());
    }

    @Test
    @Order(3)
    @DisplayName("Delete a product by productId")
    void deleteByProductId() {
        int result = productRepository.deleteByProductId(testProduct.getProductID());
        Assertions.assertEquals(1, result);
        Optional<Product> deletedProduct = productRepository.findById(testProduct.getProductID());
        Assertions.assertFalse(deletedProduct.isPresent());
    }

    @Test
    @Order(4)
    @DisplayName("Update a product")
    void update() throws SQLException {
        testProduct.setModelName("Updated Model");
        testProduct.setUnitCost(9999);

        int result = productRepository.update(testProduct);

        Assertions.assertEquals(1, result);
        Optional<Product> updatedProduct = productRepository.findById(testProduct.getProductID());

        Assertions.assertTrue(updatedProduct.isPresent());
        Assertions.assertEquals("Updated Model", updatedProduct.get().getModelName());
        Assertions.assertEquals(9999, updatedProduct.get().getUnitCost());
    }

    @Test
    @Order(5)
    @DisplayName("Count products by productId")
    void countByProductId() {

        int count = productRepository.countByProductId(testProduct.getProductID());


        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve a list of category IDs")
    void categorynaemList() {

        List<Integer> categoryIds = productRepository.categorynaemList();

        Assertions.assertNotNull(categoryIds);
        Assertions.assertTrue(categoryIds.size() > 0);
    }

    @Test
    @Order(7)
    @DisplayName("Retrieve a list of products")
    void productList() {
        List<Product> products = productRepository.productList();

        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    @Order(8)
    @DisplayName("Retrieve a page of products")
    void findAll() {
        int pageSize = 5;

        Page<Product> productPage = productRepository.findAll(1, pageSize);


        Assertions.assertNotNull(productPage);
        Assertions.assertTrue(productPage.getContent().size() > 0);

        Assertions.assertTrue(productPage.getTotalCount() >= pageSize);
    }
}