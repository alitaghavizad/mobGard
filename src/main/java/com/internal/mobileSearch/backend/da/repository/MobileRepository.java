package com.internal.mobileSearch.backend.da.repository;

import com.internal.mobileSearch.backend.da.model.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileRepository extends JpaRepository<Mobile,Long>{
    public boolean existsByMobileName(String mobileName);
    public boolean removeByMobileName(String mobileName);
    public Mobile getByMobileName(String mobileName);
}
