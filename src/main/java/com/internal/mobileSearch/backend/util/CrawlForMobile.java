package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.service.BrandService;
import com.internal.mobileSearch.backend.service.MobileService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrawlForMobile {
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


    public boolean getMobileData(Map<String ,String > brandUrls) {
        Map<String, String> allMobNames = new HashMap<>();
        for (Map.Entry<String, String> entry : brandUrls.entrySet()) {
            driver.get(entry.getValue());
            try {
                WebElement phonesGrid = driver.findElement(By.className(phoneGrid));
                List<WebElement> mobs = phonesGrid.findElements(By.tagName(mobiles));
                for (WebElement div : mobs) {
                    if (div.getAttribute(cssClass).equals(phoneAvailableClass1+" "+phoneAvailableClass2)) {
                        WebElement h4 = div.findElement(By.tagName(mobileTag));
                        WebElement mobName = h4.findElement(By.tagName(mobileNameTag));
                        allMobNames.put(mobName.getText(), entry.getKey());
                        WebElement mobPrice = driver.findElement(By.tagName(mobilePriceTag));
                        System.out.println("Brand: " + entry.getKey() + ", MobileName: " + mobName.getText() + ", PRICE: " + mobPrice.getText());
                        fillMobileData(mobName.getText(),mobPrice.getText(),entry.getKey());
                    }
                }
                removeOldMobileData(allMobNames);
            } catch (Exception e) {
                System.out.println("\n");
            }
        }
        return true;
    }

    public void fillMobileData(String mobileName,String avgPrice,String brandName){
        if (!mobileService.mobileExists(mobileName)){
            mobileService.addMobile(mobileName,avgPrice,brandService.getBrand(brandName));
        }else{
            mobileService.updateMobileAvgPrice(mobileName,avgPrice);
        }
    }

    public void removeOldMobileData(Map<String,String > allMobNames){
        List<Mobile> allMobileData=mobileService.getAllMobileData();
        for (Mobile mobile:allMobileData){
            if (!allMobNames.containsKey(mobile.getMobileName())){
                mobileService.updateMobileStatus(mobile.getMobileName(),0);
            }
        }
    }
}
//todo add try catch to the methods
//todo status Enum