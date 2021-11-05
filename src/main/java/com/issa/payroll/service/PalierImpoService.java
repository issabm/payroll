package com.issa.payroll.service;

import com.issa.payroll.domain.PalierImpo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PalierImpo}.
 */
public interface PalierImpoService {
    /**
     * Save a palierImpo.
     *
     * @param palierImpo the entity to save.
     * @return the persisted entity.
     */
    PalierImpo save(PalierImpo palierImpo);

    /**
     * Partially updates a palierImpo.
     *
     * @param palierImpo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PalierImpo> partialUpdate(PalierImpo palierImpo);

    /**
     * Get all the palierImpos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PalierImpo> findAll(Pageable pageable);

    /**
     * Get the "id" palierImpo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PalierImpo> findOne(Long id);

    /**
     * Delete the "id" palierImpo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
