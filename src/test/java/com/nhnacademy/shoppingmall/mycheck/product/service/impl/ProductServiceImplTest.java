package com.nhnacademy.shoppingmall.mycheck.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);
    Product testProduct = new Product(1, 1, "123", "s123", "/resources/images/monitor1.jpg", 1234, "삼성모니터입니다");

    @Test
    @DisplayName("getProduct")
    void getProduct() {
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        Product result = productService.getProduct(1);
        Assertions.assertEquals(testProduct, result);
    }

    @Test
    @DisplayName("saveProduct")
    void saveProduct() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(0);
        productService.saveProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).save(testProduct);
    }

    @Test
    @DisplayName("saveProduct - already exists")
    void saveProduct_alreadyExists() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(1);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(testProduct));
    }

    @Test
    @DisplayName("updateProduct")
    void updateProduct() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(1);
        productService.updateProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).update(testProduct);
    }

    @Test
    @DisplayName("updateProduct - not found")
    void updateProduct_notFound() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(0);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.updateProduct(testProduct));
    }

    @Test
    @DisplayName("deleteProduct")
    void deleteProduct() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(1);
        productService.deleteProduct(1);
        Mockito.verify(productRepository, Mockito.times(1)).deleteByProductId(1);
    }

    @Test
    @DisplayName("deleteProduct - not found")
    void deleteProduct_notFound() {
        Mockito.when(productRepository.countByProductId(1)).thenReturn(0);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.deleteProduct(1));
    }

    @Test
    @DisplayName("categorynamelist")
    void categorynamelist() {
        List<Integer> categoryList = Arrays.asList(1, 2, 3);
        Mockito.when(productRepository.categorynaemList()).thenReturn(categoryList);
        List<Integer> result = productService.categorynamelist();
        Assertions.assertEquals(categoryList, result);
    }

    @Test
    @DisplayName("productList")
    void productList() {
        List<Product> productList = Arrays.asList(testProduct);
        Mockito.when(productRepository.productList()).thenReturn(productList);
        List<Product> result = productService.productList();
        Assertions.assertEquals(productList, result);
    }

    @Test
    @DisplayName("productpage")
    void productpage() {
        int page = 1;
        int pageSize = 10;
        Page<Product> productPage = new Page<>(Arrays.asList(testProduct), 10);
        Mockito.when(productRepository.findAll(page, pageSize)).thenReturn(productPage);
        Page<Product> result = productService.productpage(page, pageSize);
        Assertions.assertEquals(productPage, result);
    }
}
