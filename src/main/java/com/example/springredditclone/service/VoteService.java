package com.example.springredditclone.service;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springredditclone.model.VoteType.DOWNVOTE;
import static com.example.springredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        VoteType voteType = voteDto.getVoteType();

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        handleVote(voteByPostAndUser, voteType, post);

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private void handleVote(Optional<Vote> currentUserVote, VoteType clickedVote, Post post) {
        if (voteDoesNotExists(currentUserVote, clickedVote)) {
            if (upvoted(clickedVote)) {
                upvotePost(post);
            } else {
                downvotePost(post);
            }
        }
    }

    private boolean voteDoesNotExists(Optional<Vote> currentUserVote, VoteType clickedVote) {
        if (currentUserVote.isPresent() && currentUserVote.get().getVoteType().equals(clickedVote)) {
            throw new SpringRedditException("You have already " + clickedVote + "'d for this post");
        }
        return true;
    }

    private boolean upvoted(VoteType voteType) {
        return voteType.equals(UPVOTE);
    }

    private void upvotePost(Post post) {
        post.setVoteCount(post.getVoteCount() + UPVOTE.getDirection());
    }

    private void downvotePost(Post post) {
        post.setVoteCount(post.getVoteCount() - DOWNVOTE.getDirection());
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
