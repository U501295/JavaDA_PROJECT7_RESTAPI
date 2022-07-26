package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    // TODO: Inject Rating service
    @Autowired
    RatingService ratingService;

    @RequestMapping("/rating/list")
    // call service find all ratings to show to the view
    public String home(Model model) {
        List<Rating> ratingList = ratingService.findAllRatings();
        model.addAttribute("ratinglist", ratingList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    // voir si ça crée pas deux objets à la place d'un seul
    public String validate(@Valid Rating rating, BindingResult result, Model model)
        // check data valid and save to db, after saving return rating list
    /*public String validate(@RequestParam("account") String account, @RequestParam("type") String type,
                           @RequestParam("ratingQuantity") double ratingQuantity, Model model)*/ {
        if (!result.hasErrors()) {
            //Rating newRating = new Rating(rating.getAccount(), rating.getType(), rating.getRatingQuantity());
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.findAllRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // get Rating by Id and to model then show to the form
        Rating rating = ratingService.findRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Long id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // check required fields, if valid call service to update Rating and return list Rating
        if (result.hasErrors()) {
            return "redirect:rating/list";
        }
        rating.setRatingId(id);
        ratingService.saveRating(rating);
        model.addAttribute("ratings", ratingService.findAllRatings());

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        // Find Rating by Id and delete the rating, return to Rating list
        Rating ratingToDelete = ratingService.findRatingById(id);
        ratingService.deleteRating(ratingToDelete.getRatingId());
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "redirect:/rating/list";
    }
}
