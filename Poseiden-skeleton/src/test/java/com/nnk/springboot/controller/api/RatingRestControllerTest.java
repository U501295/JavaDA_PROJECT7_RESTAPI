package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc//(addFilters = false)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RatingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @SuppressWarnings("unused")
    @MockBean
    private UserService userService;
    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private Rating rating;

    @BeforeEach
    public void setup() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setRatingId(1l);
    }

    @Test
    public void should_returnRightRatingList_whenGetRating() throws Exception {
        when(ratingService.findAllRatings()).thenReturn(Collections.singletonList(rating));

        mockMvc.perform(get("/api/rating"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(rating.getMoodysRating()))));
    }

    @Test
    public void should_returnRightRating_whenGetRatingId() throws Exception {
        when(ratingService.findRatingById(anyLong())).thenReturn(rating);

        mockMvc.perform(get("/api/rating/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(rating.getMoodysRating()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingRatingId() throws Exception {
        when(ratingService.findRatingById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/rating/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialRating() throws Exception {
        Rating ratingEntityPartial = rating;
        ratingEntityPartial.setMoodysRating("");
        String inputJson = new ObjectMapper().writeValueAsString(ratingEntityPartial);

        mockMvc.perform(post("/api/rating")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createRating_whenPostNewRating() throws Exception {
        when(ratingService.saveRating(any())).thenReturn(rating);
        String inputJson = new ObjectMapper().writeValueAsString(rating);

        mockMvc.perform(post("/api/rating")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnBindingErrors_whenPutPartialRating() throws Exception {
        Rating ratingEntityPartial = rating;
        ratingEntityPartial.setMoodysRating("");
        String inputJson = new ObjectMapper().writeValueAsString(ratingEntityPartial);

        mockMvc.perform(put("/api/rating/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_updateRating_whenPutExistingRating() throws Exception {
        when(ratingService.saveRating(any())).thenReturn(rating);
        String inputJson = new ObjectMapper().writeValueAsString(rating);

        mockMvc.perform(put("/api/rating/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_notUpdateRating_whenPutMissingRating() throws Exception {
        when(ratingService.saveRating(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(rating);

        mockMvc.perform(put("/api/rating/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateRating_whenPutDuplicatedRating() throws Exception {
        when(ratingService.saveRating(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(rating);

        mockMvc.perform(put("/api/rating/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteRating_whenDeleteExistingRating() throws Exception {
        mockMvc.perform(delete("/api/rating/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteRating_whenDeleteMissingRating() throws Exception {
        doThrow(new IllegalArgumentException()).when(ratingService).deleteRating(anyLong());

        mockMvc.perform(delete("/api/rating/1"))
                .andExpect(status().isNotFound());
    }
}
