package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.List;

public interface ProductService {

    Product getProduct(int ProductID);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int ProductID);


    List<Integer> categorynamelist();

    List<Product> productList();

    Page<Product> productpage(int page , int pageSize);

}
