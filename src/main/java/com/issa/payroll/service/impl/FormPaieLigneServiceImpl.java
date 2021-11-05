package com.issa.payroll.service.impl;

import com.issa.payroll.domain.FormPaieLigne;
import com.issa.payroll.repository.FormPaieLigneRepository;
import com.issa.payroll.service.FormPaieLigneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormPaieLigne}.
 */
@Service
@Transactional
public class FormPaieLigneServiceImpl implements FormPaieLigneService {

    private final Logger log = LoggerFactory.getLogger(FormPaieLigneServiceImpl.class);

    private final FormPaieLigneRepository formPaieLigneRepository;

    public FormPaieLigneServiceImpl(FormPaieLigneRepository formPaieLigneRepository) {
        this.formPaieLigneRepository = formPaieLigneRepository;
    }

    @Override
    public FormPaieLigne save(FormPaieLigne formPaieLigne) {
        log.debug("Request to save FormPaieLigne : {}", formPaieLigne);
        return formPaieLigneRepository.save(formPaieLigne);
    }

    @Override
    public Optional<FormPaieLigne> partialUpdate(FormPaieLigne formPaieLigne) {
        log.debug("Request to partially update FormPaieLigne : {}", formPaieLigne);

        return formPaieLigneRepository
            .findById(formPaieLigne.getId())
            .map(existingFormPaieLigne -> {
                if (formPaieLigne.getPriorite() != null) {
                    existingFormPaieLigne.setPriorite(formPaieLigne.getPriorite());
                }
                if (formPaieLigne.getCode() != null) {
                    existingFormPaieLigne.setCode(formPaieLigne.getCode());
                }
                if (formPaieLigne.getLibEn() != null) {
                    existingFormPaieLigne.setLibEn(formPaieLigne.getLibEn());
                }
                if (formPaieLigne.getLibAr() != null) {
                    existingFormPaieLigne.setLibAr(formPaieLigne.getLibAr());
                }
                if (formPaieLigne.getDateop() != null) {
                    existingFormPaieLigne.setDateop(formPaieLigne.getDateop());
                }
                if (formPaieLigne.getModifiedBy() != null) {
                    existingFormPaieLigne.setModifiedBy(formPaieLigne.getModifiedBy());
                }
                if (formPaieLigne.getCreatedBy() != null) {
                    existingFormPaieLigne.setCreatedBy(formPaieLigne.getCreatedBy());
                }
                if (formPaieLigne.getOp() != null) {
                    existingFormPaieLigne.setOp(formPaieLigne.getOp());
                }
                if (formPaieLigne.getUtil() != null) {
                    existingFormPaieLigne.setUtil(formPaieLigne.getUtil());
                }
                if (formPaieLigne.getIsDeleted() != null) {
                    existingFormPaieLigne.setIsDeleted(formPaieLigne.getIsDeleted());
                }
                if (formPaieLigne.getCreatedDate() != null) {
                    existingFormPaieLigne.setCreatedDate(formPaieLigne.getCreatedDate());
                }
                if (formPaieLigne.getModifiedDate() != null) {
                    existingFormPaieLigne.setModifiedDate(formPaieLigne.getModifiedDate());
                }

                return existingFormPaieLigne;
            })
            .map(formPaieLigneRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormPaieLigne> findAll(Pageable pageable) {
        log.debug("Request to get all FormPaieLignes");
        return formPaieLigneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormPaieLigne> findOne(Long id) {
        log.debug("Request to get FormPaieLigne : {}", id);
        return formPaieLigneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormPaieLigne : {}", id);
        formPaieLigneRepository.deleteById(id);
    }
}
