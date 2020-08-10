package com.example.springredditclone.service;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.exceptions.SubredditNotFoundException;
import com.example.springredditclone.mapper.SubredditMapper;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit savedSubreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setSubredditId(savedSubreddit.getSubredditId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id: " + id));
        return mapToDto(subreddit);
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .subredditId(subreddit.getSubredditId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapToSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName()) // "/r/"?
                .description(subredditDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now())
                .build();
    }
}
