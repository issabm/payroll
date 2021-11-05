package com.issa.payroll.service;

import com.issa.payroll.domain.ManagementResourceProfile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ManagementResourceProfile}.
 */
public interface ManagementResourceProfileService {
    /**
     * Save a managementResourceProfile.
     *
     * @param managementResourceProfile the entity to save.
     * @return the persisted entity.
     */
    ManagementResourceProfile save(ManagementResourceProfile managementResourceProfile);

    /**
     * Partially updates a managementResourceProfile.
     *
     * @param managementResourceProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagementResourceProfile> partialUpdate(ManagementResourceProfile managementResourceProfile);

    /**
     * Get all the managementResourceProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManagementResourceProfile> findAll(Pageable pageable);

    /**
     * Get the "id" managementResourceProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagementResourceProfile> findOne(Long id);

    /**
     * Delete the "id" managementResourceProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
