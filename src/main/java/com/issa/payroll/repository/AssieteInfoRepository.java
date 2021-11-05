package com.issa.payroll.repository;

import com.issa.payroll.domain.AssieteInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AssieteInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssieteInfoRepository extends JpaRepository<AssieteInfo, Long> {}
