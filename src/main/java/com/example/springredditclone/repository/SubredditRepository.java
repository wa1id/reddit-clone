package com.example.springredditclone.repository;

import com.example.springredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Long, Subreddit> {
    Optional<Subreddit> findByName(String subredditName);
}
