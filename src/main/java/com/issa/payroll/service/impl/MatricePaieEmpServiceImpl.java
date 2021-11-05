package com.issa.payroll.service.impl;

import com.issa.payroll.domain.MatricePaieEmp;
import com.issa.payroll.repository.MatricePaieEmpRepository;
import com.issa.payroll.service.MatricePaieEmpService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MatricePaieEmp}.
 */
@Service
@Transactional
public class MatricePaieEmpServiceImpl implements MatricePaieEmpService {

    private final Logger log = LoggerFactory.getLogger(MatricePaieEmpServiceImpl.class);

    private final MatricePaieEmpRepository matricePaieEmpRepository;

    public MatricePaieEmpServiceImpl(MatricePaieEmpRepository matricePaieEmpRepository) {
        this.matricePaieEmpRepository = matricePaieEmpRepository;
    }

    @Override
    public MatricePaieEmp save(MatricePaieEmp matricePaieEmp) {
        log.debug("Request to save MatricePaieEmp : {}", matricePaieEmp);
        return matricePaieEmpRepository.save(matricePaieEmp);
    }

    @Override
    public Optional<MatricePaieEmp> partialUpdate(MatricePaieEmp matricePaieEmp) {
        log.debug("Request to partially update MatricePaieEmp : {}", matricePaieEmp);

        return matricePaieEmpRepository
            .findById(matricePaieEmp.getId())
            .map(existingMatricePaieEmp -> {
                if (matricePaieEmp.getUtil() != null) {
                    existingMatricePaieEmp.setUtil(matricePaieEmp.getUtil());
                }
                if (matricePaieEmp.getDateop() != null) {
                    existingMatricePaieEmp.setDateop(matricePaieEmp.getDateop());
                }
                if (matricePaieEmp.getModifiedBy() != null) {
                    existingMatricePaieEmp.setModifiedBy(matricePaieEmp.getModifiedBy());
                }
                if (matricePaieEmp.getOp() != null) {
                    existingMatricePaieEmp.setOp(matricePaieEmp.getOp());
                }
                if (matricePaieEmp.getIsDeleted() != null) {
                    existingMatricePaieEmp.setIsDeleted(matricePaieEmp.getIsDeleted());
                }
                if (matricePaieEmp.getCreatedDate() != null) {
                    existingMatricePaieEmp.setCreatedDate(matricePaieEmp.getCreatedDate());
                }
                if (matricePaieEmp.getModifiedDate() != null) {
                    existingMatricePaieEmp.setModifiedDate(matricePaieEmp.getModifiedDate());
                }

                return existingMatricePaieEmp;
            })
            .map(matricePaieEmpRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatricePaieEmp> findAll(Pageable pageable) {
        log.debug("Request to get all MatricePaieEmps");
        return matricePaieEmpRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatricePaieEmp> findOne(Long id) {
        log.debug("Request to get MatricePaieEmp : {}", id);
        return matricePaieEmpRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatricePaieEmp : {}", id);
        matricePaieEmpRepository.deleteById(id);
    }
}
