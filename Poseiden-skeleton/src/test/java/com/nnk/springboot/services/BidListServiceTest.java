package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {

    @Autowired
    private BidListService bidListService;

    @Test(expected = IllegalArgumentException.class)
    public void bidListTest() {
        BidList bid = new BidList("account", "type", 10d);

        // Save
        bid = bidListService.saveBid(bid);
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListService.saveBid(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // FindAll
        List<BidList> listResult = bidListService.findAllBids();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = bid.getBidListId();
        BidList bidList = bidListService.findBidById(id);
        Assert.assertTrue(bidList.getBidListId() > 0);

        // Delete
        bidListService.deleteBid(id);
        BidList bidListException = bidListService.findBidById(id);

    }


}
