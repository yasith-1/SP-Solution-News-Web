package com.spsolutions.newsweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NewsApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listCategoriesReturnsJson() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void missingNewsReturnsJson404() throws Exception {
        mockMvc.perform(get("/api/news/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void addCommentWithBlankFieldsReturns400WithFieldErrors() throws Exception {
        mockMvc.perform(post("/api/news/{id}/comments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorName\":\"\",\"content\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.authorName").exists())
                .andExpect(jsonPath("$.fieldErrors.content").exists());
    }

    @Test
    void addValidCommentReturns201() throws Exception {
        mockMvc.perform(post("/api/news/{id}/comments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorName\":\"ApiTester\",\"content\":\"Posted via REST API\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.authorName").value("ApiTester"));
    }

    @Test
    void openApiSpecIsAvailable() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("News Web API"));
    }
}
