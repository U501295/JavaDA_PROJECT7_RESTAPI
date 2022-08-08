package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {


    @InjectMocks
    private RatingService ratingService;
    @Mock
    private RatingRepository ratingRepository;
    private Rating rating;

    @BeforeEach
    public void setup() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setRatingId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllRatings() {
        when(ratingRepository.findAll()).thenReturn(Collections.singletonList(rating));

        Assertions.assertThat(ratingService.findAllRatings()).isNotNull();
    }

    @Test
    public void should_saveRating() {
        when(ratingRepository.save(any())).thenReturn(rating);

        Rating ratingEntity = ratingService.saveRating(rating);

        Assertions.assertThat(ratingEntity).isNotNull();
        verify(ratingRepository, times(1)).save(any());
    }


    @Test
    public void should_findRating_whenGetExistingRatingById() {
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.of(rating));

        Rating ratingEntity = ratingService.findRatingById(anyLong());

        Assertions.assertThat(ratingEntity).isNotNull();
        verify(ratingRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingRatingById() {
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ratingService.findRatingById(anyLong()));
    }

    @Test
    public void should_deleteRating_whenDeleteExistingRating() {
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.of(rating));

        ratingService.deleteRating(anyLong());

        verify(ratingRepository, times(1)).delete(Optional.of(rating).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingRating() {
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ratingService.deleteRating(anyLong()));
    }

}
