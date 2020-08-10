package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long subredditId;

    @NotBlank(message = "Community name is required")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;

    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    private User user;
}
