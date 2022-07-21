package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Column(nullable = false, name = "curve_id")
    private Long idCurve;

    @Column(nullable = false, name = "asof_date")
    @Temporal(TemporalType.DATE)
    private Date asOfDate;

    @Column(nullable = false, name = "term")
    private double term;

    @Column(nullable = false, name = "value")
    private double value;

    @Column(nullable = false, name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;



    public CurvePoint() {

    }

    public CurvePoint(Long idCurve, double term, double value) {
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
