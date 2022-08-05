package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column(nullable = false, name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column(nullable = false, name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Column(nullable = false, name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }


    public User() {

    }

}
