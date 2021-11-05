package com.issa.payroll.service;

import com.issa.payroll.domain.FormPaieLigneReb;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FormPaieLigneReb}.
 */
public interface FormPaieLigneRebService {
    /**
     * Save a formPaieLigneReb.
     *
     * @param formPaieLigneReb the entity to save.
     * @return the persisted entity.
     */
    FormPaieLigneReb save(FormPaieLigneReb formPaieLigneReb);

    /**
     * Partially updates a formPaieLigneReb.
     *
     * @param formPaieLigneReb the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormPaieLigneReb> partialUpdate(FormPaieLigneReb formPaieLigneReb);

    /**
     * Get all the formPaieLigneRebs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormPaieLigneReb> findAll(Pageable pageable);

    /**
     * Get the "id" formPaieLigneReb.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormPaieLigneReb> findOne(Long id);

    /**
     * Delete the "id" formPaieLigneReb.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
