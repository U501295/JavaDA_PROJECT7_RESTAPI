package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    // call service find all bids to show to the view
    public String home(Model model) {
        List<BidList> bidlist = bidListService.findAllBids();
        model.addAttribute("bidlist", bidlist);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    //TODO : voir si ça crée pas deux objets à la place d'un seul
    public String validate(@Valid BidList bid, BindingResult result, Model model)
        // check data valid and save to db, after saving return bid list
    /*public String validate(@RequestParam("account") String account, @RequestParam("type") String type,
                           @RequestParam("bidQuantity") double bidQuantity, Model model)*/ {
        if (!result.hasErrors()) {
            //BidList newBid = new BidList(bid.getAccount(), bid.getType(), bid.getBidQuantity());
            bidListService.saveBid(bid);
            model.addAttribute("bids", bidListService.findAllBids());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // get Bid by Id and to model then show to the form
        BidList bid = bidListService.findBidById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Long id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "redirect:bidList/list";
        }
        bidList.setBidListId(id);
        bidListService.saveBid(bidList);
        model.addAttribute("bids", bidListService.findAllBids());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Long id, Model model) {
        // Find Bid by Id and delete the bid, return to Bid list
        BidList bidToDelete = bidListService.findBidById(id);
        bidListService.deleteBid(bidToDelete.getBidListId());
        model.addAttribute("bids", bidListService.findAllBids());
        return "redirect:/bidList/list";
    }
}
