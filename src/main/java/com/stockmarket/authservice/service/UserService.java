package com.stockmarket.authservice.service;

import com.stockmarket.authservice.entity.User;
import com.stockmarket.authservice.models.UserRequest;

public interface UserService {

    boolean emailExist(String email);

    void addUser(UserRequest userRequest);

    User findByEmail(String email);
}
