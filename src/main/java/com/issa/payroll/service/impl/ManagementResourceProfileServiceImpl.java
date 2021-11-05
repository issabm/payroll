package com.issa.payroll.service.impl;

import com.issa.payroll.domain.ManagementResourceProfile;
import com.issa.payroll.repository.ManagementResourceProfileRepository;
import com.issa.payroll.service.ManagementResourceProfileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ManagementResourceProfile}.
 */
@Service
@Transactional
public class ManagementResourceProfileServiceImpl implements ManagementResourceProfileService {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceProfileServiceImpl.class);

    private final ManagementResourceProfileRepository managementResourceProfileRepository;

    public ManagementResourceProfileServiceImpl(ManagementResourceProfileRepository managementResourceProfileRepository) {
        this.managementResourceProfileRepository = managementResourceProfileRepository;
    }

    @Override
    public ManagementResourceProfile save(ManagementResourceProfile managementResourceProfile) {
        log.debug("Request to save ManagementResourceProfile : {}", managementResourceProfile);
        return managementResourceProfileRepository.save(managementResourceProfile);
    }

    @Override
    public Optional<ManagementResourceProfile> partialUpdate(ManagementResourceProfile managementResourceProfile) {
        log.debug("Request to partially update ManagementResourceProfile : {}", managementResourceProfile);

        return managementResourceProfileRepository
            .findById(managementResourceProfile.getId())
            .map(existingManagementResourceProfile -> {
                if (managementResourceProfile.getProfile() != null) {
                    existingManagementResourceProfile.setProfile(managementResourceProfile.getProfile());
                }
                if (managementResourceProfile.getDateop() != null) {
                    existingManagementResourceProfile.setDateop(managementResourceProfile.getDateop());
                }
                if (managementResourceProfile.getModifiedBy() != null) {
                    existingManagementResourceProfile.setModifiedBy(managementResourceProfile.getModifiedBy());
                }
                if (managementResourceProfile.getCreatedBy() != null) {
                    existingManagementResourceProfile.setCreatedBy(managementResourceProfile.getCreatedBy());
                }
                if (managementResourceProfile.getOp() != null) {
                    existingManagementResourceProfile.setOp(managementResourceProfile.getOp());
                }
                if (managementResourceProfile.getUtil() != null) {
                    existingManagementResourceProfile.setUtil(managementResourceProfile.getUtil());
                }
                if (managementResourceProfile.getIsDeleted() != null) {
                    existingManagementResourceProfile.setIsDeleted(managementResourceProfile.getIsDeleted());
                }
                if (managementResourceProfile.getCreatedDate() != null) {
                    existingManagementResourceProfile.setCreatedDate(managementResourceProfile.getCreatedDate());
                }
                if (managementResourceProfile.getModifiedDate() != null) {
                    existingManagementResourceProfile.setModifiedDate(managementResourceProfile.getModifiedDate());
                }

                return existingManagementResourceProfile;
            })
            .map(managementResourceProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagementResourceProfile> findAll(Pageable pageable) {
        log.debug("Request to get all ManagementResourceProfiles");
        return managementResourceProfileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManagementResourceProfile> findOne(Long id) {
        log.debug("Request to get ManagementResourceProfile : {}", id);
        return managementResourceProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManagementResourceProfile : {}", id);
        managementResourceProfileRepository.deleteById(id);
    }
}
