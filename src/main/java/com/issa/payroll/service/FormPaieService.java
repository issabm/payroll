package com.issa.payroll.service;

import com.issa.payroll.domain.FormPaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FormPaie}.
 */
public interface FormPaieService {
    /**
     * Save a formPaie.
     *
     * @param formPaie the entity to save.
     * @return the persisted entity.
     */
    FormPaie save(FormPaie formPaie);

    /**
     * Partially updates a formPaie.
     *
     * @param formPaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormPaie> partialUpdate(FormPaie formPaie);

    /**
     * Get all the formPaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormPaie> findAll(Pageable pageable);

    /**
     * Get the "id" formPaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormPaie> findOne(Long id);

    /**
     * Delete the "id" formPaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
