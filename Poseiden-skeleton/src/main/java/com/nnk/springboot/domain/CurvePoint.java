package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long curveId;

    @Column(nullable = false, name = "asOfDate")
    @Temporal(TemporalType.DATE)
    private Date asOfDate;

    @Column(nullable = false, name = "term")
    private double term;

    @Column(nullable = false, name = "value")
    private double value;

    @Column(nullable = false, name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
}
