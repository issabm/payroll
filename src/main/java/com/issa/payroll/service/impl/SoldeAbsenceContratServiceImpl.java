package com.issa.payroll.service.impl;

import com.issa.payroll.domain.SoldeAbsenceContrat;
import com.issa.payroll.repository.SoldeAbsenceContratRepository;
import com.issa.payroll.service.SoldeAbsenceContratService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldeAbsenceContrat}.
 */
@Service
@Transactional
public class SoldeAbsenceContratServiceImpl implements SoldeAbsenceContratService {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsenceContratServiceImpl.class);

    private final SoldeAbsenceContratRepository soldeAbsenceContratRepository;

    public SoldeAbsenceContratServiceImpl(SoldeAbsenceContratRepository soldeAbsenceContratRepository) {
        this.soldeAbsenceContratRepository = soldeAbsenceContratRepository;
    }

    @Override
    public SoldeAbsenceContrat save(SoldeAbsenceContrat soldeAbsenceContrat) {
        log.debug("Request to save SoldeAbsenceContrat : {}", soldeAbsenceContrat);
        return soldeAbsenceContratRepository.save(soldeAbsenceContrat);
    }

    @Override
    public Optional<SoldeAbsenceContrat> partialUpdate(SoldeAbsenceContrat soldeAbsenceContrat) {
        log.debug("Request to partially update SoldeAbsenceContrat : {}", soldeAbsenceContrat);

        return soldeAbsenceContratRepository
            .findById(soldeAbsenceContrat.getId())
            .map(existingSoldeAbsenceContrat -> {
                if (soldeAbsenceContrat.getAnnee() != null) {
                    existingSoldeAbsenceContrat.setAnnee(soldeAbsenceContrat.getAnnee());
                }
                if (soldeAbsenceContrat.getFullPayRight() != null) {
                    existingSoldeAbsenceContrat.setFullPayRight(soldeAbsenceContrat.getFullPayRight());
                }
                if (soldeAbsenceContrat.getHalfPayRight() != null) {
                    existingSoldeAbsenceContrat.setHalfPayRight(soldeAbsenceContrat.getHalfPayRight());
                }
                if (soldeAbsenceContrat.getUtil() != null) {
                    existingSoldeAbsenceContrat.setUtil(soldeAbsenceContrat.getUtil());
                }
                if (soldeAbsenceContrat.getDateop() != null) {
                    existingSoldeAbsenceContrat.setDateop(soldeAbsenceContrat.getDateop());
                }
                if (soldeAbsenceContrat.getModifiedBy() != null) {
                    existingSoldeAbsenceContrat.setModifiedBy(soldeAbsenceContrat.getModifiedBy());
                }
                if (soldeAbsenceContrat.getOp() != null) {
                    existingSoldeAbsenceContrat.setOp(soldeAbsenceContrat.getOp());
                }
                if (soldeAbsenceContrat.getIsDeleted() != null) {
                    existingSoldeAbsenceContrat.setIsDeleted(soldeAbsenceContrat.getIsDeleted());
                }
                if (soldeAbsenceContrat.getCreatedDate() != null) {
                    existingSoldeAbsenceContrat.setCreatedDate(soldeAbsenceContrat.getCreatedDate());
                }
                if (soldeAbsenceContrat.getModifiedDate() != null) {
                    existingSoldeAbsenceContrat.setModifiedDate(soldeAbsenceContrat.getModifiedDate());
                }

                return existingSoldeAbsenceContrat;
            })
            .map(soldeAbsenceContratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoldeAbsenceContrat> findAll(Pageable pageable) {
        log.debug("Request to get all SoldeAbsenceContrats");
        return soldeAbsenceContratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldeAbsenceContrat> findOne(Long id) {
        log.debug("Request to get SoldeAbsenceContrat : {}", id);
        return soldeAbsenceContratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldeAbsenceContrat : {}", id);
        soldeAbsenceContratRepository.deleteById(id);
    }
}
