package com.example.springredditclone.controller;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public void vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
    }
}
