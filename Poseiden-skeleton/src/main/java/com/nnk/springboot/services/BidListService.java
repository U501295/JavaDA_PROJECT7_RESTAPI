package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> findAllBids() {
        List<BidList> bidlists = bidListRepository.findAll();
        return bidlists;
    }

    public BidList saveBid(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public BidList findBidById(Long id) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        return bid;
    }

    public void deleteBid(Long id) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidListRepository.delete(bid);

    }
}
