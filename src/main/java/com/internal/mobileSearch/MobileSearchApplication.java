package com.internal.mobileSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class MobileSearchApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Ali\\Desktop\\JavaProjects\\chromedriver_win32\\New folder\\chromedriver.exe");
        SpringApplication.run(MobileSearchApplication.class, args);
    }

}
