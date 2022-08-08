package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @SuppressWarnings("unused")
    @MockBean
    private UserService userService;
    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private BidList bid;

    @BeforeEach
    public void setup() {
        bid = new BidList("account", "type", 10d);
        bid.setBidListId(1l);
    }

    @Test
    public void should_returnRightBidList_whenGetBid() throws Exception {
        when(bidListService.findAllBids()).thenReturn(Collections.singletonList(bid));

        mockMvc.perform(get("/api/bid"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(bid.getAccount()))));
    }

    @Test
    public void should_returnRightBid_whenGetBidId() throws Exception {
        when(bidListService.findBidById(anyLong())).thenReturn(bid);

        mockMvc.perform(get("/api/bid/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(bid.getAccount()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingBidId() throws Exception {
        when(bidListService.findBidById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/bid/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialBid() throws Exception {
        BidList bidEntityPartial = bid;
        bidEntityPartial.setAccount("");
        String inputJson = new ObjectMapper().writeValueAsString(bidEntityPartial);

        mockMvc.perform(post("/api/bid")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createBid_whenPostNewBid() throws Exception {
        when(bidListService.saveBid(any())).thenReturn(bid);
        String inputJson = new ObjectMapper().writeValueAsString(bid);

        mockMvc.perform(post("/api/bid")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    public void should_returnBindingErrors_whenPutPartialBid() throws Exception {
        BidList bidEntityPartial = bid;
        bidEntityPartial.setAccount("");
        String inputJson = new ObjectMapper().writeValueAsString(bidEntityPartial);

        mockMvc.perform(put("/api/bid/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void should_updateBid_whenPutExistingBid() throws Exception {
        when(bidListService.saveBid(any())).thenReturn(bid);
        String inputJson = new ObjectMapper().writeValueAsString(bid);

        mockMvc.perform(put("/api/bid/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_notUpdateBid_whenPutMissingBid() throws Exception {
        when(bidListService.saveBid(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(bid);

        mockMvc.perform(put("/api/bid/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateBid_whenPutDuplicatedBid() throws Exception {
        when(bidListService.saveBid(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(bid);

        mockMvc.perform(put("/api/bid/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteBid_whenDeleteExistingBid() throws Exception {
        mockMvc.perform(delete("/api/bid/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteBid_whenDeleteMissingBid() throws Exception {
        doThrow(new IllegalArgumentException()).when(bidListService).deleteBid(anyLong());

        mockMvc.perform(delete("/api/bid/1"))
                .andExpect(status().isNotFound());
    }
}
