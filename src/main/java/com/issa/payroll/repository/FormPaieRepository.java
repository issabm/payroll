package com.issa.payroll.repository;

import com.issa.payroll.domain.FormPaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormPaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormPaieRepository extends JpaRepository<FormPaie, Long> {}
