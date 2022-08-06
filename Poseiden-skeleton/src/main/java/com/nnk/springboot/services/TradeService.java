package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités trade dans l'application.
 * <p>
 */
@Slf4j
@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> findAllTrades() {
        List<Trade> trades = tradeRepository.findAll();
        log.debug("trade : get all");
        return trades;
    }

    public Trade saveTrade(Trade trade) {
        log.debug("trade : save");
        return tradeRepository.save(trade);
    }

    public Trade findTradeById(Long id) {
        log.debug("trade : find by id");
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        return trade;
    }

    public void deleteTrade(Long id) {
        log.debug("trade : delete");
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);

    }
}
