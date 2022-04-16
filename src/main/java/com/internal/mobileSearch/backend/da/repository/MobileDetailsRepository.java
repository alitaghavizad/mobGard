package com.internal.mobileSearch.backend.da.repository;

import com.internal.mobileSearch.backend.da.model.Mobile;
import com.internal.mobileSearch.backend.da.model.MobileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileDetailsRepository extends JpaRepository<MobileDetails,Long>{
    public boolean existsByMobile(Mobile mobile);
    public boolean removeByMobile(Mobile mobile);
    public MobileDetails getByMobile(Mobile mobile);
}
