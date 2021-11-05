package com.issa.payroll.service;

import com.issa.payroll.domain.RebriqueContrat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RebriqueContrat}.
 */
public interface RebriqueContratService {
    /**
     * Save a rebriqueContrat.
     *
     * @param rebriqueContrat the entity to save.
     * @return the persisted entity.
     */
    RebriqueContrat save(RebriqueContrat rebriqueContrat);

    /**
     * Partially updates a rebriqueContrat.
     *
     * @param rebriqueContrat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RebriqueContrat> partialUpdate(RebriqueContrat rebriqueContrat);

    /**
     * Get all the rebriqueContrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RebriqueContrat> findAll(Pageable pageable);

    /**
     * Get the "id" rebriqueContrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RebriqueContrat> findOne(Long id);

    /**
     * Delete the "id" rebriqueContrat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
