package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureAbsenceRebrique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureAbsenceRebrique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureAbsenceRebriqueRepository extends JpaRepository<NatureAbsenceRebrique, Long> {}
