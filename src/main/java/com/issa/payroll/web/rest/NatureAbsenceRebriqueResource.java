package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureAbsenceRebrique;
import com.issa.payroll.repository.NatureAbsenceRebriqueRepository;
import com.issa.payroll.service.NatureAbsenceRebriqueService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureAbsenceRebrique}.
 */
@RestController
@RequestMapping("/api")
public class NatureAbsenceRebriqueResource {

    private final Logger log = LoggerFactory.getLogger(NatureAbsenceRebriqueResource.class);

    private static final String ENTITY_NAME = "natureAbsenceRebrique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureAbsenceRebriqueService natureAbsenceRebriqueService;

    private final NatureAbsenceRebriqueRepository natureAbsenceRebriqueRepository;

    public NatureAbsenceRebriqueResource(
        NatureAbsenceRebriqueService natureAbsenceRebriqueService,
        NatureAbsenceRebriqueRepository natureAbsenceRebriqueRepository
    ) {
        this.natureAbsenceRebriqueService = natureAbsenceRebriqueService;
        this.natureAbsenceRebriqueRepository = natureAbsenceRebriqueRepository;
    }

    /**
     * {@code POST  /nature-absence-rebriques} : Create a new natureAbsenceRebrique.
     *
     * @param natureAbsenceRebrique the natureAbsenceRebrique to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureAbsenceRebrique, or with status {@code 400 (Bad Request)} if the natureAbsenceRebrique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-absence-rebriques")
    public ResponseEntity<NatureAbsenceRebrique> createNatureAbsenceRebrique(@RequestBody NatureAbsenceRebrique natureAbsenceRebrique)
        throws URISyntaxException {
        log.debug("REST request to save NatureAbsenceRebrique : {}", natureAbsenceRebrique);
        if (natureAbsenceRebrique.getId() != null) {
            throw new BadRequestAlertException("A new natureAbsenceRebrique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureAbsenceRebrique result = natureAbsenceRebriqueService.save(natureAbsenceRebrique);
        return ResponseEntity
            .created(new URI("/api/nature-absence-rebriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-absence-rebriques/:id} : Updates an existing natureAbsenceRebrique.
     *
     * @param id the id of the natureAbsenceRebrique to save.
     * @param natureAbsenceRebrique the natureAbsenceRebrique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAbsenceRebrique,
     * or with status {@code 400 (Bad Request)} if the natureAbsenceRebrique is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureAbsenceRebrique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-absence-rebriques/{id}")
    public ResponseEntity<NatureAbsenceRebrique> updateNatureAbsenceRebrique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAbsenceRebrique natureAbsenceRebrique
    ) throws URISyntaxException {
        log.debug("REST request to update NatureAbsenceRebrique : {}, {}", id, natureAbsenceRebrique);
        if (natureAbsenceRebrique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAbsenceRebrique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAbsenceRebriqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureAbsenceRebrique result = natureAbsenceRebriqueService.save(natureAbsenceRebrique);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natureAbsenceRebrique.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-absence-rebriques/:id} : Partial updates given fields of an existing natureAbsenceRebrique, field will ignore if it is null
     *
     * @param id the id of the natureAbsenceRebrique to save.
     * @param natureAbsenceRebrique the natureAbsenceRebrique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAbsenceRebrique,
     * or with status {@code 400 (Bad Request)} if the natureAbsenceRebrique is not valid,
     * or with status {@code 404 (Not Found)} if the natureAbsenceRebrique is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureAbsenceRebrique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-absence-rebriques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureAbsenceRebrique> partialUpdateNatureAbsenceRebrique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAbsenceRebrique natureAbsenceRebrique
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureAbsenceRebrique partially : {}, {}", id, natureAbsenceRebrique);
        if (natureAbsenceRebrique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAbsenceRebrique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAbsenceRebriqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureAbsenceRebrique> result = natureAbsenceRebriqueService.partialUpdate(natureAbsenceRebrique);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natureAbsenceRebrique.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-absence-rebriques} : get all the natureAbsenceRebriques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureAbsenceRebriques in body.
     */
    @GetMapping("/nature-absence-rebriques")
    public ResponseEntity<List<NatureAbsenceRebrique>> getAllNatureAbsenceRebriques(Pageable pageable) {
        log.debug("REST request to get a page of NatureAbsenceRebriques");
        Page<NatureAbsenceRebrique> page = natureAbsenceRebriqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-absence-rebriques/:id} : get the "id" natureAbsenceRebrique.
     *
     * @param id the id of the natureAbsenceRebrique to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureAbsenceRebrique, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-absence-rebriques/{id}")
    public ResponseEntity<NatureAbsenceRebrique> getNatureAbsenceRebrique(@PathVariable Long id) {
        log.debug("REST request to get NatureAbsenceRebrique : {}", id);
        Optional<NatureAbsenceRebrique> natureAbsenceRebrique = natureAbsenceRebriqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureAbsenceRebrique);
    }

    /**
     * {@code DELETE  /nature-absence-rebriques/:id} : delete the "id" natureAbsenceRebrique.
     *
     * @param id the id of the natureAbsenceRebrique to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-absence-rebriques/{id}")
    public ResponseEntity<Void> deleteNatureAbsenceRebrique(@PathVariable Long id) {
        log.debug("REST request to delete NatureAbsenceRebrique : {}", id);
        natureAbsenceRebriqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
