package com.issa.payroll.service;

import com.issa.payroll.domain.Assiete;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Assiete}.
 */
public interface AssieteService {
    /**
     * Save a assiete.
     *
     * @param assiete the entity to save.
     * @return the persisted entity.
     */
    Assiete save(Assiete assiete);

    /**
     * Partially updates a assiete.
     *
     * @param assiete the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Assiete> partialUpdate(Assiete assiete);

    /**
     * Get all the assietes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Assiete> findAll(Pageable pageable);

    /**
     * Get the "id" assiete.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Assiete> findOne(Long id);

    /**
     * Delete the "id" assiete.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
