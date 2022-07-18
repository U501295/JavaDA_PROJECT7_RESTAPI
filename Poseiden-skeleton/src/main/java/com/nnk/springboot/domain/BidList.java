package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Long bidListId;

    @Column(nullable = false, name = "account")
    private String account;

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "bidQuantity")
    private double bidQuantity;

    @Column(nullable = false, name = "askQuantity")
    private double askQuantity;

    @Column(nullable = false, name = "bid")
    private double bid;

    @Column(nullable = false, name = "ask")
    private double ask;

    @Column(nullable = false, name = "benchmark")
    private String benchmark;

    @Column(nullable = false, name = "bidListDate")
    @Temporal(TemporalType.DATE)
    private Date bidListDate;

    @Column(nullable = false, name = "commentary")
    private String commentary;

    @Column(nullable = false, name = "security")
    private String security;

    @Column(nullable = false, name = "status")
    private String status;

    @Column(nullable = false, name = "trader")
    private String trader;

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
