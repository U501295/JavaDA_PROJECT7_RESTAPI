package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

@Slf4j
@RestController
@RequestMapping("/api/bid")

public class BidListRestController {
    @Autowired
    private BidListService bidListService;


    @GetMapping()
    public List<BidList> getBids() {
        log.debug("get all bids");
        return bidListService.findAllBids();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBid(@PathVariable Long id) {
        try {
            BidList bidEntity = bidListService.findBidById(id);
            log.debug("successfully get bid/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(bidEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting bid because of missing bid with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postBid(@Valid @RequestBody BidList bidEntity,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting bid because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        BidList bidEntitySaved = bidListService.saveBid(bidEntity);
        log.debug("successfully post bid");
        return ResponseEntity.status(HttpStatus.CREATED).body(bidEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBid(@PathVariable Long id,
                                    @Valid @RequestBody BidList bidEntity,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting bid because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            BidList bidEntityToModify = bidListService.findBidById(id);
            bidEntityToModify = bidEntity;
            bidEntityToModify.setBidListId(id);
            BidList bidSaved = bidListService.saveBid(bidEntity);
            log.debug("successfully put bid/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(bidSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting bid because missing bid with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBid(@PathVariable Long id) {
        try {
            bidListService.deleteBid(id);
            String logAndBodyMessage = "successfully delete bid/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting bid because of missing bid with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
