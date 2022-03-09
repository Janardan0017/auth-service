package com.stockmarket.authservice.models;

import javax.validation.constraints.NotNull;

public class UserRequest {

    @NotNull
    public String name;
    @NotNull
    public String email;
    @NotNull
    public String phone;
    @NotNull
    public String password;
}
