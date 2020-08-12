package com.example.springredditclone.model;

import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.exceptions.VoteNotFoundException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private final int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new VoteNotFoundException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
