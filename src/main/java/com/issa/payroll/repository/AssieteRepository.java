package com.issa.payroll.repository;

import com.issa.payroll.domain.Assiete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Assiete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssieteRepository extends JpaRepository<Assiete, Long> {}
