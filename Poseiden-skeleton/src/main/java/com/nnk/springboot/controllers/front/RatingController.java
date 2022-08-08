package com.nnk.springboot.controllers.front;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
 * Controller permettant d'atteindre les URLs en lien avec les entités rating dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<Rating> ratingList = ratingService.findAllRatings();
        model.addAttribute("ratinglist", ratingList);
        log.debug("rating : affichage de la liste");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.findAllRatings());
            log.debug("rating : erreur dans l'ajout d'un rating");
            return "redirect:/rating/list";
        }
        log.debug("rating : success dans l'ajout d'un rating");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Rating rating = ratingService.findRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Long id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("rating : erreur dans la modification d'un rating");
            return "redirect:rating/list";
        }
        rating.setRatingId(id);
        ratingService.saveRating(rating);
        model.addAttribute("ratings", ratingService.findAllRatings());
        log.debug("rating : success dans la modification d'un rating");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        Rating ratingToDelete = ratingService.findRatingById(id);
        ratingService.deleteRating(ratingToDelete.getRatingId());
        model.addAttribute("ratings", ratingService.findAllRatings());
        log.debug("rating : success dans la suppression d'un rating");
        return "redirect:/rating/list";
    }
}
