package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long ratingId;

    @Column(nullable = false, name = "moodysRating")
    private String moodysRating;

    @Column(nullable = false, name = "sandPRating")
    private String sandPRating;

    @Column(nullable = false, name = "fitchRating")
    private String fitchRating;

    @Column(nullable = false, name = "orderNumber")
    private int orderNumber;

}
