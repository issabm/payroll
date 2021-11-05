package com.issa.payroll.service.impl;

import com.issa.payroll.domain.FormPaie;
import com.issa.payroll.repository.FormPaieRepository;
import com.issa.payroll.service.FormPaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormPaie}.
 */
@Service
@Transactional
public class FormPaieServiceImpl implements FormPaieService {

    private final Logger log = LoggerFactory.getLogger(FormPaieServiceImpl.class);

    private final FormPaieRepository formPaieRepository;

    public FormPaieServiceImpl(FormPaieRepository formPaieRepository) {
        this.formPaieRepository = formPaieRepository;
    }

    @Override
    public FormPaie save(FormPaie formPaie) {
        log.debug("Request to save FormPaie : {}", formPaie);
        return formPaieRepository.save(formPaie);
    }

    @Override
    public Optional<FormPaie> partialUpdate(FormPaie formPaie) {
        log.debug("Request to partially update FormPaie : {}", formPaie);

        return formPaieRepository
            .findById(formPaie.getId())
            .map(existingFormPaie -> {
                if (formPaie.getCode() != null) {
                    existingFormPaie.setCode(formPaie.getCode());
                }
                if (formPaie.getLibEn() != null) {
                    existingFormPaie.setLibEn(formPaie.getLibEn());
                }
                if (formPaie.getLibAr() != null) {
                    existingFormPaie.setLibAr(formPaie.getLibAr());
                }
                if (formPaie.getAnneDebut() != null) {
                    existingFormPaie.setAnneDebut(formPaie.getAnneDebut());
                }
                if (formPaie.getAnneeFin() != null) {
                    existingFormPaie.setAnneeFin(formPaie.getAnneeFin());
                }
                if (formPaie.getMoisDebut() != null) {
                    existingFormPaie.setMoisDebut(formPaie.getMoisDebut());
                }
                if (formPaie.getMoisFin() != null) {
                    existingFormPaie.setMoisFin(formPaie.getMoisFin());
                }
                if (formPaie.getDateop() != null) {
                    existingFormPaie.setDateop(formPaie.getDateop());
                }
                if (formPaie.getModifiedBy() != null) {
                    existingFormPaie.setModifiedBy(formPaie.getModifiedBy());
                }
                if (formPaie.getCreatedBy() != null) {
                    existingFormPaie.setCreatedBy(formPaie.getCreatedBy());
                }
                if (formPaie.getUtil() != null) {
                    existingFormPaie.setUtil(formPaie.getUtil());
                }
                if (formPaie.getOp() != null) {
                    existingFormPaie.setOp(formPaie.getOp());
                }
                if (formPaie.getIsDeleted() != null) {
                    existingFormPaie.setIsDeleted(formPaie.getIsDeleted());
                }
                if (formPaie.getCreatedDate() != null) {
                    existingFormPaie.setCreatedDate(formPaie.getCreatedDate());
                }
                if (formPaie.getModifiedDate() != null) {
                    existingFormPaie.setModifiedDate(formPaie.getModifiedDate());
                }

                return existingFormPaie;
            })
            .map(formPaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormPaie> findAll(Pageable pageable) {
        log.debug("Request to get all FormPaies");
        return formPaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormPaie> findOne(Long id) {
        log.debug("Request to get FormPaie : {}", id);
        return formPaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormPaie : {}", id);
        formPaieRepository.deleteById(id);
    }
}
