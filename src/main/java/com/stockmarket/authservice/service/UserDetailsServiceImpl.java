package com.stockmarket.authservice.service;

import com.stockmarket.authservice.entity.User;
import com.stockmarket.authservice.models.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//  static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

//  static {
//    inMemoryUserList.add(new JwtUserDetails(1L, "admin001@yopmail.com",
//        "$2a$10$RlnYsDeWCpXx22HRVHn1suD8WtWfg9fWsTJUG3UMyTwAeu0ew3rFm", "ROLE_USER_2"));
//    inMemoryUserList.add(new JwtUserDetails(2L, "admin002@yopmail.com",
//            "$2a$10$Hd8zvA9nDLroVqakqddRMOClWQDF8JnbeoXefUrXBwcuxB92LBoLC", "ROLE_USER_2"));
//    inMemoryUserList.add(new JwtUserDetails(3L, "admin003@yopmail.com",
//            "$2a$10$rIlRNqwl2FodQIpJDNeOY.p.PImNYMnEOXAK6xyNwT4Chm7UZz/ly", "ROLE_USER_2"));
//  }

  @Override
  public UserDetails loadUserByUsername(String username) {
//    Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
//        .filter(user -> user.getUsername().equals(username)).findFirst();
    User user = userService.findByEmail(username);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return new JwtUserDetails(user.getId(), user.getEmail(), user.getPassword(), "ROLE_USER_1");
  }

}


