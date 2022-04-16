package com.internal.mobileSearch.backend.da.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "MOBILE_PRICE")
@Getter
@Setter
public class MobilePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Mobile mobile;

    @NotNull
    @Column(name = "PRICE")
    private Long price;

    @Column(name = "LINK")
    private String link;

    @Column(name = "ORIGIN")
    private String origin;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "INSERTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionDate;

    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
}
