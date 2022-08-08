package com.nnk.springboot.TestIT.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceIT {

    @Autowired
    private RatingService ratingService;

    @Test(expected = IllegalArgumentException.class)
    public void ratingTest() {
        Rating rate = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        rate = ratingService.saveRating(rate);
        Assert.assertNotNull(rate.getRatingId());
        Assert.assertEquals(rate.getMoodysRating(), "Moodys Rating", "Moodys Rating");

        // Update
        rate.setMoodysRating("modifiedRating");
        rate = ratingService.saveRating(rate);
        Assert.assertEquals(rate.getMoodysRating(), "modifiedRating", "modifiedRating");

        // FindAll
        List<Rating> listResult = ratingService.findAllRatings();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = rate.getRatingId();
        Rating rating = ratingService.findRatingById(id);
        Assert.assertTrue(rating.getRatingId() > 0);

        // Delete
        ratingService.deleteRating(id);
        Rating ratingException = ratingService.findRatingById(id);

    }


}
