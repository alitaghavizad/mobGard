package com.internal.mobileSearch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaRepositories("com.internal.mobileSearch.backend.da.repository")
@EnableAutoConfiguration
@EnableScheduling
public class AppConfig {
    @Bean
    public WebDriver webDriver(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return  driver;
    }
}
