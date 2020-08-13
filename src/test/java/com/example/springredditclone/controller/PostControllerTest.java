package com.example.springredditclone.controller;

import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostController.class)
@ContextConfiguration(classes = {UserDetailsService.class})
public class PostControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private PostService postService;

    @Before
    public void setup() {
        this.mock = MockMvcBuilders.standaloneSetup(new PostController(postService)).build();
    }

    @Test
    public void getPostById_ShouldReturnOnePost() throws Exception {
        given(postService.getPost(1L)).willReturn(new PostResponse(1L, "test", "test", "test", "test", "test", 0, 0, "test", false, false));

        mock.perform(MockMvcRequestBuilders.get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));

    }

    @Test
    public void getPostById_notFound() throws Exception {
        given(postService.getPost(anyLong())).willThrow(new PostNotFoundException(anyString()));

        mock.perform(MockMvcRequestBuilders.get("/api/posts/1"))
                .andExpect(status().isNotFound());
    }
}
