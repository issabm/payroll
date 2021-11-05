package com.issa.payroll.repository;

import com.issa.payroll.domain.FormPaieLigne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormPaieLigne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormPaieLigneRepository extends JpaRepository<FormPaieLigne, Long> {}
