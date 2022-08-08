package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
public class BidServiceTest {


    @InjectMocks
    private BidListService bidListService;
    @Mock
    private BidListRepository bidListRepository;
    private BidList bid;

    @BeforeEach
    public void setup() {
        bid = new BidList("account", "type", 10d);
        bid.setBidListId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllBids() {
        when(bidListRepository.findAll()).thenReturn(Collections.singletonList(bid));

        Assertions.assertThat(bidListService.findAllBids()).isNotNull();
    }

    @Test
    public void should_saveBid() {
        when(bidListRepository.save(any())).thenReturn(bid);

        BidList bidEntity = bidListService.saveBid(bid);

        Assertions.assertThat(bidEntity).isNotNull();
        verify(bidListRepository, times(1)).save(any());
    }


    @Test
    public void should_findBid_whenGetExistingBidById() {
        when(bidListRepository.findById(anyLong())).thenReturn(Optional.of(bid));

        BidList bidEntity = bidListService.findBidById(anyLong());

        Assertions.assertThat(bidEntity).isNotNull();
        verify(bidListRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingBidById() {
        when(bidListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bidListService.findBidById(anyLong()));
    }

    @Test
    public void should_deleteBid_whenDeleteExistingBid() {
        when(bidListRepository.findById(anyLong())).thenReturn(Optional.of(bid));

        bidListService.deleteBid(anyLong());

        verify(bidListRepository, times(1)).delete(Optional.of(bid).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingBid() {
        when(bidListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bidListService.deleteBid(anyLong()));
    }

}
