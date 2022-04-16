package com.internal.mobileSearch.backend.service;

import com.internal.mobileSearch.backend.da.model.Brand;
import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.da.model.MobilePrice;
import com.internal.mobileSearch.backend.da.repository.MobilePriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class MobilePriceService {
    private final Logger LOGGER = LoggerFactory.getLogger(MobilePriceService.class);

    @Autowired
    private MobilePriceRepository mobilePriceRepository;

    @Transactional
    public boolean addMobilePrice(String link, Mobile mobile,Long price,String origin){
        if (!mobilePriceExists(link)){
            MobilePrice mobilePrice=new MobilePrice();
            mobilePrice.setPrice(price);
            mobilePrice.setMobile(mobile);
            mobilePrice.setLink(link);
            mobilePrice.setOrigin(origin);
            mobilePrice.setStatus(1);
            mobilePrice.setLastUpdateDate(new Date());
            mobilePrice.setInsertionDate(new Date());
            try {
                mobilePriceRepository.save(mobilePrice);
                LOGGER.info("MobilePrice:"+ link+ "Saved In DataBase");
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Save MobilePrice:"+ link+ "In DataBase");
                return false;
            }
        } else {
            LOGGER.warn("MobilePrice AllReady Exists: "+link);
            return false;
        }
    }

    @Transactional
    public boolean mobilePriceExists(String link){
        try {
            Boolean exist=mobilePriceRepository.existsByLink(link);
            LOGGER.info("MobilePrice Exist:"+exist);
            return exist;
        }catch (Exception e){
            LOGGER.error("Failed To Communicate With DataBase(mobilePriceExists)"+link);
            return false;
        }
    }

    @Transactional
    public boolean removeMobilePrice(String link){
        if (mobilePriceExists(link)){
            try {
                mobilePriceRepository.removeByLink(link);
                LOGGER.info("MobilePrice removed:"+link);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(removeMobilePrice)"+link);
                return false;
            }
        } else {
            LOGGER.warn("MobilePrice Does Not Exist: "+link);
            return false;
        }
    }

    @Transactional
    public MobilePrice getMobilePrice(String link){
        if (mobilePriceExists(link)){
            try {
                MobilePrice mobilePrice=mobilePriceRepository.getByLink(link);
                LOGGER.info("MobilePrice returned :"+mobilePrice.getMobile().getMobileName());
                return mobilePrice;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(getMobilePrice)");
                return null;
            }
        } else {
            LOGGER.warn("MobilePrice Does Not Exist: ");
            return null;
        }
    }

    @Transactional
    public boolean updateMobilePrice(String linkOld,String linkNew, Mobile mobile,Long price,String origin,int status){
        if (mobilePriceExists(linkOld)){
            MobilePrice mobilePrice=getMobilePrice(linkOld);
            mobilePrice.setPrice(price);
            mobilePrice.setMobile(mobile);
            mobilePrice.setStatus(status);
            mobilePrice.setLink(linkNew);
            mobilePrice.setOrigin(origin);
            mobilePrice.setLastUpdateDate(new Date());
            try {
                mobilePriceRepository.save(mobilePrice);
                LOGGER.info("MobilePrice updated :"+mobile.getMobileName());
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateMobilePrice)"+mobile.getMobileName());
                return false;
            }
        } else {
            LOGGER.warn("MobilePrice Does Not Exist: "+mobile.getMobileName());
            return false;
        }
    }
}
