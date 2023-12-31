package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        //todo#4-1 회원조회

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else {
           return null;
        }
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if(userRepository.countByUserId(user.getUserId())==0){
            userRepository.save(user);
        }else{
            throw new UserAlreadyExistsException(user.getUserId());
        }

    }


    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정

        if(userRepository.countByUserId(user.getUserId())==1){
            userRepository.update(user);
        }else{
            throw new UserAlreadyExistsException(user.getUserId());
        }

    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제

        if(userRepository.countByUserId(userId)==1){
            userRepository.deleteByUserId(userId);
        }else{
            throw new UserAlreadyExistsException(userId);
        }


    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회

        Optional<User> optionalUser = userRepository.findByUserIdAndUserPassword(userId,userPassword);

        if(!optionalUser.equals(Optional.empty())){
            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return optionalUser.get();
        }else{
            throw new UserNotFoundException(userId);
        }




    }

}
