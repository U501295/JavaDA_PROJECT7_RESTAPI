package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc//(addFilters = false)
//@WebMvcTest(controllers = RatingController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RatingControllerTest {

    private final Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    RatingService bidListService;

    @Test
    @WithMockUser
    public void testRatingList() throws Exception {
        when(bidListService.findAllRatings()).thenReturn(Collections.singletonList(rating));
        mockMvc.perform(get("/rating/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratinglist"))
                .andExpect(content().string(containsString(String.valueOf(rating.getMoodysRating()))));
    }

    @Test
    @WithMockUser
    public void addRating() throws Exception {
        mockMvc.perform(
                        get("/rating/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/rating/validate").with(csrf().asHeader())
                        .param("moodysRating", "test")
                        .param("sandPRating", "test")
                        .param("fitchRating", "test")
                        .param("orderNumber", "10")).andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidateRating() throws Exception {
        String json = TestFunctions.asJsonString(rating);
        mockMvc.perform(
                post("http://localhost:8080/rating/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/rating/validate").with(csrf().asHeader())
                        .param("moodysRating", "")
                        .param("sandPRating", "test")
                        .param("fitchRating", "test")
                        .param("orderNumber", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"));
    }


    @Test
    @WithMockUser
    public void RatingUpdate() throws Exception {
        when(bidListService.findRatingById(anyLong())).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    void testUpdateRating() throws Exception {
        when(bidListService.saveRating(rating)).thenReturn(rating);
        mockMvc.perform(post("/rating/update/1").with(csrf().asHeader())
                        .param("moodysRating", "test")
                        .param("sandPRating", "test")
                        .param("fitchRating", "test")
                        .param("orderNumber", "10"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateRatingHasError() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .with(csrf().asHeader())
                        .param("moodysRating", "")
                        .param("sandPRating", "test")
                        .param("fitchRating", "test")
                        .param("orderNumber", "10"))
                .andExpect(view().name("redirect:rating/list"));

    }

    @Test
    @WithMockUser
    void testDeleteRating() throws Exception {
        when(bidListService.findRatingById(anyLong())).thenReturn(rating);
        mockMvc.perform(get("/rating/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }

}
