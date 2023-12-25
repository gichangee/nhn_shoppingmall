package com.nhnacademy.shoppingmall.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(int productId){
        super(String.format("user already exist:%s",productId));
    }
}
