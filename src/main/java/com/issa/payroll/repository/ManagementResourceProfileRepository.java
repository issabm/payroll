package com.issa.payroll.repository;

import com.issa.payroll.domain.ManagementResourceProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ManagementResourceProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagementResourceProfileRepository extends JpaRepository<ManagementResourceProfile, Long> {}
