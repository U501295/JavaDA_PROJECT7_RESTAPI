package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


@Entity
@Table(name = "curve_point")
@Getter
@Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curve_point_id")
    private Long curveId;

    @PositiveOrZero
    @NotNull(message = "Curve id not be empty")
    @Column(nullable = false, name = "curve_id")
    private Long idCurve;

    @Column(nullable = false, name = "asof_date")
    @Temporal(TemporalType.DATE)
    private Date asOfDate;

    @PositiveOrZero
    @NotNull(message = "term may not be empty")
    @Column(nullable = false, name = "term")
    private Double term;

    @PositiveOrZero
    @NotNull(message = "Value may not be empty")
    @Column(nullable = false, name = "value")
    private Double value;

    @Column(nullable = false, name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;


    public CurvePoint() {

    }

    public CurvePoint(Long idCurve, Double term, Double value) {
        this.idCurve = idCurve;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        this.asOfDate = date;
        this.term = term;
        this.value = value;
        this.creationDate = date;
    }
}
