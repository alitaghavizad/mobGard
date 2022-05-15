package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.service.MobileDetailsService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrawlForDetails {
    private final Logger LOGGER = LoggerFactory.getLogger(CrawlForDetails.class);

    @Autowired
    private WebDriver driver;

    @Autowired
    private MobileDetailsService mobileDetailsService;

    @Value("${gsm.arena.url}")
    private String gsmArenaUrl;

    @Value("${gsm.arena.search.bar}")
    private String searchBar;

    @Value("${gsm.arena.mobiles.lists}")
    private String mobilesLists;

    @Value("${gsm.arena.mobiles.list}")
    private String mobilesList;

    @Value("${gsm.arena.mobile.tag}")
    private String mobileTag;

    @Value("${gsm.arena.mobile.anchor.tag}")
    private String mobileAnchorTag;

    @Value("${gsm.arena.mobile.url}")
    private String mobileUrl;

    @Value("${gsm.arena.spec.list}")
    private String specList;

    @Value("${gsm.arena.spec.table}")
    private String specTable;

    @Value("${gsm.arena.spec.table.body}")
    private String specTableBody;

    @Value("${gsm.arena.spec.table.row}")
    private String specTableRow;

    @Value("${gsm.arena.spec.table.header}")
    private String specTableHeader;

    @Value("${gsm.arena.spec.table.data}")
    private String specTableData;

    public boolean getMobileDetails(String mobileName,String brandName) {
        try {
            driver.get(gsmArenaUrl);
            WebElement searchInput = driver.findElement(By.name(searchBar));
            searchInput.sendKeys(brandName + " " + mobileName);
            searchInput.sendKeys("\n");
            WebElement mobileLists = driver.findElement(By.className(mobilesLists));
            WebElement mobileList = mobileLists.findElement(By.tagName(mobilesList));
            List<WebElement> mobileTags = mobileList.findElements(By.tagName(mobileTag));
            WebElement myMobile = mobileTags.get(0);//todo change to similar name
            WebElement myMobiles = myMobile.findElement(By.tagName(mobileAnchorTag));
            String myMobileUrl = myMobiles.getAttribute(mobileUrl);
            driver.get(myMobileUrl);
            WebElement mobileDetails = driver.findElement(By.cssSelector(specList));
            List<WebElement> specs = mobileDetails.findElements(By.tagName(specTable));
            for (WebElement spec : specs) {
                WebElement tbody = spec.findElement(By.tagName(specTableBody));
                WebElement tr = tbody.findElement(By.tagName(specTableRow));
                WebElement th = tr.findElement(By.tagName(specTableHeader));
                System.out.println(th.getText() + " : ");
                List<WebElement> tableData = tr.findElements(By.tagName(specTableData));
                for (WebElement data : tableData) {
                    System.out.printf(data.getText() + ",");
                }
                LOGGER.error("Selenium Failed");
            }
            Thread.sleep(20000);
        } catch (Exception e) {
            System.out.println("!!!Warning: " + mobileName);
            try {
                Thread.sleep(20000);
                return false;
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void FillMobileDetails(){
        //todo Kinda Tricky
    }
}
