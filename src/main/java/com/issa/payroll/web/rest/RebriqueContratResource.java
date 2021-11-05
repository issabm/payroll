package com.issa.payroll.web.rest;

import com.issa.payroll.domain.RebriqueContrat;
import com.issa.payroll.repository.RebriqueContratRepository;
import com.issa.payroll.service.RebriqueContratService;
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
 * REST controller for managing {@link com.issa.payroll.domain.RebriqueContrat}.
 */
@RestController
@RequestMapping("/api")
public class RebriqueContratResource {

    private final Logger log = LoggerFactory.getLogger(RebriqueContratResource.class);

    private static final String ENTITY_NAME = "rebriqueContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RebriqueContratService rebriqueContratService;

    private final RebriqueContratRepository rebriqueContratRepository;

    public RebriqueContratResource(RebriqueContratService rebriqueContratService, RebriqueContratRepository rebriqueContratRepository) {
        this.rebriqueContratService = rebriqueContratService;
        this.rebriqueContratRepository = rebriqueContratRepository;
    }

    /**
     * {@code POST  /rebrique-contrats} : Create a new rebriqueContrat.
     *
     * @param rebriqueContrat the rebriqueContrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rebriqueContrat, or with status {@code 400 (Bad Request)} if the rebriqueContrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rebrique-contrats")
    public ResponseEntity<RebriqueContrat> createRebriqueContrat(@RequestBody RebriqueContrat rebriqueContrat) throws URISyntaxException {
        log.debug("REST request to save RebriqueContrat : {}", rebriqueContrat);
        if (rebriqueContrat.getId() != null) {
            throw new BadRequestAlertException("A new rebriqueContrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RebriqueContrat result = rebriqueContratService.save(rebriqueContrat);
        return ResponseEntity
            .created(new URI("/api/rebrique-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rebrique-contrats/:id} : Updates an existing rebriqueContrat.
     *
     * @param id the id of the rebriqueContrat to save.
     * @param rebriqueContrat the rebriqueContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebriqueContrat,
     * or with status {@code 400 (Bad Request)} if the rebriqueContrat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rebriqueContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rebrique-contrats/{id}")
    public ResponseEntity<RebriqueContrat> updateRebriqueContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebriqueContrat rebriqueContrat
    ) throws URISyntaxException {
        log.debug("REST request to update RebriqueContrat : {}, {}", id, rebriqueContrat);
        if (rebriqueContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebriqueContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebriqueContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RebriqueContrat result = rebriqueContratService.save(rebriqueContrat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rebriqueContrat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rebrique-contrats/:id} : Partial updates given fields of an existing rebriqueContrat, field will ignore if it is null
     *
     * @param id the id of the rebriqueContrat to save.
     * @param rebriqueContrat the rebriqueContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebriqueContrat,
     * or with status {@code 400 (Bad Request)} if the rebriqueContrat is not valid,
     * or with status {@code 404 (Not Found)} if the rebriqueContrat is not found,
     * or with status {@code 500 (Internal Server Error)} if the rebriqueContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rebrique-contrats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RebriqueContrat> partialUpdateRebriqueContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RebriqueContrat rebriqueContrat
    ) throws URISyntaxException {
        log.debug("REST request to partial update RebriqueContrat partially : {}, {}", id, rebriqueContrat);
        if (rebriqueContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebriqueContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebriqueContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RebriqueContrat> result = rebriqueContratService.partialUpdate(rebriqueContrat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rebriqueContrat.getId().toString())
        );
    }

    /**
     * {@code GET  /rebrique-contrats} : get all the rebriqueContrats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rebriqueContrats in body.
     */
    @GetMapping("/rebrique-contrats")
    public ResponseEntity<List<RebriqueContrat>> getAllRebriqueContrats(Pageable pageable) {
        log.debug("REST request to get a page of RebriqueContrats");
        Page<RebriqueContrat> page = rebriqueContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rebrique-contrats/:id} : get the "id" rebriqueContrat.
     *
     * @param id the id of the rebriqueContrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rebriqueContrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rebrique-contrats/{id}")
    public ResponseEntity<RebriqueContrat> getRebriqueContrat(@PathVariable Long id) {
        log.debug("REST request to get RebriqueContrat : {}", id);
        Optional<RebriqueContrat> rebriqueContrat = rebriqueContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rebriqueContrat);
    }

    /**
     * {@code DELETE  /rebrique-contrats/:id} : delete the "id" rebriqueContrat.
     *
     * @param id the id of the rebriqueContrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rebrique-contrats/{id}")
    public ResponseEntity<Void> deleteRebriqueContrat(@PathVariable Long id) {
        log.debug("REST request to delete RebriqueContrat : {}", id);
        rebriqueContratService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
