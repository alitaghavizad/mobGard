package com.internal.mobileSearch.frontend.controller;

import com.internal.mobileSearch.backend.util.CrawlForBrand;
import com.internal.mobileSearch.backend.util.CrawlForDetails;
import com.internal.mobileSearch.backend.util.CrawlForMobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/brand")
public class BrandController {
    private final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private CrawlForBrand crawlForBrand;

    @Autowired
    private CrawlForMobile crawlForMobile;

    @Autowired
    private CrawlForDetails crawlForDetails;

    @GetMapping("/get-brand")
    public void addBrand() {
            crawlForBrand.getBrand();
    }

    @GetMapping("/get")
    public void addBrands(String mobileName,String brandName) {
        crawlForDetails.getMobileDetails(mobileName,brandName);
    }

    @GetMapping("/gets")
    public void addsBrand(String brandName,String brandUrl) {
        Map<String ,String > brandsUrl=new HashMap<>();
        brandsUrl.put(brandName,brandUrl);
        crawlForMobile.getMobileData(brandsUrl);
    }

}
