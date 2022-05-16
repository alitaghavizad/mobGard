package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.da.model.BrandStatus;
import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.da.model.MobileStatus;
import com.internal.mobileSearch.backend.service.BrandService;
import com.internal.mobileSearch.backend.service.MobileService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrawlForMobile {
    private final Logger LOGGER = LoggerFactory.getLogger(CrawlForMobile.class);

    @Autowired
    private WebDriver driver;

    @Autowired
    private MobileService mobileService;

    @Autowired
    private BrandService brandService;

    @Value("${mobile.ir.phones.grid}")
    private String phoneGrid;

    @Value("${mobile.ir.mobiles}")
    private String mobiles;

    @Value("${mobile.ir.css.class}")
    private String cssClass;

    @Value("${mobile.ir.phone.available.class.1}")
    private String phoneAvailableClass1;

    @Value("${mobile.ir.phone.available.class.2}")
    private String phoneAvailableClass2;

    @Value("${mobile.ir.mobile.tag}")
    private String mobileTag;

    @Value("${mobile.ir.mobile.name.tag}")
    private String mobileNameTag;

    @Value("${mobile.ir.mobile.price.tag}")
    private String mobilePriceTag;

    @Value("${mobile.ir.pagination.box}")
    private String paginationBoxClass;

    @Value("${mobile.ir.pagination}")
    private String paginationClass;

    @Value("${mobile.ir.pagination.anchor.tag}")
    private String paginationAnchorTag;

    @Value("${mobile.ir.pagination.url}")
    private String paginationUrl;

    public boolean getMobileData(Map<String, String> brandUrls) {
        for (Map.Entry<String, String> entry : brandUrls.entrySet()) {
            try {
                List<String> pagesUrl = getAllOfMobilePagesUrlSelenium(brandUrls);
                Map<String, Map<String, String>> mobileDetails = getMobileDataSelenium(pagesUrl);
                if (mobileDetails != null) {
                    for (Map.Entry<String, Map<String, String>> entry1 : mobileDetails.entrySet()) {
                        String price = "";
                        String url = "";
                        for (Map.Entry<String, String> entry2 : entry1.getValue().entrySet()) {
                            switch (entry2.getKey()) {
                                case "price":
                                    price = entry2.getValue();
                                    break;
                                case "url":
                                    url = entry2.getValue();
                                    break;
                            }
                        }
                        fillMobileData(entry1.getKey(), price, url, entry.getKey());
                    }
                } else {
                    brandService.updateBrandStatus(entry.getKey(), BrandStatus.INACTIVE.getStatus());
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public List<String> getAllOfMobilePagesUrlSelenium(Map<String, String> brandUrls) {
        List<String> pagesUrl = new ArrayList<>();
        for (Map.Entry<String, String> entry : brandUrls.entrySet()) {
            pagesUrl.add(entry.getValue());
            driver.get(entry.getValue());
            try {
                WebElement paginationBox = driver.findElement(By.className(paginationBoxClass));
                WebElement pagination = paginationBox.findElement(By.className(paginationClass));
                List<WebElement> pages = pagination.findElements(By.tagName(paginationAnchorTag));
                for (WebElement webElement : pages) {
                    pagesUrl.add(webElement.getAttribute(paginationUrl));
                }
                pagesUrl.remove(pagesUrl.size() - 1);
                return pagesUrl;
            } catch (Exception e) {
                return pagesUrl;
            }
        }
        return pagesUrl;
    }

    public Map<String, Map<String, String>> getMobileDataSelenium(List<String> pagesUrl) {
        Map<String, Map<String, String>> mobilesNamesAndAvgPrices = new HashMap<>();
        List<String> allMobNames = new ArrayList<>();
        for (String url : pagesUrl) {
            driver.get(url);
            WebElement phonesGrid = driver.findElement(By.className(phoneGrid));
            List<WebElement> mobs = phonesGrid.findElements(By.tagName(mobiles));
            if (mobs.size() > 2) {
                for (WebElement div : mobs) {
                    Map<String, String> mobileAvgPriceAndUrl = new HashMap<>();
                    if (div.getAttribute(cssClass).equals(phoneAvailableClass1 + " " + phoneAvailableClass2)) {
                        WebElement h4 = div.findElement(By.tagName(mobileTag));
                        WebElement mobileUrl = div.findElement(By.tagName("a"));
                        WebElement mobName = h4.findElement(By.tagName(mobileNameTag));
                        allMobNames.add(mobName.getText());
                        WebElement mobPrice = div.findElement(By.tagName(mobilePriceTag));
                        String priceOfMobile = mobPrice.getText();
                        priceOfMobile = priceOfMobile.replace("تومان", "");
                        priceOfMobile = priceOfMobile.replace(",", "");
                        priceOfMobile = priceOfMobile.replace(" ", "");

                        mobileAvgPriceAndUrl.put("url", mobileUrl.getAttribute("href"));
                        mobileAvgPriceAndUrl.put("price", priceOfMobile);

                        LOGGER.info("MobileName: " + mobName.getText() + ", PRICE: " + priceOfMobile);
                        mobilesNamesAndAvgPrices.put(mobName.getText(), mobileAvgPriceAndUrl);
                    }
                }
            } else {
                return null;
            }
        }
        removeOldMobileData(allMobNames);
        return mobilesNamesAndAvgPrices;
    }

    public void fillMobileData(String mobileName, String avgPrice, String url, String brandName) {
        try {
            if (!mobileService.mobileExists(mobileName)) {
                mobileService.addMobile(mobileName, avgPrice, url, brandService.getBrand(brandName));
                LOGGER.info("Added New Mobile: " + mobileName);
            } else {
                mobileService.updateMobileAvgPriceAndUrl(mobileName, avgPrice, url);
                mobileService.updateMobileStatus(mobileName, MobileStatus.ACTIVE.getStatus());
                LOGGER.info("Updated Mobile Avg Price: " + mobileName + " And Status To Active");
            }
        } catch (Exception e) {
            LOGGER.error("DataBase Connection Failed");
        }
    }

    public void removeOldMobileData(List<String> allMobNames) {
        try {
            List<Mobile> allMobileData = mobileService.getAllMobileData();
            for (Mobile mobile : allMobileData) {
                if (!allMobNames.contains(mobile.getMobileName())) {
                    mobileService.updateMobileStatus(mobile.getMobileName(), MobileStatus.INACTIVE.getStatus());
                    LOGGER.info("Mobile: " + mobile.getMobileName() + " Status Has Been Updated To: " + MobileStatus.INACTIVE.name());
                }
            }
        } catch (Exception e) {
            LOGGER.error("DataBase Connection Failed");
        }
    }
}