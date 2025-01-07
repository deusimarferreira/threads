package com.example.threads;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
public class ThreadsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetThreadName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/thread/name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Thread")));
    }

    @Test
    public void testSaveByName() throws Exception {
        String newName = "TestThread";
        mockMvc.perform(MockMvcRequestBuilders.post("/thread/name/" + newName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("New name is: " + newName));
    }

    @Test
    public void testReverseString() throws Exception {
        String originalString = "hello";
        String reversedString = new StringBuilder(originalString).reverse().toString();
        mockMvc.perform(MockMvcRequestBuilders.get("/thread/reverse/" + originalString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(reversedString));
    }
}