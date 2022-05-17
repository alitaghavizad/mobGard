package com.internal.mobileSearch.frontend.controller;

import com.internal.mobileSearch.backend.da.model.Brand;
import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.service.BrandService;
import com.internal.mobileSearch.backend.service.MobileService;
import com.internal.mobileSearch.backend.util.CrawlForBrand;
import com.internal.mobileSearch.backend.util.CrawlForDetails;
import com.internal.mobileSearch.backend.util.CrawlForMobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private MobileService mobileService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/get-brand")
    public void addBrand() {
            crawlForBrand.getBrand();
    }

    @GetMapping("/total")
    public void total() {
        crawlForBrand.getBrand();
        List<Brand> brands=brandService.getAllBrands();
        for (Brand brand:brands){
            Map<String ,String > brandsUrl=new HashMap<>();
            brandsUrl.put(brand.getBrandName(),brand.getBrandUrl());
            crawlForMobile.getMobileData(brandsUrl);
        }
        List<Mobile> mobiles= mobileService.getAllMobileData();
        for (Mobile mobile:mobiles){
            if (mobile.getMobileDetails()==null){
                crawlForDetails.getMobileDetails(mobile.getMobileName(),mobile.getBrand().getBrandName());
            }
        }

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
