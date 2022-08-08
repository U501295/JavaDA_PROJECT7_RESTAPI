package com.nnk.springboot.controllers.front;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités bidLists dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidList> bidList = bidListService.findAllBids();
        model.addAttribute("bidlist", bidList);
        log.debug("bidlist : affichage de la liste");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.saveBid(bid);
            model.addAttribute("bids", bidListService.findAllBids());
            log.debug("bidlist : erreur dans l'ajout d'une bid");
            return "redirect:/bidList/list";
        }
        log.debug("bidlist : success dans l'ajout d'une bid");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        BidList bid = bidListService.findBidById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Long id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("bidlist : erreur dans la modification d'une bid");
            return "redirect:/bidList/list";
        }
        bidList.setBidListId(id);
        bidListService.saveBid(bidList);
        model.addAttribute("bids", bidListService.findAllBids());
        log.debug("bidlist : success dans la modification d'une bid");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Long id, Model model) {
        BidList bidToDelete = bidListService.findBidById(id);
        bidListService.deleteBid(bidToDelete.getBidListId());
        model.addAttribute("bids", bidListService.findAllBids());
        log.debug("bidlist : success dans la suppression d'une bid");
        return "redirect:/bidList/list";
    }
}
