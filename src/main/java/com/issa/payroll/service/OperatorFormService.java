package com.issa.payroll.service;

import com.issa.payroll.domain.OperatorForm;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OperatorForm}.
 */
public interface OperatorFormService {
    /**
     * Save a operatorForm.
     *
     * @param operatorForm the entity to save.
     * @return the persisted entity.
     */
    OperatorForm save(OperatorForm operatorForm);

    /**
     * Partially updates a operatorForm.
     *
     * @param operatorForm the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperatorForm> partialUpdate(OperatorForm operatorForm);

    /**
     * Get all the operatorForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OperatorForm> findAll(Pageable pageable);

    /**
     * Get the "id" operatorForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperatorForm> findOne(Long id);

    /**
     * Delete the "id" operatorForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
