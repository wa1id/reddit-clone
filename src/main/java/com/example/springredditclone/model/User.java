package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"") //user is a reserved word in Postgres so we need to escape te table name
public class User {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long userId;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid") // TODO: not working
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    private Instant created;

    private boolean enabled;
}
