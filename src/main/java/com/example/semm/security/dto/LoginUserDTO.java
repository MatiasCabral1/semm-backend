package com.example.semm.security.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUserDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "Debe ingresar un telefono")
    @NotBlank(message = "El telefono NO puede ser vacio")
    @Size(min = 10, max = 10, message = "El telefono debe tener 10 digitos")
    private String username;
    @NotBlank
    @NotNull(message = "Debe ingresar una clave")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
