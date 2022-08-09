package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
 * Controller permettant d'atteindre les URLs en lien avec les entités rating dans l'API.
 * <p>
 */
@Slf4j
@RestController
@RequestMapping("/api/rating")

public class RatingRestController {
    @Autowired
    private RatingService ratingService;


    @GetMapping()
    public List<Rating> getRatings() {
        log.debug("get all ratings");
        return ratingService.findAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRating(@PathVariable Long id) {
        try {
            Rating ratingEntity = ratingService.findRatingById(id);
            log.debug("successfully get rating/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(ratingEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting rating because of missing rating with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postRating(@Valid @RequestBody Rating ratingEntity,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting rating because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        Rating ratingEntitySaved = ratingService.saveRating(ratingEntity);
        log.debug("successfully post rating");
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putRating(@PathVariable Long id,
                                       @Valid @RequestBody Rating ratingEntity,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting rating because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            Rating ratingEntityToModify = ratingService.findRatingById(id);
            ratingEntityToModify = ratingEntity;
            ratingEntityToModify.setRatingId(id);
            Rating ratingSaved = ratingService.saveRating(ratingEntity);
            log.debug("successfully put rating/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(ratingSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting rating because missing rating with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id) {
        try {
            ratingService.deleteRating(id);
            String logAndBodyMessage = "successfully delete rating/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting rating because of missing rating with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
