package com.internal.mobileSearch.backend.da.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "MOBILE_DETAILS")
@Getter
@Setter
public class MobileDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Mobile mobile;

    @Column(name = "NETWORK")
    private String network;

    @Column(name = "LAUNCH")
    private String launch;

    @Column(name = "BODY")
    private String body;

    @Column(name = "DISPLAY")
    private String display;

    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "MEMORY")
    private String memory;

    @Column(name = "MAIN_CAMERA")
    private String mainCamera;

    @Column(name = "SELFIE_CAMERA")
    private String selfieCamera;

    @Column(name = "SOUND")
    private String sound;

    @Column(name = "COMMS")
    private String comms;

    @Column(name = "FEATURES")
    private String features;

    @Column(name = "BATTERY")
    private String battery;

    @Column(name = "MISC")
    private String misc;

    @Column(name = "TESTS")
    private String tests;

    @Column(name = "INSERTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionDate;
}
