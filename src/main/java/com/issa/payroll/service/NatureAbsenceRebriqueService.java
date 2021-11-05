package com.issa.payroll.service;

import com.issa.payroll.domain.NatureAbsenceRebrique;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureAbsenceRebrique}.
 */
public interface NatureAbsenceRebriqueService {
    /**
     * Save a natureAbsenceRebrique.
     *
     * @param natureAbsenceRebrique the entity to save.
     * @return the persisted entity.
     */
    NatureAbsenceRebrique save(NatureAbsenceRebrique natureAbsenceRebrique);

    /**
     * Partially updates a natureAbsenceRebrique.
     *
     * @param natureAbsenceRebrique the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureAbsenceRebrique> partialUpdate(NatureAbsenceRebrique natureAbsenceRebrique);

    /**
     * Get all the natureAbsenceRebriques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureAbsenceRebrique> findAll(Pageable pageable);

    /**
     * Get the "id" natureAbsenceRebrique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureAbsenceRebrique> findOne(Long id);

    /**
     * Delete the "id" natureAbsenceRebrique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
