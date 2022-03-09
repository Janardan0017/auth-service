package com.stockmarket.authservice.controller;

import com.stockmarket.authservice.models.TokenRequest;
import com.stockmarket.authservice.models.TokenResponse;
import com.stockmarket.authservice.models.UserRequest;
import com.stockmarket.authservice.service.JwtTokenUtil;
import com.stockmarket.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/auth-service/api/v1.0/market/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping()
    public ResponseEntity<TokenResponse> addUser(@RequestBody @Valid UserRequest userRequest) {
        if (userService.emailExist(userRequest.email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already exists with given email");
        }
        userService.addUser(userRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.email);
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> createAuthenticationToken(@RequestBody @Valid TokenRequest tokenRequest) {

        authenticate(tokenRequest.getUsername(), tokenRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestParam String token) {
        try {
            jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        return ResponseEntity.ok(true);
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
