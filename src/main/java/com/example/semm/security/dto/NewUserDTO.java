package com.example.semm.security.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewUserDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank
    private String name;
    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notBlank}")
    @Size(min = 10, max = 10, message = "{user.phone.notSize}")
    @Pattern(regexp = "(\\d{10})", message = "{user.phone.notNumber}")
    private String username;// nombre de usuario = telefono
    @NotNull(message = "{user.email.notNull}")
    @NotBlank(message = "user.email.notBlank")
    @Email(message = "{user.email.notValid}")
    private String email;
    @NotNull(message = "{user.password.notNull}")
    private String password;

    private Set<String> roles = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
