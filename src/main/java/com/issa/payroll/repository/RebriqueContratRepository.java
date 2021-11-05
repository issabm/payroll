package com.issa.payroll.repository;

import com.issa.payroll.domain.RebriqueContrat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RebriqueContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RebriqueContratRepository extends JpaRepository<RebriqueContrat, Long> {}
