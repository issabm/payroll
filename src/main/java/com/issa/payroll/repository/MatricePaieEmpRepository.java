package com.issa.payroll.repository;

import com.issa.payroll.domain.MatricePaieEmp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MatricePaieEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatricePaieEmpRepository extends JpaRepository<MatricePaieEmp, Long> {}
