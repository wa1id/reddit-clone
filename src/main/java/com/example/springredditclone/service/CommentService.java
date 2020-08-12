package com.example.springredditclone.service;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.mapper.CommentMapper;
import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.NotificationEmail;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class CommentService {

    //private static final String POST_URL = ""; // TODO: implement URL to the post in email
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        sendCommentNotificationToOriginalPoster(post.getUser(), comment.getUser());
    }

    private void sendCommentNotificationToOriginalPoster(User originalPoster, User commenter) {
        String message = mailContentBuilder.build(commenter.getUsername() + " posted a comment on your post.");
        mailService.sendMail(new NotificationEmail(commenter.getUsername() + " commented on your post", originalPoster.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }
}
