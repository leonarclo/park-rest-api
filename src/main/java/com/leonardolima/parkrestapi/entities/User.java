package com.leonardolima.parkrestapi.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User {

    public enum Role {
        ADMIN,
        COMMON
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "image", nullable = true)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    private Role role = Role.COMMON;

    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "created_by")
    private LocalDateTime created_by;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "updated_by")
    private LocalDateTime updated_by;
}
