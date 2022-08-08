package com.nnk.springboot.TestIT.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceIT {

    @Autowired
    private TradeService tradeService;

    @Test(expected = IllegalArgumentException.class)
    public void tradeTest() {
        Trade trade = new Trade("account", "type", 10d);

        // Save
        trade = tradeService.saveTrade(trade);
        Assert.assertNotNull(trade.getTradeId());
        Assert.assertEquals(trade.getBuyQuantity(), 10d, 10d);

        // Update
        trade.setBuyQuantity(20d);
        trade = tradeService.saveTrade(trade);
        Assert.assertEquals(trade.getBuyQuantity(), 20d, 20d);

        // FindAll
        List<Trade> listResult = tradeService.findAllTrades();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = trade.getTradeId();
        Trade tradeToBeFound = tradeService.findTradeById(id);
        Assert.assertTrue(trade.getTradeId() > 0);

        // Delete
        tradeService.deleteTrade(id);
        Trade tradeException = tradeService.findTradeById(id);

    }


}
