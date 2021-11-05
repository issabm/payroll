package com.issa.payroll.repository;

import com.issa.payroll.domain.OperatorForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OperatorForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatorFormRepository extends JpaRepository<OperatorForm, Long> {}
