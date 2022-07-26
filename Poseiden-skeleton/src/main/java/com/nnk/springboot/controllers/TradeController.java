package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

@Controller
public class TradeController {
    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    // call service find all trades to show to the view
    public String home(Model model) {
        List<Trade> tradelist = tradeService.findAllTrades();
        model.addAttribute("tradelist", tradelist);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    //TODO : voir si ça crée pas deux objets à la place d'un seul
    public String validate(@Valid Trade trade, BindingResult result, Model model)
        // check data valid and save to db, after saving return trade list
    /*public String validate(@RequestParam("account") String account, @RequestParam("type") String type,
                           @RequestParam("tradeQuantity") double tradeQuantity, Model model)*/ {
        if (!result.hasErrors()) {
            //Trade newTrade = new Trade(trade.getAccount(), trade.getType(), trade.getTradeQuantity());
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.findAllTrades());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // get Trade by Id and to model then show to the form
        Trade trade = tradeService.findTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Long id, @Valid Trade trade,
                              BindingResult result, Model model) {
        // check required fields, if valid call service to update Trade and return list Trade
        if (result.hasErrors()) {
            return "redirect:trade/list";
        }
        trade.setTradeId(id);
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.findAllTrades());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Long id, Model model) {
        // Find Trade by Id and delete the trade, return to Trade list
        Trade tradeToDelete = tradeService.findTradeById(id);
        tradeService.deleteTrade(tradeToDelete.getTradeId());
        model.addAttribute("trades", tradeService.findAllTrades());
        return "redirect:/trade/list";
    }
}
