package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités bidLists dans l'application.
 * <p>
 */
@Slf4j
@Service
public class BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> findAllBids() {
        List<BidList> bidLists = bidListRepository.findAll();
        log.debug("bidList : get all");
        return bidLists;
    }

    public BidList saveBid(BidList bidList) {
        log.debug("bidList : save");
        return bidListRepository.save(bidList);
    }

    public BidList findBidById(Long id) {
        log.debug("bidList : find by id");
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        return bid;
    }

    public void deleteBid(Long id) {
        log.debug("bidList : delete");
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidListRepository.delete(bid);

    }
}
