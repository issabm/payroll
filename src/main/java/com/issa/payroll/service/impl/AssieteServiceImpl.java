package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Assiete;
import com.issa.payroll.repository.AssieteRepository;
import com.issa.payroll.service.AssieteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Assiete}.
 */
@Service
@Transactional
public class AssieteServiceImpl implements AssieteService {

    private final Logger log = LoggerFactory.getLogger(AssieteServiceImpl.class);

    private final AssieteRepository assieteRepository;

    public AssieteServiceImpl(AssieteRepository assieteRepository) {
        this.assieteRepository = assieteRepository;
    }

    @Override
    public Assiete save(Assiete assiete) {
        log.debug("Request to save Assiete : {}", assiete);
        return assieteRepository.save(assiete);
    }

    @Override
    public Optional<Assiete> partialUpdate(Assiete assiete) {
        log.debug("Request to partially update Assiete : {}", assiete);

        return assieteRepository
            .findById(assiete.getId())
            .map(existingAssiete -> {
                if (assiete.getPriorite() != null) {
                    existingAssiete.setPriorite(assiete.getPriorite());
                }
                if (assiete.getCode() != null) {
                    existingAssiete.setCode(assiete.getCode());
                }
                if (assiete.getLib() != null) {
                    existingAssiete.setLib(assiete.getLib());
                }
                if (assiete.getDateSituation() != null) {
                    existingAssiete.setDateSituation(assiete.getDateSituation());
                }
                if (assiete.getDateop() != null) {
                    existingAssiete.setDateop(assiete.getDateop());
                }
                if (assiete.getModifiedBy() != null) {
                    existingAssiete.setModifiedBy(assiete.getModifiedBy());
                }
                if (assiete.getCreatedBy() != null) {
                    existingAssiete.setCreatedBy(assiete.getCreatedBy());
                }
                if (assiete.getUtil() != null) {
                    existingAssiete.setUtil(assiete.getUtil());
                }
                if (assiete.getOp() != null) {
                    existingAssiete.setOp(assiete.getOp());
                }
                if (assiete.getIsDeleted() != null) {
                    existingAssiete.setIsDeleted(assiete.getIsDeleted());
                }
                if (assiete.getCreatedDate() != null) {
                    existingAssiete.setCreatedDate(assiete.getCreatedDate());
                }
                if (assiete.getModifiedDate() != null) {
                    existingAssiete.setModifiedDate(assiete.getModifiedDate());
                }

                return existingAssiete;
            })
            .map(assieteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Assiete> findAll(Pageable pageable) {
        log.debug("Request to get all Assietes");
        return assieteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Assiete> findOne(Long id) {
        log.debug("Request to get Assiete : {}", id);
        return assieteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assiete : {}", id);
        assieteRepository.deleteById(id);
    }
}
