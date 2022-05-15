package com.internal.mobileSearch.backend.service;

import com.internal.mobileSearch.backend.da.model.*;
import com.internal.mobileSearch.backend.da.repository.MobileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class MobileService {
    private final Logger LOGGER = LoggerFactory.getLogger(MobileService.class);

    @Autowired
    private MobileRepository mobileRepository;

    @Transactional
    public boolean addMobile(String mobileName, String avgPrice, Brand brand) {
        if (!mobileExists(mobileName)) {
            Mobile mobile = new Mobile();
            mobile.setMobileName(mobileName);
            mobile.setMobileAvgPrice(avgPrice);
            mobile.setBrand(brand);
            mobile.setInsertionDate(new Date());
            mobile.setLastUpdateDate(new Date());
            mobile.setStatus(MobileStatus.ACTIVE.getStatus());
            try {
                mobileRepository.save(mobile);
                LOGGER.info("Mobile:"+ mobileName+ "Saved In DataBase");
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Save Mobile:"+ mobileName+ "In DataBase");
                return false;
            }
        } else {
            LOGGER.warn("Mobile AllReady Exists: "+mobileName);
            return false;
        }
    }

    @Transactional
    public boolean mobileExists(String mobileName) {
        try {
            Boolean exist=mobileRepository.existsByMobileName(mobileName);
            LOGGER.info("Mobile Exist:"+exist);
            return exist;
        }catch (Exception e){
            LOGGER.error("Failed To Communicate With DataBase(mobileExists)"+mobileName);
            return false;
        }
    }

    @Transactional
    public boolean removeMobile(String mobileName) {
        if (mobileExists(mobileName)) {
            try {
                mobileRepository.removeByMobileName(mobileName);
                LOGGER.info("Mobile removed:"+mobileName);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(removeMobile)"+mobileName);
                return false;
            }
        } else {
            LOGGER.warn("Mobile Does Not Exist: "+mobileName);
            return false;
        }
    }

    @Transactional
    public Mobile getMobile(String mobileName) {
        if (mobileExists(mobileName)) {
            try {
                Mobile mobile=mobileRepository.getByMobileName(mobileName);
                LOGGER.info("Mobile returned :"+mobileName);
                return mobile;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(getMobile)"+mobileName);
                return null;
            }
        } else {
            LOGGER.warn("Mobile Does Not Exist: "+mobileName);
            return null;
        }
    }

    @Transactional
    public List<Mobile> getAllMobileData(){
        try {
            return mobileRepository.findAll();
        }catch (Exception e){
            LOGGER.error("DataBase Connection Failed");
            return null;
        }
    }

    @Transactional
    public boolean updateMobile(String mobileNameOld, String mobileNameNew, String avgPrice, Brand brand,
                                MobileDetails mobileDetails, List<MobilePrice> mobilePrices,int status) {
        if (mobileExists(mobileNameOld)) {
            Mobile mobile = getMobile(mobileNameOld);
            mobile.setMobileDetails(mobileDetails);
            mobile.setMobilePrice(mobilePrices);
            mobile.setMobileName(mobileNameNew);
            mobile.setMobileAvgPrice(avgPrice);
            mobile.setBrand(brand);
            mobile.setStatus(status);
            mobile.setLastUpdateDate(new Date());
            try {
                mobileRepository.save(mobile);
                LOGGER.info("Mobile updated :"+mobileNameOld+" To "+ mobileNameNew);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateMobile)"+mobileNameOld);
                return false;
            }
        } else {
            LOGGER.warn("Mobile Does Not Exist: "+mobileNameOld);
            return false;
        }
    }

    @Transactional
    public boolean updateMobileAvgPrice(String mobileName, String avgPrice) {
        if (mobileExists(mobileName)) {
            Mobile mobile = getMobile(mobileName);
            mobile.setMobileAvgPrice(avgPrice);
            mobile.setLastUpdateDate(new Date());
            try {
                mobileRepository.save(mobile);
                LOGGER.info("Mobile updated To : "+ avgPrice);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateMobile)"+mobileName);
                return false;
            }
        } else {
            LOGGER.warn("Mobile Does Not Exist: "+mobileName);
            return false;
        }
    }

    @Transactional
    public boolean updateMobileStatus(String mobileName, int status) {
        if (mobileExists(mobileName)) {
            Mobile mobile = getMobile(mobileName);
            mobile.setStatus(status);
            mobile.setLastUpdateDate(new Date());
            try {
                mobileRepository.save(mobile);
                LOGGER.info("Mobile updated To : "+ status);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateMobile)"+mobileName);
                return false;
            }
        } else {
            LOGGER.warn("Mobile Does Not Exist: "+mobileName);
            return false;
        }
    }

}
