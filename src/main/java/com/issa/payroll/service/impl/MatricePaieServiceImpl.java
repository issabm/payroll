package com.issa.payroll.service.impl;

import com.issa.payroll.domain.MatricePaie;
import com.issa.payroll.repository.MatricePaieRepository;
import com.issa.payroll.service.MatricePaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MatricePaie}.
 */
@Service
@Transactional
public class MatricePaieServiceImpl implements MatricePaieService {

    private final Logger log = LoggerFactory.getLogger(MatricePaieServiceImpl.class);

    private final MatricePaieRepository matricePaieRepository;

    public MatricePaieServiceImpl(MatricePaieRepository matricePaieRepository) {
        this.matricePaieRepository = matricePaieRepository;
    }

    @Override
    public MatricePaie save(MatricePaie matricePaie) {
        log.debug("Request to save MatricePaie : {}", matricePaie);
        return matricePaieRepository.save(matricePaie);
    }

    @Override
    public Optional<MatricePaie> partialUpdate(MatricePaie matricePaie) {
        log.debug("Request to partially update MatricePaie : {}", matricePaie);

        return matricePaieRepository
            .findById(matricePaie.getId())
            .map(existingMatricePaie -> {
                if (matricePaie.getCode() != null) {
                    existingMatricePaie.setCode(matricePaie.getCode());
                }
                if (matricePaie.getLibAr() != null) {
                    existingMatricePaie.setLibAr(matricePaie.getLibAr());
                }
                if (matricePaie.getLibEn() != null) {
                    existingMatricePaie.setLibEn(matricePaie.getLibEn());
                }
                if (matricePaie.getIsDisplay() != null) {
                    existingMatricePaie.setIsDisplay(matricePaie.getIsDisplay());
                }
                if (matricePaie.getAnneeDebut() != null) {
                    existingMatricePaie.setAnneeDebut(matricePaie.getAnneeDebut());
                }
                if (matricePaie.getMoisDebut() != null) {
                    existingMatricePaie.setMoisDebut(matricePaie.getMoisDebut());
                }
                if (matricePaie.getAnneeFin() != null) {
                    existingMatricePaie.setAnneeFin(matricePaie.getAnneeFin());
                }
                if (matricePaie.getMoisFin() != null) {
                    existingMatricePaie.setMoisFin(matricePaie.getMoisFin());
                }
                if (matricePaie.getUtil() != null) {
                    existingMatricePaie.setUtil(matricePaie.getUtil());
                }
                if (matricePaie.getDateop() != null) {
                    existingMatricePaie.setDateop(matricePaie.getDateop());
                }
                if (matricePaie.getModifiedBy() != null) {
                    existingMatricePaie.setModifiedBy(matricePaie.getModifiedBy());
                }
                if (matricePaie.getOp() != null) {
                    existingMatricePaie.setOp(matricePaie.getOp());
                }
                if (matricePaie.getIsDeleted() != null) {
                    existingMatricePaie.setIsDeleted(matricePaie.getIsDeleted());
                }
                if (matricePaie.getCreatedDate() != null) {
                    existingMatricePaie.setCreatedDate(matricePaie.getCreatedDate());
                }
                if (matricePaie.getModifiedDate() != null) {
                    existingMatricePaie.setModifiedDate(matricePaie.getModifiedDate());
                }

                return existingMatricePaie;
            })
            .map(matricePaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatricePaie> findAll(Pageable pageable) {
        log.debug("Request to get all MatricePaies");
        return matricePaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatricePaie> findOne(Long id) {
        log.debug("Request to get MatricePaie : {}", id);
        return matricePaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatricePaie : {}", id);
        matricePaieRepository.deleteById(id);
    }
}
