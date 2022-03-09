package com.stockmarket.authservice.service;

import com.stockmarket.authservice.entity.User;
import com.stockmarket.authservice.models.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByEmail(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new JwtUserDetails(user.getId(), user.getEmail(), user.getPassword(), "ROLE_USER_1");
    }

}


