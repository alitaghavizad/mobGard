package com.internal.mobileSearch.backend.util;

import com.internal.mobileSearch.backend.service.MobileDetailsService;
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
public class CrawlForDetails {
    private final Logger LOGGER = LoggerFactory.getLogger(CrawlForDetails.class);

    @Autowired
    private WebDriver driver;

    @Autowired
    private MobileDetailsService mobileDetailsService;

    @Autowired
    private MobileService mobileService;

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

    public boolean getMobileDetails(String mobileName, String brandName) {
        Map<String ,String > mobileDetailsMap=getMobileDetailsSelenium(mobileName,brandName);
        FillMobileDetails(mobileDetailsMap, mobileName);
        return true;
    }

    public Map<String ,String > getMobileDetailsSelenium(String mobileName,String brandName){
        Map<String, String> mobileDetailsMap = new HashMap<>();
        try {
            //go to gsm Arena
            driver.get(gsmArenaUrl);
            //search for Mobile
            WebElement searchInput = driver.findElement(By.name(searchBar));
            searchInput.sendKeys(brandName + " " + mobileName);
            searchInput.sendKeys("\n");
            //in the list of mobiles found Pick The first one
            WebElement mobileLists = driver.findElement(By.className(mobilesLists));
            WebElement mobileList = mobileLists.findElement(By.tagName(mobilesList));
            List<WebElement> mobileTags = mobileList.findElements(By.tagName(mobileTag));
            WebElement myMobile = mobileTags.get(0);//todo change to similar name
            WebElement myMobiles = myMobile.findElement(By.tagName(mobileAnchorTag));
            String myMobileUrl = myMobiles.getAttribute(mobileUrl);
            //go to specified Mobile page
            driver.get(myMobileUrl);
            WebElement mobileDetails = driver.findElement(By.cssSelector(specList));
            List<WebElement> specs = mobileDetails.findElements(By.tagName(specTable));
            //iterate the list of mobile specs
            for (WebElement spec : specs) {
                String microData = "";
                WebElement tbody = spec.findElement(By.tagName(specTableBody));
                WebElement tr = tbody.findElement(By.tagName(specTableRow));
                WebElement th = tr.findElement(By.tagName(specTableHeader));
                List<WebElement> tableData = tr.findElements(By.tagName(specTableData));
                //iterate the information for each spec
                for (WebElement data : tableData) {
                    microData = microData + "," + (data.getText());
                }
                mobileDetailsMap.put(th.getText(), microData);
            }
            Thread.sleep(20000);
            return mobileDetailsMap;
        } catch (Exception e) {
            System.out.println("!!!Warning: " + mobileName);
            try {
                Thread.sleep(20000);
                return mobileDetailsMap;
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return null;
            }
        }
    }


    public void FillMobileDetails(Map<String, String> mobileDetailsMap, String mobileName) {
        String network = "";
        String launch = "";
        String display = "";
        String platform = "";
        String memory = "";
        String mainCamera = "";
        String selfieCamera = "";
        String sound = "";
        String comms = "";
        String features = "";
        String battery = "";
        String misc = "";
        String tests = "";
        String body = "";
        for (Map.Entry<String, String> entry : mobileDetailsMap.entrySet()) {
            if (entry.getValue().length()<255){
                switch (entry.getKey()) {
                    case "NETWORK": {
                        network = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "LAUNCH": {
                        launch = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "DISPLAY": {
                        display = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "PLATFORM": {
                        platform = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "MEMORY": {
                        memory = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "MAIN CAMERA": {
                        mainCamera = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "SELFIE CAMERA": {
                        selfieCamera = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "SOUND": {
                        sound = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "COMMS": {
                        comms = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "FEATURES": {
                        features = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "BATTERY": {
                        battery = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "MISC": {
                        misc = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "TESTS": {
                        tests = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    case "BODY": {
                        body = entry.getValue();
                        LOGGER.info("Information Added: " + entry.getKey() + entry.getValue());
                        break;
                    }
                    default: {
                        LOGGER.error("Information Malfunction: " + entry.getKey());
                        break;
                    }
                }
            }else {
                LOGGER.error(entry.getKey()+" , "+entry.getValue().length());
            }

        }
        mobileDetailsService.addMobileDetails(mobileService.getMobile(mobileName), battery, body, comms, display, features, launch, mainCamera, misc, memory, network, platform, sound, tests, selfieCamera);
    }
}
//todo Longer Than 255 Fields
