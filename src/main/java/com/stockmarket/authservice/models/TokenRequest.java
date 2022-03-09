package com.stockmarket.authservice.models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TokenRequest {

    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
