package com.internal.mobileSearch.backend.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AppJob {
    private final Logger LOGGER = LoggerFactory.getLogger(AppJob.class);

    @Scheduled(fixedDelay = 86400000)
    public void dailyCrawl() {

    }
}
