package com.issa.payroll.web.rest;

import com.issa.payroll.domain.SoldeAbsenceContrat;
import com.issa.payroll.repository.SoldeAbsenceContratRepository;
import com.issa.payroll.service.SoldeAbsenceContratService;
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
 * REST controller for managing {@link com.issa.payroll.domain.SoldeAbsenceContrat}.
 */
@RestController
@RequestMapping("/api")
public class SoldeAbsenceContratResource {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsenceContratResource.class);

    private static final String ENTITY_NAME = "soldeAbsenceContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoldeAbsenceContratService soldeAbsenceContratService;

    private final SoldeAbsenceContratRepository soldeAbsenceContratRepository;

    public SoldeAbsenceContratResource(
        SoldeAbsenceContratService soldeAbsenceContratService,
        SoldeAbsenceContratRepository soldeAbsenceContratRepository
    ) {
        this.soldeAbsenceContratService = soldeAbsenceContratService;
        this.soldeAbsenceContratRepository = soldeAbsenceContratRepository;
    }

    /**
     * {@code POST  /solde-absence-contrats} : Create a new soldeAbsenceContrat.
     *
     * @param soldeAbsenceContrat the soldeAbsenceContrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soldeAbsenceContrat, or with status {@code 400 (Bad Request)} if the soldeAbsenceContrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solde-absence-contrats")
    public ResponseEntity<SoldeAbsenceContrat> createSoldeAbsenceContrat(@RequestBody SoldeAbsenceContrat soldeAbsenceContrat)
        throws URISyntaxException {
        log.debug("REST request to save SoldeAbsenceContrat : {}", soldeAbsenceContrat);
        if (soldeAbsenceContrat.getId() != null) {
            throw new BadRequestAlertException("A new soldeAbsenceContrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoldeAbsenceContrat result = soldeAbsenceContratService.save(soldeAbsenceContrat);
        return ResponseEntity
            .created(new URI("/api/solde-absence-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solde-absence-contrats/:id} : Updates an existing soldeAbsenceContrat.
     *
     * @param id the id of the soldeAbsenceContrat to save.
     * @param soldeAbsenceContrat the soldeAbsenceContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsenceContrat,
     * or with status {@code 400 (Bad Request)} if the soldeAbsenceContrat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsenceContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solde-absence-contrats/{id}")
    public ResponseEntity<SoldeAbsenceContrat> updateSoldeAbsenceContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsenceContrat soldeAbsenceContrat
    ) throws URISyntaxException {
        log.debug("REST request to update SoldeAbsenceContrat : {}, {}", id, soldeAbsenceContrat);
        if (soldeAbsenceContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsenceContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsenceContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SoldeAbsenceContrat result = soldeAbsenceContratService.save(soldeAbsenceContrat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soldeAbsenceContrat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solde-absence-contrats/:id} : Partial updates given fields of an existing soldeAbsenceContrat, field will ignore if it is null
     *
     * @param id the id of the soldeAbsenceContrat to save.
     * @param soldeAbsenceContrat the soldeAbsenceContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsenceContrat,
     * or with status {@code 400 (Bad Request)} if the soldeAbsenceContrat is not valid,
     * or with status {@code 404 (Not Found)} if the soldeAbsenceContrat is not found,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsenceContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solde-absence-contrats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SoldeAbsenceContrat> partialUpdateSoldeAbsenceContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsenceContrat soldeAbsenceContrat
    ) throws URISyntaxException {
        log.debug("REST request to partial update SoldeAbsenceContrat partially : {}, {}", id, soldeAbsenceContrat);
        if (soldeAbsenceContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsenceContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsenceContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SoldeAbsenceContrat> result = soldeAbsenceContratService.partialUpdate(soldeAbsenceContrat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soldeAbsenceContrat.getId().toString())
        );
    }

    /**
     * {@code GET  /solde-absence-contrats} : get all the soldeAbsenceContrats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soldeAbsenceContrats in body.
     */
    @GetMapping("/solde-absence-contrats")
    public ResponseEntity<List<SoldeAbsenceContrat>> getAllSoldeAbsenceContrats(Pageable pageable) {
        log.debug("REST request to get a page of SoldeAbsenceContrats");
        Page<SoldeAbsenceContrat> page = soldeAbsenceContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solde-absence-contrats/:id} : get the "id" soldeAbsenceContrat.
     *
     * @param id the id of the soldeAbsenceContrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soldeAbsenceContrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solde-absence-contrats/{id}")
    public ResponseEntity<SoldeAbsenceContrat> getSoldeAbsenceContrat(@PathVariable Long id) {
        log.debug("REST request to get SoldeAbsenceContrat : {}", id);
        Optional<SoldeAbsenceContrat> soldeAbsenceContrat = soldeAbsenceContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(soldeAbsenceContrat);
    }

    /**
     * {@code DELETE  /solde-absence-contrats/:id} : delete the "id" soldeAbsenceContrat.
     *
     * @param id the id of the soldeAbsenceContrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solde-absence-contrats/{id}")
    public ResponseEntity<Void> deleteSoldeAbsenceContrat(@PathVariable Long id) {
        log.debug("REST request to delete SoldeAbsenceContrat : {}", id);
        soldeAbsenceContratService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
