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
        when(bidListService.findAllBids()).thenReturn(Collections.singletonList(bid));
        mockMvc.perform(get("/bidList/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidlist"))
                .andExpect(content().string(containsString(String.valueOf(bid.getAccount()))));
    }

    @Test
    @WithMockUser
    public void addBidList() throws Exception {
        mockMvc.perform(
                        get("/bidList/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/bidList/validate").with(csrf().asHeader())
                        .param("account", "test")
                        .param("type", "test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidateBidList() throws Exception {
        String json = TestFunctions.asJsonString(bid);
        mockMvc.perform(
                post("http://localhost:8080/bidList/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/bidList/validate").with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"));
    }


    @Test
    @WithMockUser
    public void BidListUpdate() throws Exception {
        when(bidListService.findBidById(anyLong())).thenReturn(bid);

        mockMvc.perform(get("/bidList/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void testUpdateBid() throws Exception {
        when(bidListService.saveBid(bid)).thenReturn(bid);
        mockMvc.perform(post("/bidList/update/1").with(csrf().asHeader()).param("account", "test")
                        .param("type", "test").param("bidQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateBidHasError() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf().asHeader())
                        .param("account", "")
                        .param("type", "test")
                        .param("bidQuantity", "10.0"))
                .andExpect(view().name("redirect:/bidList/list"));

    }

    @Test
    @WithMockUser
    void testDeleteBid() throws Exception {
        when(bidListService.findBidById(anyLong())).thenReturn(bid);
        mockMvc.perform(get("/bidList/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }

}
