package com.nnk.springboot.controller.front;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.TradeService;
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
//@WebMvcTest(controllers = TradeListController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TradeControllerTest {

    private final Trade trade = new Trade("account", "type", 10d);
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    TradeService tradeService;

    @Test
    @WithMockUser
    public void testTradeListList() throws Exception {
        when(tradeService.findAllTrades()).thenReturn(Collections.singletonList(trade));
        mockMvc.perform(get("/trade/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("tradelist"))
                .andExpect(content().string(containsString(String.valueOf(trade.getAccount()))));
    }

    @Test
    @WithMockUser
    public void addTradeList() throws Exception {
        mockMvc.perform(
                        get("/trade/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/trade/validate").with(csrf().asHeader())
                        .param("account", "test")
                        .param("type", "test")
                        .param("buyQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidateTradeList() throws Exception {
        String json = TestFunctions.asJsonString(trade);
        mockMvc.perform(
                post("http://localhost:8080/trade/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/trade/validate").with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "test")
                        .param("buyQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"));
    }


    @Test
    @WithMockUser
    public void TradeListUpdate() throws Exception {
        when(tradeService.findTradeById(anyLong())).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser
    void testUpdateTrade() throws Exception {
        when(tradeService.saveTrade(trade)).thenReturn(trade);
        mockMvc.perform(post("/trade/update/1").with(csrf().asHeader()).param("account", "test")
                        .param("type", "test").param("buyQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateTradeHasError() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "test")
                        .param("buyQuantity", "10.0"))
                .andExpect(view().name("redirect:/trade/list"));

    }

    @Test
    @WithMockUser
    void testDeleteTrade() throws Exception {
        when(tradeService.findTradeById(anyLong())).thenReturn(trade);
        mockMvc.perform(get("/trade/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
    }

}
