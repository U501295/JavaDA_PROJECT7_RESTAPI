package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.BidListService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc//(addFilters = false)
//@WebMvcTest(controllers = BidListController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BidListControllerTest {

    private final BidList bid = new BidList("account", "type", 10d);
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    BidListService bidListService;

    @Test
    @WithMockUser
    public void testBidListList() throws Exception {
        mockMvc.perform(get("/bidList/list").with(csrf().asHeader())).andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithMockUser
    public void getBidList() throws Exception {

        when(bidListService.findAllBids()).thenReturn(Collections.singletonList(bid));
        /*try {
            mockMvc.perform(
                            get("http://localhost:8080/bidList/list"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }*/
        mockMvc.perform(get("/bidList/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));

    }

    @Test
    public void addBidList() throws Exception {


        mockMvc.perform(
                        get("http:/localhost:8080/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void validateBidList() {
        String json = TestFunctions.asJsonString(bid);
        //when(bidListService.saveBid(any(BidList.class))).thenReturn(any(BidList.class));
        try {
            mockMvc.perform(
                    post("http://localhost:8080/bidList/validate")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)


            ).andExpect(status().isOk()).andDo(print());
        } catch (Exception e) {

        }

    }

    @Test
    public void validateErrorBidList() throws Exception {
        String json = TestFunctions.asJsonString(null);
        when(bidListService.saveBid(any(BidList.class))).thenThrow(RuntimeException.class);
        mockMvc.perform(
                post("http://localhost:8080/bidList/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isOk()).andDo(print());


    }



/*
    @GetMapping("/bidList/add")


    @PostMapping("/bidList/validate")


    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // get Bid by Id and to model then show to the form
        BidList bid = bidListService.findBidById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")


    @GetMapping("/bidList/delete/{id}")*/


}
