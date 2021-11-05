package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureAbsenceRebrique;
import com.issa.payroll.repository.NatureAbsenceRebriqueRepository;
import com.issa.payroll.service.NatureAbsenceRebriqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureAbsenceRebrique}.
 */
@Service
@Transactional
public class NatureAbsenceRebriqueServiceImpl implements NatureAbsenceRebriqueService {

    private final Logger log = LoggerFactory.getLogger(NatureAbsenceRebriqueServiceImpl.class);

    private final NatureAbsenceRebriqueRepository natureAbsenceRebriqueRepository;

    public NatureAbsenceRebriqueServiceImpl(NatureAbsenceRebriqueRepository natureAbsenceRebriqueRepository) {
        this.natureAbsenceRebriqueRepository = natureAbsenceRebriqueRepository;
    }

    @Override
    public NatureAbsenceRebrique save(NatureAbsenceRebrique natureAbsenceRebrique) {
        log.debug("Request to save NatureAbsenceRebrique : {}", natureAbsenceRebrique);
        return natureAbsenceRebriqueRepository.save(natureAbsenceRebrique);
    }

    @Override
    public Optional<NatureAbsenceRebrique> partialUpdate(NatureAbsenceRebrique natureAbsenceRebrique) {
        log.debug("Request to partially update NatureAbsenceRebrique : {}", natureAbsenceRebrique);

        return natureAbsenceRebriqueRepository
            .findById(natureAbsenceRebrique.getId())
            .map(existingNatureAbsenceRebrique -> {
                if (natureAbsenceRebrique.getCode() != null) {
                    existingNatureAbsenceRebrique.setCode(natureAbsenceRebrique.getCode());
                }
                if (natureAbsenceRebrique.getLibAr() != null) {
                    existingNatureAbsenceRebrique.setLibAr(natureAbsenceRebrique.getLibAr());
                }
                if (natureAbsenceRebrique.getLibEn() != null) {
                    existingNatureAbsenceRebrique.setLibEn(natureAbsenceRebrique.getLibEn());
                }
                if (natureAbsenceRebrique.getValueTaken() != null) {
                    existingNatureAbsenceRebrique.setValueTaken(natureAbsenceRebrique.getValueTaken());
                }
                if (natureAbsenceRebrique.getUtil() != null) {
                    existingNatureAbsenceRebrique.setUtil(natureAbsenceRebrique.getUtil());
                }
                if (natureAbsenceRebrique.getDateop() != null) {
                    existingNatureAbsenceRebrique.setDateop(natureAbsenceRebrique.getDateop());
                }
                if (natureAbsenceRebrique.getModifiedBy() != null) {
                    existingNatureAbsenceRebrique.setModifiedBy(natureAbsenceRebrique.getModifiedBy());
                }
                if (natureAbsenceRebrique.getOp() != null) {
                    existingNatureAbsenceRebrique.setOp(natureAbsenceRebrique.getOp());
                }
                if (natureAbsenceRebrique.getIsDeleted() != null) {
                    existingNatureAbsenceRebrique.setIsDeleted(natureAbsenceRebrique.getIsDeleted());
                }
                if (natureAbsenceRebrique.getCreatedDate() != null) {
                    existingNatureAbsenceRebrique.setCreatedDate(natureAbsenceRebrique.getCreatedDate());
                }
                if (natureAbsenceRebrique.getModifiedDate() != null) {
                    existingNatureAbsenceRebrique.setModifiedDate(natureAbsenceRebrique.getModifiedDate());
                }

                return existingNatureAbsenceRebrique;
            })
            .map(natureAbsenceRebriqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureAbsenceRebrique> findAll(Pageable pageable) {
        log.debug("Request to get all NatureAbsenceRebriques");
        return natureAbsenceRebriqueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureAbsenceRebrique> findOne(Long id) {
        log.debug("Request to get NatureAbsenceRebrique : {}", id);
        return natureAbsenceRebriqueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureAbsenceRebrique : {}", id);
        natureAbsenceRebriqueRepository.deleteById(id);
    }
}
