package com.issa.payroll.web.rest;

import com.issa.payroll.domain.MatricePaie;
import com.issa.payroll.repository.MatricePaieRepository;
import com.issa.payroll.service.MatricePaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.MatricePaie}.
 */
@RestController
@RequestMapping("/api")
public class MatricePaieResource {

    private final Logger log = LoggerFactory.getLogger(MatricePaieResource.class);

    private static final String ENTITY_NAME = "matricePaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatricePaieService matricePaieService;

    private final MatricePaieRepository matricePaieRepository;

    public MatricePaieResource(MatricePaieService matricePaieService, MatricePaieRepository matricePaieRepository) {
        this.matricePaieService = matricePaieService;
        this.matricePaieRepository = matricePaieRepository;
    }

    /**
     * {@code POST  /matrice-paies} : Create a new matricePaie.
     *
     * @param matricePaie the matricePaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matricePaie, or with status {@code 400 (Bad Request)} if the matricePaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matrice-paies")
    public ResponseEntity<MatricePaie> createMatricePaie(@RequestBody MatricePaie matricePaie) throws URISyntaxException {
        log.debug("REST request to save MatricePaie : {}", matricePaie);
        if (matricePaie.getId() != null) {
            throw new BadRequestAlertException("A new matricePaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatricePaie result = matricePaieService.save(matricePaie);
        return ResponseEntity
            .created(new URI("/api/matrice-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matrice-paies/:id} : Updates an existing matricePaie.
     *
     * @param id the id of the matricePaie to save.
     * @param matricePaie the matricePaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matricePaie,
     * or with status {@code 400 (Bad Request)} if the matricePaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matricePaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matrice-paies/{id}")
    public ResponseEntity<MatricePaie> updateMatricePaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatricePaie matricePaie
    ) throws URISyntaxException {
        log.debug("REST request to update MatricePaie : {}, {}", id, matricePaie);
        if (matricePaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matricePaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matricePaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatricePaie result = matricePaieService.save(matricePaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matricePaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /matrice-paies/:id} : Partial updates given fields of an existing matricePaie, field will ignore if it is null
     *
     * @param id the id of the matricePaie to save.
     * @param matricePaie the matricePaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matricePaie,
     * or with status {@code 400 (Bad Request)} if the matricePaie is not valid,
     * or with status {@code 404 (Not Found)} if the matricePaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the matricePaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/matrice-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatricePaie> partialUpdateMatricePaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatricePaie matricePaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatricePaie partially : {}, {}", id, matricePaie);
        if (matricePaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matricePaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matricePaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatricePaie> result = matricePaieService.partialUpdate(matricePaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matricePaie.getId().toString())
        );
    }

    /**
     * {@code GET  /matrice-paies} : get all the matricePaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matricePaies in body.
     */
    @GetMapping("/matrice-paies")
    public ResponseEntity<List<MatricePaie>> getAllMatricePaies(Pageable pageable) {
        log.debug("REST request to get a page of MatricePaies");
        Page<MatricePaie> page = matricePaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matrice-paies/:id} : get the "id" matricePaie.
     *
     * @param id the id of the matricePaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matricePaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matrice-paies/{id}")
    public ResponseEntity<MatricePaie> getMatricePaie(@PathVariable Long id) {
        log.debug("REST request to get MatricePaie : {}", id);
        Optional<MatricePaie> matricePaie = matricePaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matricePaie);
    }

    /**
     * {@code DELETE  /matrice-paies/:id} : delete the "id" matricePaie.
     *
     * @param id the id of the matricePaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matrice-paies/{id}")
    public ResponseEntity<Void> deleteMatricePaie(@PathVariable Long id) {
        log.debug("REST request to delete MatricePaie : {}", id);
        matricePaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
