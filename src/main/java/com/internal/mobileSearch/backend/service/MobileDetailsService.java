package com.internal.mobileSearch.backend.service;

import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.da.model.MobileDetails;
import com.internal.mobileSearch.backend.da.repository.MobileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class MobileDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(MobileDetailsService.class);

    @Autowired
    private MobileDetailsRepository mobileDetailsRepository;

    @Transactional
    public boolean addMobileDetails(Mobile mobile,String battery,String body,String comms,String display,
                                    String features,String launch,String mainCamera,String misc,String memory,
                                    String network,String platform,String sound,String tests,String selfieCamera){
        if (!mobileDetailsExists(mobile)){
            MobileDetails mobileDetails=new MobileDetails();
            mobileDetails.setMobile(mobile);
            mobileDetails.setBattery(battery);
            mobileDetails.setBody(body);
            mobileDetails.setComms(comms);
            mobileDetails.setDisplay(display);
            mobileDetails.setFeatures(features);
            mobileDetails.setLaunch(launch);
            mobileDetails.setMainCamera(mainCamera);
            mobileDetails.setMisc(misc);
            mobileDetails.setMemory(memory);
            mobileDetails.setNetwork(network);
            mobileDetails.setPlatform(platform);
            mobileDetails.setSound(sound);
            mobileDetails.setTests(tests);
            mobileDetails.setSelfieCamera(selfieCamera);
            mobileDetails.setInsertionDate(new Date());
            try {
                mobileDetailsRepository.save(mobileDetails);
                LOGGER.info("Mobile Details:"+ mobile.getMobileName()+ "Saved In DataBase");
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Save Mobile Details:"+ mobile.getMobileName()+ "In DataBase");
                return false;
            }
        } else {
            LOGGER.warn("Mobile Details AllReady Exists: "+mobile.getMobileName());
            return false;
        }
    }

    @Transactional
    public boolean mobileDetailsExists(Mobile mobile){
        try {
            Boolean exist=mobileDetailsRepository.existsByMobile(mobile);
            LOGGER.info("Mobile Details Exist:"+exist);
            return exist;
        }catch (Exception e){
            LOGGER.error("Failed To Communicate With DataBase(mobileDetailsExists) :"+mobile.getMobileName());
            return false;
        }
    }

    @Transactional
    public boolean removeMobileDetails(Mobile mobile){
        if (mobileDetailsExists(mobile)){
            try {
                mobileDetailsRepository.removeByMobile(mobile);
                LOGGER.info("Mobile Details removed:"+mobile.getMobileName());
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(removeMobileDetails)"+mobile.getMobileName());
                return false;
            }
        } else {
            LOGGER.warn("Mobile Details Does Not Exist"+mobile.getMobileName());
            return false;
        }
    }

    @Transactional
    public MobileDetails getMobileDetails(Mobile mobile){
        if (mobileDetailsExists(mobile)){
            try {
                MobileDetails mobileDetails=mobileDetailsRepository.getByMobile(mobile);
                LOGGER.info("Mobile Details returned :"+mobile.getMobileName());
                return mobileDetails;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(getMobile)"+mobile.getMobileName());
                return null;
            }
        } else {
            LOGGER.warn("Mobile Details Does Not Exist: "+mobile.getMobileName());
            return null;
        }
    }

    @Transactional
    public boolean updateMobileDetails(Mobile mobile,String battery,String body,String comms,String display,
                                       String features,String launch,String mainCamera,String misc,String memory,
                                       String network,String platform,String sound,String tests,String selfieCamera){
        if (mobileDetailsExists(mobile)){
            MobileDetails mobileDetails=getMobileDetails(mobile);
            mobileDetails.setBattery(battery);
            mobileDetails.setBody(body);
            mobileDetails.setComms(comms);
            mobileDetails.setDisplay(display);
            mobileDetails.setFeatures(features);
            mobileDetails.setLaunch(launch);
            mobileDetails.setMainCamera(mainCamera);
            mobileDetails.setMisc(misc);
            mobileDetails.setMemory(memory);
            mobileDetails.setNetwork(network);
            mobileDetails.setPlatform(platform);
            mobileDetails.setSound(sound);
            mobileDetails.setTests(tests);
            mobileDetails.setSelfieCamera(selfieCamera);
            try {
                mobileDetailsRepository.save(mobileDetails);
                LOGGER.info("Mobile Details updated :"+mobile.getMobileName());
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateMobileDetails)"+mobile.getMobileName());
                return false;
            }
        } else {
            LOGGER.warn("Mobile Details Does Not Exist: "+mobile.getMobileName());
            return false;
        }
    }
}
