package com.issa.payroll.web.rest;

import com.issa.payroll.domain.ManagementResourceProfile;
import com.issa.payroll.repository.ManagementResourceProfileRepository;
import com.issa.payroll.service.ManagementResourceProfileService;
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
 * REST controller for managing {@link com.issa.payroll.domain.ManagementResourceProfile}.
 */
@RestController
@RequestMapping("/api")
public class ManagementResourceProfileResource {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceProfileResource.class);

    private static final String ENTITY_NAME = "managementResourceProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagementResourceProfileService managementResourceProfileService;

    private final ManagementResourceProfileRepository managementResourceProfileRepository;

    public ManagementResourceProfileResource(
        ManagementResourceProfileService managementResourceProfileService,
        ManagementResourceProfileRepository managementResourceProfileRepository
    ) {
        this.managementResourceProfileService = managementResourceProfileService;
        this.managementResourceProfileRepository = managementResourceProfileRepository;
    }

    /**
     * {@code POST  /management-resource-profiles} : Create a new managementResourceProfile.
     *
     * @param managementResourceProfile the managementResourceProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managementResourceProfile, or with status {@code 400 (Bad Request)} if the managementResourceProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/management-resource-profiles")
    public ResponseEntity<ManagementResourceProfile> createManagementResourceProfile(
        @RequestBody ManagementResourceProfile managementResourceProfile
    ) throws URISyntaxException {
        log.debug("REST request to save ManagementResourceProfile : {}", managementResourceProfile);
        if (managementResourceProfile.getId() != null) {
            throw new BadRequestAlertException("A new managementResourceProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManagementResourceProfile result = managementResourceProfileService.save(managementResourceProfile);
        return ResponseEntity
            .created(new URI("/api/management-resource-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /management-resource-profiles/:id} : Updates an existing managementResourceProfile.
     *
     * @param id the id of the managementResourceProfile to save.
     * @param managementResourceProfile the managementResourceProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResourceProfile,
     * or with status {@code 400 (Bad Request)} if the managementResourceProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managementResourceProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/management-resource-profiles/{id}")
    public ResponseEntity<ManagementResourceProfile> updateManagementResourceProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResourceProfile managementResourceProfile
    ) throws URISyntaxException {
        log.debug("REST request to update ManagementResourceProfile : {}, {}", id, managementResourceProfile);
        if (managementResourceProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResourceProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManagementResourceProfile result = managementResourceProfileService.save(managementResourceProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managementResourceProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /management-resource-profiles/:id} : Partial updates given fields of an existing managementResourceProfile, field will ignore if it is null
     *
     * @param id the id of the managementResourceProfile to save.
     * @param managementResourceProfile the managementResourceProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResourceProfile,
     * or with status {@code 400 (Bad Request)} if the managementResourceProfile is not valid,
     * or with status {@code 404 (Not Found)} if the managementResourceProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the managementResourceProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/management-resource-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManagementResourceProfile> partialUpdateManagementResourceProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResourceProfile managementResourceProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManagementResourceProfile partially : {}, {}", id, managementResourceProfile);
        if (managementResourceProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResourceProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManagementResourceProfile> result = managementResourceProfileService.partialUpdate(managementResourceProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managementResourceProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /management-resource-profiles} : get all the managementResourceProfiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managementResourceProfiles in body.
     */
    @GetMapping("/management-resource-profiles")
    public ResponseEntity<List<ManagementResourceProfile>> getAllManagementResourceProfiles(Pageable pageable) {
        log.debug("REST request to get a page of ManagementResourceProfiles");
        Page<ManagementResourceProfile> page = managementResourceProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /management-resource-profiles/:id} : get the "id" managementResourceProfile.
     *
     * @param id the id of the managementResourceProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managementResourceProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/management-resource-profiles/{id}")
    public ResponseEntity<ManagementResourceProfile> getManagementResourceProfile(@PathVariable Long id) {
        log.debug("REST request to get ManagementResourceProfile : {}", id);
        Optional<ManagementResourceProfile> managementResourceProfile = managementResourceProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managementResourceProfile);
    }

    /**
     * {@code DELETE  /management-resource-profiles/:id} : delete the "id" managementResourceProfile.
     *
     * @param id the id of the managementResourceProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/management-resource-profiles/{id}")
    public ResponseEntity<Void> deleteManagementResourceProfile(@PathVariable Long id) {
        log.debug("REST request to delete ManagementResourceProfile : {}", id);
        managementResourceProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
