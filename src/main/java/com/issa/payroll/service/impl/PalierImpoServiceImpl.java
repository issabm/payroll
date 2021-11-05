package com.issa.payroll.service.impl;

import com.issa.payroll.domain.PalierImpo;
import com.issa.payroll.repository.PalierImpoRepository;
import com.issa.payroll.service.PalierImpoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PalierImpo}.
 */
@Service
@Transactional
public class PalierImpoServiceImpl implements PalierImpoService {

    private final Logger log = LoggerFactory.getLogger(PalierImpoServiceImpl.class);

    private final PalierImpoRepository palierImpoRepository;

    public PalierImpoServiceImpl(PalierImpoRepository palierImpoRepository) {
        this.palierImpoRepository = palierImpoRepository;
    }

    @Override
    public PalierImpo save(PalierImpo palierImpo) {
        log.debug("Request to save PalierImpo : {}", palierImpo);
        return palierImpoRepository.save(palierImpo);
    }

    @Override
    public Optional<PalierImpo> partialUpdate(PalierImpo palierImpo) {
        log.debug("Request to partially update PalierImpo : {}", palierImpo);

        return palierImpoRepository
            .findById(palierImpo.getId())
            .map(existingPalierImpo -> {
                if (palierImpo.getAnnee() != null) {
                    existingPalierImpo.setAnnee(palierImpo.getAnnee());
                }
                if (palierImpo.getPayrollMin() != null) {
                    existingPalierImpo.setPayrollMin(palierImpo.getPayrollMin());
                }
                if (palierImpo.getPayrollMax() != null) {
                    existingPalierImpo.setPayrollMax(palierImpo.getPayrollMax());
                }
                if (palierImpo.getImpoValue() != null) {
                    existingPalierImpo.setImpoValue(palierImpo.getImpoValue());
                }
                if (palierImpo.getUtil() != null) {
                    existingPalierImpo.setUtil(palierImpo.getUtil());
                }
                if (palierImpo.getDateop() != null) {
                    existingPalierImpo.setDateop(palierImpo.getDateop());
                }
                if (palierImpo.getDateModif() != null) {
                    existingPalierImpo.setDateModif(palierImpo.getDateModif());
                }
                if (palierImpo.getModifiedBy() != null) {
                    existingPalierImpo.setModifiedBy(palierImpo.getModifiedBy());
                }
                if (palierImpo.getOp() != null) {
                    existingPalierImpo.setOp(palierImpo.getOp());
                }
                if (palierImpo.getIsDeleted() != null) {
                    existingPalierImpo.setIsDeleted(palierImpo.getIsDeleted());
                }

                return existingPalierImpo;
            })
            .map(palierImpoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PalierImpo> findAll(Pageable pageable) {
        log.debug("Request to get all PalierImpos");
        return palierImpoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PalierImpo> findOne(Long id) {
        log.debug("Request to get PalierImpo : {}", id);
        return palierImpoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PalierImpo : {}", id);
        palierImpoRepository.deleteById(id);
    }
}
