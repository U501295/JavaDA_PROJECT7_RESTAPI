package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "bid_list")
@Getter
@Setter
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Long BidListId;

    @NotBlank(message = "account is mandatory")
    @Column(nullable = false, name = "account")
    private String account;

    @NotBlank(message = "type is mandatory")
    @Column(nullable = false, name = "type")
    private String type;

    @PositiveOrZero
    @NotNull(message = "bidQuantity may not be empty")
    @Column(nullable = false, name = "bid_quantity")
    private Double bidQuantity;

    @Column(nullable = false, name = "ask_quantity")
    private Double askQuantity;

    @Column(nullable = false, name = "bid")
    private Double bid;

    @Column(nullable = false, name = "ask")
    private Double ask;

    @Column(nullable = false, name = "benchmark")
    private String benchmark;

    @Column(nullable = false, name = "bid_list_date")
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

    @Column(nullable = false, name = "creation_name")
    private String creationName;

    @Column(nullable = false, name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(nullable = false, name = "revision_name")
    private String revisionName;

    @Column(nullable = false, name = "revision_date")
    @Temporal(TemporalType.DATE)
    private Date revisionDate;

    @Column(nullable = false, name = "deal_name")
    private String dealName;

    @Column(nullable = false, name = "deal_type")
    private String dealType;

    @Column(nullable = false, name = "source_list_id")
    private String sourceListId;

    @Column(nullable = false, name = "side")
    private String side;


    public BidList() {

    }

    public BidList(String account, String type, Double bidQuantity) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
        this.askQuantity = 20d;
        this.bid = 30d;
        this.ask = 40d;
        this.benchmark = "defaultValue";
        this.bidListDate = date;
        this.commentary = "defaultValue";
        this.security = "defaultValue";
        this.status = "defaultValue";
        this.trader = "defaultValue";
        this.book = "defaultValue";
        this.creationName = "defaultValue";
        this.creationDate = date;
        this.revisionName = "defaultValue";
        this.revisionDate = date;
        this.dealName = "defaultValue";
        this.dealType = "defaultValue";
        this.sourceListId = "defaultValue";
        this.side = "defaultValue";
    }
}
