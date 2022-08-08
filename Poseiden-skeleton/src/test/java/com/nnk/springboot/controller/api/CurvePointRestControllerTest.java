package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @SuppressWarnings("unused")
    @MockBean
    private UserService userService;
    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private CurvePoint curve;

    @BeforeEach
    public void setup() {
        curve = new CurvePoint(10l, 1d, 10d);
        curve.setCurveId(1l);
    }

    @Test
    public void should_returnRightCurvePointList_whenGetCurvePoint() throws Exception {
        when(curvePointService.findAllCurvePoints()).thenReturn(Collections.singletonList(curve));

        mockMvc.perform(get("/api/curve"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(curve.getValue()))));
    }

    @Test
    public void should_returnRightCurvePoint_whenGetCurvePointId() throws Exception {
        when(curvePointService.findCurvePointById(anyLong())).thenReturn(curve);

        mockMvc.perform(get("/api/curve/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(curve.getValue()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingCurvePointId() throws Exception {
        when(curvePointService.findCurvePointById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/curve/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialCurvePoint() throws Exception {
        CurvePoint curveEntityPartial = curve;
        curveEntityPartial.setValue(null);
        String inputJson = new ObjectMapper().writeValueAsString(curveEntityPartial);

        mockMvc.perform(post("/api/curve")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createCurvePoint_whenPostNewCurvePoint() throws Exception {
        when(curvePointService.saveCurvePoint(any())).thenReturn(curve);
        String inputJson = new ObjectMapper().writeValueAsString(curve);

        mockMvc.perform(post("/api/curve")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    public void should_returnBindingErrors_whenPutPartialCurvePoint() throws Exception {
        CurvePoint curveEntityPartial = curve;
        curveEntityPartial.setValue(null);
        String inputJson = new ObjectMapper().writeValueAsString(curveEntityPartial);

        mockMvc.perform(put("/api/curve/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void should_updateCurvePoint_whenPutExistingCurvePoint() throws Exception {
        when(curvePointService.saveCurvePoint(any())).thenReturn(curve);
        String inputJson = new ObjectMapper().writeValueAsString(curve);

        mockMvc.perform(put("/api/curve/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_notUpdateCurvePoint_whenPutMissingCurvePoint() throws Exception {
        when(curvePointService.saveCurvePoint(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(curve);

        mockMvc.perform(put("/api/curve/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateCurvePoint_whenPutDuplicatedCurvePoint() throws Exception {
        when(curvePointService.saveCurvePoint(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(curve);

        mockMvc.perform(put("/api/curve/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteCurvePoint_whenDeleteExistingCurvePoint() throws Exception {
        mockMvc.perform(delete("/api/curve/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteCurvePoint_whenDeleteMissingCurvePoint() throws Exception {
        doThrow(new IllegalArgumentException()).when(curvePointService).deleteCurvePoint(anyLong());

        mockMvc.perform(delete("/api/curve/1"))
                .andExpect(status().isNotFound());
    }
}
