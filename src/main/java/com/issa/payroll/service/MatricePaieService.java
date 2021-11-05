package com.issa.payroll.service;

import com.issa.payroll.domain.MatricePaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MatricePaie}.
 */
public interface MatricePaieService {
    /**
     * Save a matricePaie.
     *
     * @param matricePaie the entity to save.
     * @return the persisted entity.
     */
    MatricePaie save(MatricePaie matricePaie);

    /**
     * Partially updates a matricePaie.
     *
     * @param matricePaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatricePaie> partialUpdate(MatricePaie matricePaie);

    /**
     * Get all the matricePaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatricePaie> findAll(Pageable pageable);

    /**
     * Get the "id" matricePaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatricePaie> findOne(Long id);

    /**
     * Delete the "id" matricePaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
