package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.da.model.Brand;
import com.internal.mobileSearch.backend.da.model.BrandStatus;
import com.internal.mobileSearch.backend.service.BrandService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrawlForBrand {
    private final Logger LOGGER = LoggerFactory.getLogger(CrawlForBrand.class);

    @Autowired
    private WebDriver driver;
    @Autowired
    private BrandService brandService;

    @Value("${main.web.page}")
    private String webPage;

    @Value("${mobile.ir.side.menu}")
    private String sideMenu;

    @Value("${mobile.ir.brands.list.item}")
    private String listItem;

    @Value("${mobile.ir.brands.span}")
    private String span;

    @Value("${mobile.ir.brands.anchor.tag}")
    private String anchorTag;

    @Value("${mobile.ir.brands.url}")
    private String url;


    public boolean getBrand() {
        try {
            Map<String ,String > brandUrls=getBrandSelenium();
            fillBrandInfo(brandUrls);
            removeOldBrands(brandUrls);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Map<String ,String > getBrandSelenium(){
        try {
            //open the main page of mobile.ir
            driver.get(webPage);
            //search for the list of brands that are on right side of the page
            WebElement ul = driver.findElement(By.className(sideMenu));
            List<WebElement> lis = ul.findElements(By.tagName(listItem));
            Map<String, String> brandUrls = new HashMap<>();
            for (WebElement li : lis) {
                List<WebElement> spans = li.findElements(By.tagName(span));
                if (spans.size() != 0) {
                    WebElement a = li.findElement(By.tagName(anchorTag));
                    brandUrls.put(spans.get(1).getText(), a.getAttribute(url));
                }
            }
            //returning the map that contains the keys of brands names and values of brand urls
            return brandUrls;
        }catch (Exception e){
            LOGGER.error("Selenium Failed");
            return null;
        }
    }

    private void fillBrandInfo(Map<String, String> brandUrls) {
        try {
            for (Map.Entry<String, String> entry : brandUrls.entrySet()) {
                if (!brandService.brandExists(entry.getKey())) {
                    brandService.addBrand(entry.getKey(), entry.getValue());
                    LOGGER.info("Added New Brand: "+ entry.getKey());
                } else if (!brandService.getBrand(entry.getKey()).getBrandUrl().equals(entry.getValue())) {
                    brandService.updateBrand(entry.getKey(), entry.getKey(), entry.getValue(), BrandStatus.ACTIVE.getStatus());
                    LOGGER.info("Brand: "+entry.getKey()+" Url Updated: "+ entry.getValue());
                }
            }
        }catch (Exception e){
            LOGGER.error("DataBase Connection Error");
        }
    }

    private void removeOldBrands(Map<String, String> brandUrls){
        try {
            List<Brand> brands=brandService.getAllBrands();
            for (Brand brand:brands){
                if (!brandUrls.containsKey(brand.getBrandName()) && !brandUrls.containsValue(brand.getBrandUrl())) {
                    brandService.updateBrand(brand.getBrandName(),brand.getBrandName(),brand.getBrandUrl(),BrandStatus.INACTIVE.getStatus());
                    LOGGER.info("Brand: "+brand.getBrandName()+" Status Updated To: "+BrandStatus.INACTIVE.name());
                }
            }
        }catch (Exception e){
            LOGGER.error("DataBase Connection Error");
        }

    }
}
