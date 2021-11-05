package com.issa.payroll.web.rest;

import com.issa.payroll.domain.AssieteInfo;
import com.issa.payroll.repository.AssieteInfoRepository;
import com.issa.payroll.service.AssieteInfoService;
import com.issa.payroll.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.issa.payroll.domain.AssieteInfo}.
 */
@RestController
@RequestMapping("/api")
public class AssieteInfoResource {

    private final Logger log = LoggerFactory.getLogger(AssieteInfoResource.class);

    private static final String ENTITY_NAME = "assieteInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssieteInfoService assieteInfoService;

    private final AssieteInfoRepository assieteInfoRepository;

    public AssieteInfoResource(AssieteInfoService assieteInfoService, AssieteInfoRepository assieteInfoRepository) {
        this.assieteInfoService = assieteInfoService;
        this.assieteInfoRepository = assieteInfoRepository;
    }

    /**
     * {@code POST  /assiete-infos} : Create a new assieteInfo.
     *
     * @param assieteInfo the assieteInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assieteInfo, or with status {@code 400 (Bad Request)} if the assieteInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assiete-infos")
    public ResponseEntity<AssieteInfo> createAssieteInfo(@RequestBody AssieteInfo assieteInfo) throws URISyntaxException {
        log.debug("REST request to save AssieteInfo : {}", assieteInfo);
        if (assieteInfo.getId() != null) {
            throw new BadRequestAlertException("A new assieteInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssieteInfo result = assieteInfoService.save(assieteInfo);
        return ResponseEntity
            .created(new URI("/api/assiete-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assiete-infos/:id} : Updates an existing assieteInfo.
     *
     * @param id the id of the assieteInfo to save.
     * @param assieteInfo the assieteInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assieteInfo,
     * or with status {@code 400 (Bad Request)} if the assieteInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assieteInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assiete-infos/{id}")
    public ResponseEntity<AssieteInfo> updateAssieteInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssieteInfo assieteInfo
    ) throws URISyntaxException {
        log.debug("REST request to update AssieteInfo : {}, {}", id, assieteInfo);
        if (assieteInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assieteInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assieteInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssieteInfo result = assieteInfoService.save(assieteInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assieteInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /assiete-infos/:id} : Partial updates given fields of an existing assieteInfo, field will ignore if it is null
     *
     * @param id the id of the assieteInfo to save.
     * @param assieteInfo the assieteInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assieteInfo,
     * or with status {@code 400 (Bad Request)} if the assieteInfo is not valid,
     * or with status {@code 404 (Not Found)} if the assieteInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the assieteInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assiete-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssieteInfo> partialUpdateAssieteInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssieteInfo assieteInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssieteInfo partially : {}, {}", id, assieteInfo);
        if (assieteInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assieteInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assieteInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssieteInfo> result = assieteInfoService.partialUpdate(assieteInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assieteInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /assiete-infos} : get all the assieteInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assieteInfos in body.
     */
    @GetMapping("/assiete-infos")
    public ResponseEntity<List<AssieteInfo>> getAllAssieteInfos(Pageable pageable) {
        log.debug("REST request to get a page of AssieteInfos");
        Page<AssieteInfo> page = assieteInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assiete-infos/:id} : get the "id" assieteInfo.
     *
     * @param id the id of the assieteInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assieteInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assiete-infos/{id}")
    public ResponseEntity<AssieteInfo> getAssieteInfo(@PathVariable Long id) {
        log.debug("REST request to get AssieteInfo : {}", id);
        Optional<AssieteInfo> assieteInfo = assieteInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assieteInfo);
    }

    /**
     * {@code DELETE  /assiete-infos/:id} : delete the "id" assieteInfo.
     *
     * @param id the id of the assieteInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assiete-infos/{id}")
    public ResponseEntity<Void> deleteAssieteInfo(@PathVariable Long id) {
        log.debug("REST request to delete AssieteInfo : {}", id);
        assieteInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
