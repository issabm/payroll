package com.issa.payroll.service;

import com.issa.payroll.domain.FormPaieLigne;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FormPaieLigne}.
 */
public interface FormPaieLigneService {
    /**
     * Save a formPaieLigne.
     *
     * @param formPaieLigne the entity to save.
     * @return the persisted entity.
     */
    FormPaieLigne save(FormPaieLigne formPaieLigne);

    /**
     * Partially updates a formPaieLigne.
     *
     * @param formPaieLigne the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormPaieLigne> partialUpdate(FormPaieLigne formPaieLigne);

    /**
     * Get all the formPaieLignes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormPaieLigne> findAll(Pageable pageable);

    /**
     * Get the "id" formPaieLigne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormPaieLigne> findOne(Long id);

    /**
     * Delete the "id" formPaieLigne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
