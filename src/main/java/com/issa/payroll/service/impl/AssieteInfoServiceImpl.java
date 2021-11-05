package com.issa.payroll.service.impl;

import com.issa.payroll.domain.AssieteInfo;
import com.issa.payroll.repository.AssieteInfoRepository;
import com.issa.payroll.service.AssieteInfoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AssieteInfo}.
 */
@Service
@Transactional
public class AssieteInfoServiceImpl implements AssieteInfoService {

    private final Logger log = LoggerFactory.getLogger(AssieteInfoServiceImpl.class);

    private final AssieteInfoRepository assieteInfoRepository;

    public AssieteInfoServiceImpl(AssieteInfoRepository assieteInfoRepository) {
        this.assieteInfoRepository = assieteInfoRepository;
    }

    @Override
    public AssieteInfo save(AssieteInfo assieteInfo) {
        log.debug("Request to save AssieteInfo : {}", assieteInfo);
        return assieteInfoRepository.save(assieteInfo);
    }

    @Override
    public Optional<AssieteInfo> partialUpdate(AssieteInfo assieteInfo) {
        log.debug("Request to partially update AssieteInfo : {}", assieteInfo);

        return assieteInfoRepository
            .findById(assieteInfo.getId())
            .map(existingAssieteInfo -> {
                if (assieteInfo.getPriorite() != null) {
                    existingAssieteInfo.setPriorite(assieteInfo.getPriorite());
                }
                if (assieteInfo.getVal() != null) {
                    existingAssieteInfo.setVal(assieteInfo.getVal());
                }
                if (assieteInfo.getUtil() != null) {
                    existingAssieteInfo.setUtil(assieteInfo.getUtil());
                }
                if (assieteInfo.getDateSituation() != null) {
                    existingAssieteInfo.setDateSituation(assieteInfo.getDateSituation());
                }
                if (assieteInfo.getDateop() != null) {
                    existingAssieteInfo.setDateop(assieteInfo.getDateop());
                }
                if (assieteInfo.getModifiedBy() != null) {
                    existingAssieteInfo.setModifiedBy(assieteInfo.getModifiedBy());
                }
                if (assieteInfo.getCreatedBy() != null) {
                    existingAssieteInfo.setCreatedBy(assieteInfo.getCreatedBy());
                }
                if (assieteInfo.getOp() != null) {
                    existingAssieteInfo.setOp(assieteInfo.getOp());
                }
                if (assieteInfo.getIsDeleted() != null) {
                    existingAssieteInfo.setIsDeleted(assieteInfo.getIsDeleted());
                }
                if (assieteInfo.getCreatedDate() != null) {
                    existingAssieteInfo.setCreatedDate(assieteInfo.getCreatedDate());
                }
                if (assieteInfo.getModifiedDate() != null) {
                    existingAssieteInfo.setModifiedDate(assieteInfo.getModifiedDate());
                }

                return existingAssieteInfo;
            })
            .map(assieteInfoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssieteInfo> findAll(Pageable pageable) {
        log.debug("Request to get all AssieteInfos");
        return assieteInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssieteInfo> findOne(Long id) {
        log.debug("Request to get AssieteInfo : {}", id);
        return assieteInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssieteInfo : {}", id);
        assieteInfoRepository.deleteById(id);
    }
}
