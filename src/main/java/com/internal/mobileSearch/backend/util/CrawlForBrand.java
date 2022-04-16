package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.service.BrandService;
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
public class CrawlForBrand {
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

       public boolean getBrand(){
        driver.get(webPage);

        //Get All Brands
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
           for (Map.Entry<String, String> entry : brandUrls.entrySet()) {
               System.out.println(entry.getKey()+"   "+entry.getValue());
           }
        return true;
    }

    //todo get ALLL brands

    private void fillBrandInfo(Map<String ,String> brandMap){
           //todo iterate map
    }
}
