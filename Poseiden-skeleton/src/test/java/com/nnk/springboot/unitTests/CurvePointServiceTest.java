package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {


    @InjectMocks
    private CurvePointService curvePointService;
    @Mock
    private CurvePointRepository curvePointRepository;
    private CurvePoint curve;

    @BeforeEach
    public void setup() {
        curve = new CurvePoint(10l, 1d, 10d);
        curve.setCurveId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllCurvePoints() {
        when(curvePointRepository.findAll()).thenReturn(Collections.singletonList(curve));

        Assertions.assertThat(curvePointService.findAllCurvePoints()).isNotNull();
    }

    @Test
    public void should_saveCurvePoint() {
        when(curvePointRepository.save(any())).thenReturn(curve);

        CurvePoint curvePointEntity = curvePointService.saveCurvePoint(curve);

        Assertions.assertThat(curvePointEntity).isNotNull();
        verify(curvePointRepository, times(1)).save(any());
    }


    @Test
    public void should_findCurvePoint_whenGetExistingCurvePointById() {
        when(curvePointRepository.findById(anyLong())).thenReturn(Optional.of(curve));

        CurvePoint curvePointEntity = curvePointService.findCurvePointById(anyLong());

        Assertions.assertThat(curvePointEntity).isNotNull();
        verify(curvePointRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingCurvePointById() {
        when(curvePointRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> curvePointService.findCurvePointById(anyLong()));
    }

    @Test
    public void should_deleteCurvePoint_whenDeleteExistingCurvePoint() {
        when(curvePointRepository.findById(anyLong())).thenReturn(Optional.of(curve));

        curvePointService.deleteCurvePoint(anyLong());

        verify(curvePointRepository, times(1)).delete(Optional.of(curve).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingCurvePoint() {
        when(curvePointRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> curvePointService.deleteCurvePoint(anyLong()));
    }

}
