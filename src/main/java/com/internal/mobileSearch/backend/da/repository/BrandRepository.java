package com.internal.mobileSearch.backend.da.repository;

import com.internal.mobileSearch.backend.da.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long>{
    public boolean existsByBrandName(String brandName);
    public Brand getByBrandName(String brandName);
    public boolean removeByBrandName(String brandName);
}
