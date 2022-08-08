package com.nnk.springboot.controllers.front;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
 * Controller permettant d'atteindre les URLs en lien avec les entités trade dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class TradeController {
    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<Trade> tradelist = tradeService.findAllTrades();
        model.addAttribute("tradelist", tradelist);
        log.debug("trade : affichage de la liste");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.findAllTrades());
            log.debug("trade : erreur lors de l'ajout");
            return "redirect:/trade/list";
        }
        log.debug("trade : succès lors de l'ajout");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Trade trade = tradeService.findTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Long id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("trade : erreur lors de la modification");
            return "redirect:/trade/list";
        }
        trade.setTradeId(id);
        tradeService.saveTrade(trade);
        model.addAttribute("trades", tradeService.findAllTrades());
        log.debug("trade : succès lors de la modification");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Long id, Model model) {
        Trade tradeToDelete = tradeService.findTradeById(id);
        tradeService.deleteTrade(tradeToDelete.getTradeId());
        model.addAttribute("trades", tradeService.findAllTrades());
        log.debug("trade : succès lors de la suppression");
        return "redirect:/trade/list";
    }
}
