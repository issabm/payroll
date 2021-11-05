package com.issa.payroll.repository;

import com.issa.payroll.domain.SoldeAbsenceContrat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SoldeAbsenceContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoldeAbsenceContratRepository extends JpaRepository<SoldeAbsenceContrat, Long> {}
