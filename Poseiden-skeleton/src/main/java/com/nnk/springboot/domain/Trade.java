package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "trade")
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long tradeId;

    @NotBlank(message = "Account is mandatory")
    @Column(nullable = false, name = "account")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column(nullable = false, name = "type")
    private String type;

    @PositiveOrZero
    @NotNull(message = "buy_quantity may not be empty")
    @Column(nullable = false, name = "buy_quantity")
    private Double buyQuantity;

    @Column(nullable = false, name = "sell_quantity")
    private Double sellQuantity;

    @Column(nullable = false, name = "buy_price")
    private Double buyPrice;

    @Column(nullable = false, name = "sell_price")
    private Double sellPrice;

    @Column(nullable = false, name = "trade_date")
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


    public Trade() {

    }

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
