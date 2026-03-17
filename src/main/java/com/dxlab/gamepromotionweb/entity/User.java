package com.dxlab.gamepromotionweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "gpw_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 50)
    @NotNull
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Size(max = 45)
    @Column(name = "email", length = 45)
    private String email;


}