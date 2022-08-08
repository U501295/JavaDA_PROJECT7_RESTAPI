package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {


    @InjectMocks
    private TradeService tradeService;
    @Mock
    private TradeRepository tradeRepository;
    private Trade trade;

    @BeforeEach
    public void setup() {
        trade = new Trade("account", "type", 10d);
        trade.setTradeId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllTrades() {
        when(tradeRepository.findAll()).thenReturn(Collections.singletonList(trade));

        Assertions.assertThat(tradeService.findAllTrades()).isNotNull();
    }

    @Test
    public void should_saveTrade() {
        when(tradeRepository.save(any())).thenReturn(trade);

        Trade tradeEntity = tradeService.saveTrade(trade);

        Assertions.assertThat(tradeEntity).isNotNull();
        verify(tradeRepository, times(1)).save(any());
    }


    @Test
    public void should_findTrade_whenGetExistingTradeById() {
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(trade));

        Trade tradeEntity = tradeService.findTradeById(anyLong());

        Assertions.assertThat(tradeEntity).isNotNull();
        verify(tradeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingTradeById() {
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tradeService.findTradeById(anyLong()));
    }

    @Test
    public void should_deleteTrade_whenDeleteExistingTrade() {
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.of(trade));

        tradeService.deleteTrade(anyLong());

        verify(tradeRepository, times(1)).delete(Optional.of(trade).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingTrade() {
        when(tradeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tradeService.deleteTrade(anyLong()));
    }

}
