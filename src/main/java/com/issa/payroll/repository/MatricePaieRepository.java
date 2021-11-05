package com.issa.payroll.repository;

import com.issa.payroll.domain.MatricePaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MatricePaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatricePaieRepository extends JpaRepository<MatricePaie, Long> {}
