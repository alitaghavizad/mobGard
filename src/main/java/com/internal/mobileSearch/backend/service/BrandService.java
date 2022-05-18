package com.internal.mobileSearch.backend.service;

import com.internal.mobileSearch.backend.da.model.Brand;
import com.internal.mobileSearch.backend.da.model.BrandStatus;
import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.da.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class BrandService {
    private final Logger LOGGER = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    public boolean addBrand(String brandName,String brandUrl){
        if (!brandExists(brandName)){
            Brand brand=new Brand();
            brand.setBrandUrl(brandUrl);
            brand.setBrandName(brandName);
            brand.setStatus(BrandStatus.EXISTING.getStatus());
            brand.setInsertionDate(new Date());
            brand.setLastUpdateDate(new Date());
            try {
                brandRepository.save(brand);
                LOGGER.info("Brand:"+ brandName+ "Saved In DataBase");
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Save Brand:"+ brandName+ "In DataBase");
                return false;
            }
        } else {
            LOGGER.warn("Brand AllReady Exists: "+brandName);
            return false;
        }
    }

    @Transactional
    public boolean brandExists(String brandName){
        try {
            Boolean exist=brandRepository.existsByBrandName(brandName);
            LOGGER.info("Brand Exist:"+exist);
            return exist;
        }catch (Exception e){
            LOGGER.error("Failed To Communicate With DataBase(brandExists)"+brandName);
            return false;
        }
    }



    @Transactional
    public boolean removeBrand(String brandName){
        if (brandExists(brandName)){
            try {
                brandRepository.removeByBrandName(brandName);
                LOGGER.info("Brand removed:"+brandName);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(removeBrand)"+brandName);
                return false;
            }
        } else {
            LOGGER.warn("Brand Does Not Exist: "+brandName);
            return false;
        }
    }

    @Transactional
    public Brand getBrand(String brandName){
        if (brandExists(brandName)){
            try {
                Brand brand=brandRepository.getByBrandName(brandName);
                LOGGER.info("Brand returned :"+brandName);
                return brand;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(getBrand)"+brandName);
                return null;
            }
        } else {
            LOGGER.warn("Brand Does Not Exist: "+brandName);
            return null;
        }
    }

    @Transactional
    public List<Brand> getAllBrands(){
        try {
            return brandRepository.findAll();
        }catch (Exception e){
            LOGGER.error("DataBase Connection Failed");
            return null;
        }
    }

    @Transactional
    public boolean updateBrand(String brandNameOld,String brandNameNew,String brandUrlNew,int status){
        if (brandExists(brandNameOld)){
            Brand brand=getBrand(brandNameOld);
            brand.setBrandName(brandNameNew);
            brand.setBrandUrl(brandUrlNew);
            brand.setLastUpdateDate(new Date());
            brand.setStatus(status);
            try {
                brandRepository.save(brand);
                LOGGER.info("Brand updated :"+brandNameOld+" To "+ brandNameNew);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateBrand)"+brandNameOld);
                return false;
            }
        } else {
            LOGGER.warn("Brand Does Not Exist: "+brandNameOld);
            return false;
        }
    }

    @Transactional
    public boolean updateBrandStatus(String brandName,int status){
        if (brandExists(brandName)){
            Brand brand=getBrand(brandName);
            brand.setLastUpdateDate(new Date());
            brand.setStatus(status);
            try {
                brandRepository.save(brand);
                LOGGER.info("Brand updated :"+brandName+" To "+ status);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateBrand)"+brandName);
                return false;
            }
        } else {
            LOGGER.warn("Brand Does Not Exist: "+brandName);
            return false;
        }
    }

    @Transactional
    public boolean updateBrandMobiles(String brandName, Mobile mobile){
        if (brandExists(brandName)){
            Brand brand=getBrand(brandName);
            brand.setLastUpdateDate(new Date());
            List<Mobile> brandMobiles=brand.getMobile();
            brandMobiles.add(mobile);
            brand.setMobile(brandMobiles);
            try {
                brandRepository.save(brand);
                LOGGER.info("Brand updated :"+brandName);
                return true;
            }catch (Exception e){
                LOGGER.error("Failed To Communicate With DataBase(updateBrand)"+brandName);
                return false;
            }
        } else {
            LOGGER.warn("Brand Does Not Exist: "+brandName);
            return false;
        }
    }

}
