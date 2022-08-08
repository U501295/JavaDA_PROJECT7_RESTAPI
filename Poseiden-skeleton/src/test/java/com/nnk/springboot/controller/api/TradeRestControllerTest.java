package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradePointService;

    @SuppressWarnings("unused")
    @MockBean
    private UserService userService;
    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private Trade trade;

    @BeforeEach
    public void setup() {
        trade = new Trade("account", "type", 10d);
        trade.setTradeId(1l);
    }

    @Test
    public void should_returnRightTradeList_whenGetTrade() throws Exception {
        when(tradePointService.findAllTrades()).thenReturn(Collections.singletonList(trade));

        mockMvc.perform(get("/api/trade"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(trade.getAccount()))));
    }

    @Test
    public void should_returnRightTrade_whenGetTradeId() throws Exception {
        when(tradePointService.findTradeById(anyLong())).thenReturn(trade);

        mockMvc.perform(get("/api/trade/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(trade.getAccount()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingTradeId() throws Exception {
        when(tradePointService.findTradeById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/trade/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialTrade() throws Exception {
        Trade tradeEntityPartial = trade;
        tradeEntityPartial.setAccount("");
        String inputJson = new ObjectMapper().writeValueAsString(tradeEntityPartial);

        mockMvc.perform(post("/api/trade")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createTrade_whenPostNewTrade() throws Exception {
        when(tradePointService.saveTrade(any())).thenReturn(trade);
        String inputJson = new ObjectMapper().writeValueAsString(trade);

        mockMvc.perform(post("/api/trade")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnBindingErrors_whenPutPartialTrade() throws Exception {
        Trade tradeEntityPartial = trade;
        tradeEntityPartial.setAccount("");
        String inputJson = new ObjectMapper().writeValueAsString(tradeEntityPartial);

        mockMvc.perform(put("/api/trade/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_updateTrade_whenPutExistingTrade() throws Exception {
        when(tradePointService.saveTrade(any())).thenReturn(trade);
        String inputJson = new ObjectMapper().writeValueAsString(trade);

        mockMvc.perform(put("/api/trade/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_notUpdateTrade_whenPutMissingTrade() throws Exception {
        when(tradePointService.saveTrade(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(trade);

        mockMvc.perform(put("/api/trade/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateTrade_whenPutDuplicatedTrade() throws Exception {
        when(tradePointService.saveTrade(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(trade);

        mockMvc.perform(put("/api/trade/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteTrade_whenDeleteExistingTrade() throws Exception {
        mockMvc.perform(delete("/api/trade/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteTrade_whenDeleteMissingTrade() throws Exception {
        doThrow(new IllegalArgumentException()).when(tradePointService).deleteTrade(anyLong());

        mockMvc.perform(delete("/api/trade/1"))
                .andExpect(status().isNotFound());
    }
}
