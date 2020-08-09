package com.example.springredditclone.model;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tokenId;

    private String token;

    @OneToOne(fetch = LAZY)
    private User user;

    private Instant expiryDate;
}
