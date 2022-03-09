package com.stockmarket.authservice.service;

import com.stockmarket.authservice.entity.User;
import com.stockmarket.authservice.models.UserRequest;
import com.stockmarket.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.emailExist(email);
    }

    @Override
    public void addUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name);
        user.setEmail(userRequest.email);
        user.setPhone(userRequest.phone);
        user.setPassword(passwordEncoder.encode(userRequest.password));
        user.setCreatedAt(System.currentTimeMillis());

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}
