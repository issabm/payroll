package com.issa.payroll.service.impl;

import com.issa.payroll.domain.OperatorForm;
import com.issa.payroll.repository.OperatorFormRepository;
import com.issa.payroll.service.OperatorFormService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperatorForm}.
 */
@Service
@Transactional
public class OperatorFormServiceImpl implements OperatorFormService {

    private final Logger log = LoggerFactory.getLogger(OperatorFormServiceImpl.class);

    private final OperatorFormRepository operatorFormRepository;

    public OperatorFormServiceImpl(OperatorFormRepository operatorFormRepository) {
        this.operatorFormRepository = operatorFormRepository;
    }

    @Override
    public OperatorForm save(OperatorForm operatorForm) {
        log.debug("Request to save OperatorForm : {}", operatorForm);
        return operatorFormRepository.save(operatorForm);
    }

    @Override
    public Optional<OperatorForm> partialUpdate(OperatorForm operatorForm) {
        log.debug("Request to partially update OperatorForm : {}", operatorForm);

        return operatorFormRepository
            .findById(operatorForm.getId())
            .map(existingOperatorForm -> {
                if (operatorForm.getCode() != null) {
                    existingOperatorForm.setCode(operatorForm.getCode());
                }
                if (operatorForm.getLibEn() != null) {
                    existingOperatorForm.setLibEn(operatorForm.getLibEn());
                }
                if (operatorForm.getLibAr() != null) {
                    existingOperatorForm.setLibAr(operatorForm.getLibAr());
                }
                if (operatorForm.getDateop() != null) {
                    existingOperatorForm.setDateop(operatorForm.getDateop());
                }
                if (operatorForm.getModifiedBy() != null) {
                    existingOperatorForm.setModifiedBy(operatorForm.getModifiedBy());
                }
                if (operatorForm.getCreatedBy() != null) {
                    existingOperatorForm.setCreatedBy(operatorForm.getCreatedBy());
                }
                if (operatorForm.getOp() != null) {
                    existingOperatorForm.setOp(operatorForm.getOp());
                }
                if (operatorForm.getUtil() != null) {
                    existingOperatorForm.setUtil(operatorForm.getUtil());
                }
                if (operatorForm.getIsDeleted() != null) {
                    existingOperatorForm.setIsDeleted(operatorForm.getIsDeleted());
                }
                if (operatorForm.getCreatedDate() != null) {
                    existingOperatorForm.setCreatedDate(operatorForm.getCreatedDate());
                }
                if (operatorForm.getModifiedDate() != null) {
                    existingOperatorForm.setModifiedDate(operatorForm.getModifiedDate());
                }

                return existingOperatorForm;
            })
            .map(operatorFormRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperatorForm> findAll(Pageable pageable) {
        log.debug("Request to get all OperatorForms");
        return operatorFormRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperatorForm> findOne(Long id) {
        log.debug("Request to get OperatorForm : {}", id);
        return operatorFormRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperatorForm : {}", id);
        operatorFormRepository.deleteById(id);
    }
}
