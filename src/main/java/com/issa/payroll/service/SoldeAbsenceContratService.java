package com.issa.payroll.service;

import com.issa.payroll.domain.SoldeAbsenceContrat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SoldeAbsenceContrat}.
 */
public interface SoldeAbsenceContratService {
    /**
     * Save a soldeAbsenceContrat.
     *
     * @param soldeAbsenceContrat the entity to save.
     * @return the persisted entity.
     */
    SoldeAbsenceContrat save(SoldeAbsenceContrat soldeAbsenceContrat);

    /**
     * Partially updates a soldeAbsenceContrat.
     *
     * @param soldeAbsenceContrat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SoldeAbsenceContrat> partialUpdate(SoldeAbsenceContrat soldeAbsenceContrat);

    /**
     * Get all the soldeAbsenceContrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoldeAbsenceContrat> findAll(Pageable pageable);

    /**
     * Get the "id" soldeAbsenceContrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoldeAbsenceContrat> findOne(Long id);

    /**
     * Delete the "id" soldeAbsenceContrat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
