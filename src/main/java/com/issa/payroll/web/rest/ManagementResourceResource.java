package com.issa.payroll.web.rest;

import com.issa.payroll.domain.ManagementResource;
import com.issa.payroll.repository.ManagementResourceRepository;
import com.issa.payroll.service.ManagementResourceService;
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
 * REST controller for managing {@link com.issa.payroll.domain.ManagementResource}.
 */
@RestController
@RequestMapping("/api")
public class ManagementResourceResource {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceResource.class);

    private static final String ENTITY_NAME = "managementResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagementResourceService managementResourceService;

    private final ManagementResourceRepository managementResourceRepository;

    public ManagementResourceResource(
        ManagementResourceService managementResourceService,
        ManagementResourceRepository managementResourceRepository
    ) {
        this.managementResourceService = managementResourceService;
        this.managementResourceRepository = managementResourceRepository;
    }

    /**
     * {@code POST  /management-resources} : Create a new managementResource.
     *
     * @param managementResource the managementResource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managementResource, or with status {@code 400 (Bad Request)} if the managementResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/management-resources")
    public ResponseEntity<ManagementResource> createManagementResource(@RequestBody ManagementResource managementResource)
        throws URISyntaxException {
        log.debug("REST request to save ManagementResource : {}", managementResource);
        if (managementResource.getId() != null) {
            throw new BadRequestAlertException("A new managementResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManagementResource result = managementResourceService.save(managementResource);
        return ResponseEntity
            .created(new URI("/api/management-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /management-resources/:id} : Updates an existing managementResource.
     *
     * @param id the id of the managementResource to save.
     * @param managementResource the managementResource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResource,
     * or with status {@code 400 (Bad Request)} if the managementResource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managementResource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/management-resources/{id}")
    public ResponseEntity<ManagementResource> updateManagementResource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResource managementResource
    ) throws URISyntaxException {
        log.debug("REST request to update ManagementResource : {}, {}", id, managementResource);
        if (managementResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResource.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManagementResource result = managementResourceService.save(managementResource);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managementResource.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /management-resources/:id} : Partial updates given fields of an existing managementResource, field will ignore if it is null
     *
     * @param id the id of the managementResource to save.
     * @param managementResource the managementResource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResource,
     * or with status {@code 400 (Bad Request)} if the managementResource is not valid,
     * or with status {@code 404 (Not Found)} if the managementResource is not found,
     * or with status {@code 500 (Internal Server Error)} if the managementResource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/management-resources/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManagementResource> partialUpdateManagementResource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResource managementResource
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManagementResource partially : {}, {}", id, managementResource);
        if (managementResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResource.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManagementResource> result = managementResourceService.partialUpdate(managementResource);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, managementResource.getId().toString())
        );
    }

    /**
     * {@code GET  /management-resources} : get all the managementResources.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managementResources in body.
     */
    @GetMapping("/management-resources")
    public ResponseEntity<List<ManagementResource>> getAllManagementResources(Pageable pageable) {
        log.debug("REST request to get a page of ManagementResources");
        Page<ManagementResource> page = managementResourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /management-resources/:id} : get the "id" managementResource.
     *
     * @param id the id of the managementResource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managementResource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/management-resources/{id}")
    public ResponseEntity<ManagementResource> getManagementResource(@PathVariable Long id) {
        log.debug("REST request to get ManagementResource : {}", id);
        Optional<ManagementResource> managementResource = managementResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managementResource);
    }

    /**
     * {@code DELETE  /management-resources/:id} : delete the "id" managementResource.
     *
     * @param id the id of the managementResource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/management-resources/{id}")
    public ResponseEntity<Void> deleteManagementResource(@PathVariable Long id) {
        log.debug("REST request to delete ManagementResource : {}", id);
        managementResourceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
