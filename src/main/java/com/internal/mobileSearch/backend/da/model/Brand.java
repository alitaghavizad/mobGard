package com.internal.mobileSearch.backend.da.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity(name = "BRAND")
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "BRAND_NAME")
    private String brandName;

    @NotNull
    @Column(name = "BRAND_URL")
    private String brandUrl;

    @Column(name = "STATUS")
    private int status;

    @OneToMany
    private List<Mobile> mobile;

    @Column(name = "INSERTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionDate;

    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
}
