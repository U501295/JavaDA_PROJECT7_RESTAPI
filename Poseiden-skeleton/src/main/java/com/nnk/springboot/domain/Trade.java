package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "trade")
@Getter
@Setter
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Long tradeId;

    @Column(nullable = false, name = "account")
    private String account;

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "buyQuantity")
    private double buyQuantity;

    @Column(nullable = false, name = "sellQuantity")
    private double sellQuantity;

    @Column(nullable = false, name = "buyPrice")
    private double buyPrice;

    @Column(nullable = false, name = "sellPrice")
    private double sellPrice;

    @Column(nullable = false, name = "tradeDate")
    @Temporal(TemporalType.DATE)
    private Date tradeDate;

    @Column(nullable = false, name = "status")
    private String status;

    @Column(nullable = false, name = "trader")
    private String trader;

    @Column(nullable = false, name = "benchmark")
    private String benchmark;

    @Column(nullable = false, name = "book")
    private String book;

    @Column(nullable = false, name = "creationName")
    private String creationName;

    @Column(nullable = false, name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(nullable = false, name = "revisionName")
    private String revisionName;

    @Column(nullable = false, name = "revisionDate")
    @Temporal(TemporalType.DATE)
    private Date revisionDate;

    @Column(nullable = false, name = "dealName")
    private String dealName;

    @Column(nullable = false, name = "dealType")
    private String dealType;

    @Column(nullable = false, name = "sourceListId")
    private String sourceListId;

    @Column(nullable = false, name = "side")
    private String  side;

}
