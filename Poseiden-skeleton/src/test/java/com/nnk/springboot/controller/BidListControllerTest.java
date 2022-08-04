package com.nnk.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.TestFunctions;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BidListController.class)
public class BidListControllerTest extends TestCase {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    BidListService bidListService;

    private BidList bidList;

    @Before
    public void setup() {
        bidList = new BidList();
    }

    @Test
    public void getBidList() {

        //when(bidListService.findAllBids()).thenReturn(new ArrayList<>());
        try {
            mockMvc.perform(
                            get("http://localhost:8080/bidList/list"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }

    }

    @Test
    public void addBidList() {

        try {
            mockMvc.perform(
                            get("http:/localhost:8080/bidList/add"))
                    //.andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }

    }

    @Test
    public void validateBidList() {
        String json = TestFunctions.asJsonString(bidList);
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
