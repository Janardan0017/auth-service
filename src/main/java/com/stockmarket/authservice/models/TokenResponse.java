package com.stockmarket.authservice.models;

import java.io.Serializable;

public class TokenResponse {

    private final String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
