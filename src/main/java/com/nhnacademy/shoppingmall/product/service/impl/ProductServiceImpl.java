package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product getProduct(int ProductID) {
        Optional<Product> optionalProduct = productRepository.findById(ProductID);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveProduct(Product product) {

        if(productRepository.countByProductId(product.getProductID())==0){
            productRepository.save(product);
        }else{
            throw new ProductAlreadyExistsException(product.getProductID());
        }
    }

    @Override
    public void updateProduct(Product product) {

        if(productRepository.countByProductId(product.getProductID())==1){
            productRepository.update(product);
        }else{
            throw new ProductAlreadyExistsException(product.getProductID());
        }
    }

    @Override
    public void deleteProduct(int ProductID) {

        if(productRepository.countByProductId(ProductID)==1){
            productRepository.deleteByProductId(ProductID);
        }else{
            throw new ProductAlreadyExistsException(ProductID);
        }
    }


    @Override
    public List<Integer> categorynamelist() {
        return productRepository.categorynaemList();
    }

    @Override
    public List<Product> productList() {
        return productRepository.productList();
    }

    @Override
    public Page<Product> productpage(int page , int pageSize) {
        return productRepository.findAll(page,pageSize);
    }


}
