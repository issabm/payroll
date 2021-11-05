package com.issa.payroll.repository;

import com.issa.payroll.domain.FormPaieLigneReb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormPaieLigneReb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormPaieLigneRebRepository extends JpaRepository<FormPaieLigneReb, Long> {}
