package com.issa.payroll.service.impl;

import com.issa.payroll.domain.FormPaieLigneReb;
import com.issa.payroll.repository.FormPaieLigneRebRepository;
import com.issa.payroll.service.FormPaieLigneRebService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormPaieLigneReb}.
 */
@Service
@Transactional
public class FormPaieLigneRebServiceImpl implements FormPaieLigneRebService {

    private final Logger log = LoggerFactory.getLogger(FormPaieLigneRebServiceImpl.class);

    private final FormPaieLigneRebRepository formPaieLigneRebRepository;

    public FormPaieLigneRebServiceImpl(FormPaieLigneRebRepository formPaieLigneRebRepository) {
        this.formPaieLigneRebRepository = formPaieLigneRebRepository;
    }

    @Override
    public FormPaieLigneReb save(FormPaieLigneReb formPaieLigneReb) {
        log.debug("Request to save FormPaieLigneReb : {}", formPaieLigneReb);
        return formPaieLigneRebRepository.save(formPaieLigneReb);
    }

    @Override
    public Optional<FormPaieLigneReb> partialUpdate(FormPaieLigneReb formPaieLigneReb) {
        log.debug("Request to partially update FormPaieLigneReb : {}", formPaieLigneReb);

        return formPaieLigneRebRepository
            .findById(formPaieLigneReb.getId())
            .map(existingFormPaieLigneReb -> {
                if (formPaieLigneReb.getPriorite() != null) {
                    existingFormPaieLigneReb.setPriorite(formPaieLigneReb.getPriorite());
                }
                if (formPaieLigneReb.getCode() != null) {
                    existingFormPaieLigneReb.setCode(formPaieLigneReb.getCode());
                }
                if (formPaieLigneReb.getLibEn() != null) {
                    existingFormPaieLigneReb.setLibEn(formPaieLigneReb.getLibEn());
                }
                if (formPaieLigneReb.getLibAr() != null) {
                    existingFormPaieLigneReb.setLibAr(formPaieLigneReb.getLibAr());
                }
                if (formPaieLigneReb.getValOrigin() != null) {
                    existingFormPaieLigneReb.setValOrigin(formPaieLigneReb.getValOrigin());
                }
                if (formPaieLigneReb.getValCalcul() != null) {
                    existingFormPaieLigneReb.setValCalcul(formPaieLigneReb.getValCalcul());
                }
                if (formPaieLigneReb.getDateop() != null) {
                    existingFormPaieLigneReb.setDateop(formPaieLigneReb.getDateop());
                }
                if (formPaieLigneReb.getModifiedBy() != null) {
                    existingFormPaieLigneReb.setModifiedBy(formPaieLigneReb.getModifiedBy());
                }
                if (formPaieLigneReb.getCreatedBy() != null) {
                    existingFormPaieLigneReb.setCreatedBy(formPaieLigneReb.getCreatedBy());
                }
                if (formPaieLigneReb.getOp() != null) {
                    existingFormPaieLigneReb.setOp(formPaieLigneReb.getOp());
                }
                if (formPaieLigneReb.getUtil() != null) {
                    existingFormPaieLigneReb.setUtil(formPaieLigneReb.getUtil());
                }
                if (formPaieLigneReb.getIsDeleted() != null) {
                    existingFormPaieLigneReb.setIsDeleted(formPaieLigneReb.getIsDeleted());
                }
                if (formPaieLigneReb.getCreatedDate() != null) {
                    existingFormPaieLigneReb.setCreatedDate(formPaieLigneReb.getCreatedDate());
                }
                if (formPaieLigneReb.getModifiedDate() != null) {
                    existingFormPaieLigneReb.setModifiedDate(formPaieLigneReb.getModifiedDate());
                }

                return existingFormPaieLigneReb;
            })
            .map(formPaieLigneRebRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormPaieLigneReb> findAll(Pageable pageable) {
        log.debug("Request to get all FormPaieLigneRebs");
        return formPaieLigneRebRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormPaieLigneReb> findOne(Long id) {
        log.debug("Request to get FormPaieLigneReb : {}", id);
        return formPaieLigneRebRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormPaieLigneReb : {}", id);
        formPaieLigneRebRepository.deleteById(id);
    }
}
