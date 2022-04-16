package com.internal.mobileSearch.backend.da.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity(name = "MOBILE")
@Getter
@Setter
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Brand brand;

    @OneToOne
    private MobileDetails mobileDetails;

    @OneToMany
    private List<MobilePrice> mobilePrice;

    @NotNull
    @Column(name = "MOBILE_NAME")
    private String mobileName;

    @Column(name = "MOBILE_AVG_PRICE")
    private String mobileAvgPrice;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "INSERTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionDate;

    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

}
