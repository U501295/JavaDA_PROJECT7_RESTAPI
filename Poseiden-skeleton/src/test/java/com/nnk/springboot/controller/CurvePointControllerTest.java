package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.CurvePointService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc//(addFilters = false)
//@WebMvcTest(controllers = curvePointController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CurvePointControllerTest {

    private final CurvePoint cur = new CurvePoint(10l, 1d, 10d);
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    CurvePointService curvePointService;

    @Test
    @WithMockUser
    public void testcurvePointList() throws Exception {
        when(curvePointService.findAllCurvePoints()).thenReturn(Collections.singletonList(cur));
        mockMvc.perform(get("/curvePoint/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser
    public void addcurvePoint() throws Exception {
        mockMvc.perform(
                        get("/curvePoint/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/curvePoint/validate").with(csrf().asHeader())
                        .param("idCurve", String.valueOf(11L))
                        .param("term", String.valueOf(2d))
                        .param("value", String.valueOf(3d)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidatecurvePoint() throws Exception {
        String json = TestFunctions.asJsonString(cur);
        mockMvc.perform(
                post("http://localhost:8080/curvePoint/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate").with(csrf().asHeader())
                        .param("idCurve", "")
                        .param("term", String.valueOf(2d))
                        .param("value", String.valueOf(3d)))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "idCurve", "NotNull"));
    }


    @Test
    @WithMockUser
    public void curvePointUpdate() throws Exception {
        when(curvePointService.findCurvePointById(anyLong())).thenReturn(cur);

        mockMvc.perform(get("/curvePoint/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser
    void testUpdateCurvePoint() throws Exception {
        when(curvePointService.saveCurvePoint(cur)).thenReturn(cur);
        mockMvc.perform(post("/curvePoint/update/1").with(csrf().asHeader())
                        .param("idCurve", String.valueOf(11L))
                        .param("term", String.valueOf(2d))
                        .param("value", String.valueOf(3d)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateCurvePointHasError() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf().asHeader())
                        .param("idCurve", "")
                        .param("term", String.valueOf(2d))
                        .param("value", String.valueOf(3d)))
                .andExpect(view().name("redirect:/curvePoint/list"));

    }

    @Test
    @WithMockUser
    void testDeleteCurvePoint() throws Exception {
        when(curvePointService.findCurvePointById(anyLong())).thenReturn(cur);
        mockMvc.perform(get("/curvePoint/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

}
