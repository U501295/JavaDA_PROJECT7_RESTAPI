package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités rating dans l'application.
 * <p>
 */
@Slf4j
@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> findAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        log.debug("rating : get all");
        return ratings;
    }

    public Rating saveRating(Rating rating) {
        log.debug("rating : save");
        return ratingRepository.save(rating);
    }

    public Rating findRatingById(Long id) {
        log.debug("rating : find by id");
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        return rating;
    }

    public void deleteRating(Long id) {
        log.debug("rating : delete");
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);

    }
}
