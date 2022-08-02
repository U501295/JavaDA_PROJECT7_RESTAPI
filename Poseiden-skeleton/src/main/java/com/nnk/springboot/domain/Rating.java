package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @NotBlank(message = "Moodys_rating is mandatory")
    @Column(nullable = false, name = "moodys_rating")
    private String moodysRating;

    @NotBlank(message = "Sand_p_rating is mandatory")
    @Column(nullable = false, name = "sand_p_rating")
    private String sandPRating;

    @NotBlank(message = "Fitch_rating is mandatory")
    @Column(nullable = false, name = "fitch_rating")
    private String fitchRating;

    @PositiveOrZero
    @NotNull(message = "order_number not be empty")
    @Column(nullable = false, name = "order_number")
    private int orderNumber;


    public Rating() {

    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
