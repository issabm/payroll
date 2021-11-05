package com.issa.payroll.repository;

import com.issa.payroll.domain.PalierImpo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PalierImpo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PalierImpoRepository extends JpaRepository<PalierImpo, Long> {}
