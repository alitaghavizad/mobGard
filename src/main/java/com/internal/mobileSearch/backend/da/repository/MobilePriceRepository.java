package com.internal.mobileSearch.backend.da.repository;

import com.internal.mobileSearch.backend.da.model.MobilePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobilePriceRepository extends JpaRepository<MobilePrice,Long>{
    public boolean existsByLink(String link);
    public boolean removeByLink(String link);
    public MobilePrice getByLink(String link);
}
