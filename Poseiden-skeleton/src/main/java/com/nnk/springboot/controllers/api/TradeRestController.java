package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités trade dans l'API.
 * <p>
 */
@Slf4j
@RestController
@RequestMapping("/api/trade")

public class TradeRestController {
    @Autowired
    private TradeService tradeService;


    @GetMapping()
    public List<Trade> getTrades() {
        log.debug("get all trades");
        return tradeService.findAllTrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrade(@PathVariable Long id) {
        try {
            Trade tradeEntity = tradeService.findTradeById(id);
            log.debug("successfully get trade/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(tradeEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting trade because of missing trade with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postTrade(@Valid @RequestBody Trade tradeEntity,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting trade because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        Trade tradeEntitySaved = tradeService.saveTrade(tradeEntity);
        log.debug("successfully post trade");
        return ResponseEntity.status(HttpStatus.CREATED).body(tradeEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTrade(@PathVariable Long id,
                                      @Valid @RequestBody Trade tradeEntity,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting trade because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            Trade tradeEntityToModify = tradeService.findTradeById(id);
            tradeEntityToModify = tradeEntity;
            tradeEntityToModify.setTradeId(id);
            Trade tradeSaved = tradeService.saveTrade(tradeEntity);
            log.debug("successfully put trade/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(tradeSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting trade because missing trade with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrade(@PathVariable Long id) {
        try {
            tradeService.deleteTrade(id);
            String logAndBodyMessage = "successfully delete trade/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting trade because of missing trade with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
