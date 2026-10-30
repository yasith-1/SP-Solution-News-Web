package com.spsolutions.newsweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void missingCategoryReturns404ErrorView() throws Exception {
        mockMvc.perform(get("/categories/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/error"))
                .andExpect(model().attribute("status", 404));
    }

    @Test
    void missingNewsReturns404ErrorView() throws Exception {
        mockMvc.perform(get("/news/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/error"));
    }
}
