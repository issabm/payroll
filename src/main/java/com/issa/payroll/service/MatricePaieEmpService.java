package com.issa.payroll.service;

import com.issa.payroll.domain.MatricePaieEmp;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MatricePaieEmp}.
 */
public interface MatricePaieEmpService {
    /**
     * Save a matricePaieEmp.
     *
     * @param matricePaieEmp the entity to save.
     * @return the persisted entity.
     */
    MatricePaieEmp save(MatricePaieEmp matricePaieEmp);

    /**
     * Partially updates a matricePaieEmp.
     *
     * @param matricePaieEmp the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatricePaieEmp> partialUpdate(MatricePaieEmp matricePaieEmp);

    /**
     * Get all the matricePaieEmps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatricePaieEmp> findAll(Pageable pageable);

    /**
     * Get the "id" matricePaieEmp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatricePaieEmp> findOne(Long id);

    /**
     * Delete the "id" matricePaieEmp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
