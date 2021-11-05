package com.issa.payroll.service.impl;

import com.issa.payroll.domain.RebriqueContrat;
import com.issa.payroll.repository.RebriqueContratRepository;
import com.issa.payroll.service.RebriqueContratService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RebriqueContrat}.
 */
@Service
@Transactional
public class RebriqueContratServiceImpl implements RebriqueContratService {

    private final Logger log = LoggerFactory.getLogger(RebriqueContratServiceImpl.class);

    private final RebriqueContratRepository rebriqueContratRepository;

    public RebriqueContratServiceImpl(RebriqueContratRepository rebriqueContratRepository) {
        this.rebriqueContratRepository = rebriqueContratRepository;
    }

    @Override
    public RebriqueContrat save(RebriqueContrat rebriqueContrat) {
        log.debug("Request to save RebriqueContrat : {}", rebriqueContrat);
        return rebriqueContratRepository.save(rebriqueContrat);
    }

    @Override
    public Optional<RebriqueContrat> partialUpdate(RebriqueContrat rebriqueContrat) {
        log.debug("Request to partially update RebriqueContrat : {}", rebriqueContrat);

        return rebriqueContratRepository
            .findById(rebriqueContrat.getId())
            .map(existingRebriqueContrat -> {
                if (rebriqueContrat.getUtil() != null) {
                    existingRebriqueContrat.setUtil(rebriqueContrat.getUtil());
                }
                if (rebriqueContrat.getDateSituation() != null) {
                    existingRebriqueContrat.setDateSituation(rebriqueContrat.getDateSituation());
                }
                if (rebriqueContrat.getDateop() != null) {
                    existingRebriqueContrat.setDateop(rebriqueContrat.getDateop());
                }
                if (rebriqueContrat.getModifiedBy() != null) {
                    existingRebriqueContrat.setModifiedBy(rebriqueContrat.getModifiedBy());
                }
                if (rebriqueContrat.getCreatedBy() != null) {
                    existingRebriqueContrat.setCreatedBy(rebriqueContrat.getCreatedBy());
                }
                if (rebriqueContrat.getOp() != null) {
                    existingRebriqueContrat.setOp(rebriqueContrat.getOp());
                }
                if (rebriqueContrat.getIsDeleted() != null) {
                    existingRebriqueContrat.setIsDeleted(rebriqueContrat.getIsDeleted());
                }
                if (rebriqueContrat.getCreatedDate() != null) {
                    existingRebriqueContrat.setCreatedDate(rebriqueContrat.getCreatedDate());
                }
                if (rebriqueContrat.getModifiedDate() != null) {
                    existingRebriqueContrat.setModifiedDate(rebriqueContrat.getModifiedDate());
                }

                return existingRebriqueContrat;
            })
            .map(rebriqueContratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RebriqueContrat> findAll(Pageable pageable) {
        log.debug("Request to get all RebriqueContrats");
        return rebriqueContratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RebriqueContrat> findOne(Long id) {
        log.debug("Request to get RebriqueContrat : {}", id);
        return rebriqueContratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RebriqueContrat : {}", id);
        rebriqueContratRepository.deleteById(id);
    }
}
