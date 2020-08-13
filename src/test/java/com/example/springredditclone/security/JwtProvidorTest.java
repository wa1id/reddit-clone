package com.example.springredditclone.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtProvidorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvidor jwtProvidor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void shouldNotAllowAccessToUnauthorizedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("test", "test"));

        String token = jwtProvidor.generateToken(authenticate);

        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/test").header("Authorization", token))
                .andExpect(status().isOk());
    }
}
