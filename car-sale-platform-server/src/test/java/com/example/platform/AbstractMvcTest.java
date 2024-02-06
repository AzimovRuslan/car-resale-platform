package com.example.platform;

import com.example.platform.jwt.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AbstractMvcTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    private String json(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    protected ResultActions login(String username, String password) throws Exception {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        return mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/signin")
                        .content(json(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }
}
