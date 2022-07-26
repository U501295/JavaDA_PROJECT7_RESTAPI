package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import junit.framework.TestCase;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BidListController.class)
public class BidListControllerTest extends TestCase {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    BidListService bidListService;

    @Test
    public void getBidList() {
        //List<BidList> test = bidListService.findAllBids();
        when(bidListService.findAllBids()).thenReturn(new ArrayList<>());
        try {
            mockMvc.perform(
                            get("http://localhost:8080//bidList/list"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }

    }


}
