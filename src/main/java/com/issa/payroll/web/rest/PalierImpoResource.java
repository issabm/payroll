package com.issa.payroll.web.rest;

import com.issa.payroll.domain.PalierImpo;
import com.issa.payroll.repository.PalierImpoRepository;
import com.issa.payroll.service.PalierImpoService;
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
 * REST controller for managing {@link com.issa.payroll.domain.PalierImpo}.
 */
@RestController
@RequestMapping("/api")
public class PalierImpoResource {

    private final Logger log = LoggerFactory.getLogger(PalierImpoResource.class);

    private static final String ENTITY_NAME = "palierImpo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PalierImpoService palierImpoService;

    private final PalierImpoRepository palierImpoRepository;

    public PalierImpoResource(PalierImpoService palierImpoService, PalierImpoRepository palierImpoRepository) {
        this.palierImpoService = palierImpoService;
        this.palierImpoRepository = palierImpoRepository;
    }

    /**
     * {@code POST  /palier-impos} : Create a new palierImpo.
     *
     * @param palierImpo the palierImpo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new palierImpo, or with status {@code 400 (Bad Request)} if the palierImpo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/palier-impos")
    public ResponseEntity<PalierImpo> createPalierImpo(@RequestBody PalierImpo palierImpo) throws URISyntaxException {
        log.debug("REST request to save PalierImpo : {}", palierImpo);
        if (palierImpo.getId() != null) {
            throw new BadRequestAlertException("A new palierImpo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PalierImpo result = palierImpoService.save(palierImpo);
        return ResponseEntity
            .created(new URI("/api/palier-impos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /palier-impos/:id} : Updates an existing palierImpo.
     *
     * @param id the id of the palierImpo to save.
     * @param palierImpo the palierImpo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierImpo,
     * or with status {@code 400 (Bad Request)} if the palierImpo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the palierImpo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/palier-impos/{id}")
    public ResponseEntity<PalierImpo> updatePalierImpo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierImpo palierImpo
    ) throws URISyntaxException {
        log.debug("REST request to update PalierImpo : {}, {}", id, palierImpo);
        if (palierImpo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierImpo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierImpoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PalierImpo result = palierImpoService.save(palierImpo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, palierImpo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /palier-impos/:id} : Partial updates given fields of an existing palierImpo, field will ignore if it is null
     *
     * @param id the id of the palierImpo to save.
     * @param palierImpo the palierImpo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierImpo,
     * or with status {@code 400 (Bad Request)} if the palierImpo is not valid,
     * or with status {@code 404 (Not Found)} if the palierImpo is not found,
     * or with status {@code 500 (Internal Server Error)} if the palierImpo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/palier-impos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PalierImpo> partialUpdatePalierImpo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierImpo palierImpo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PalierImpo partially : {}, {}", id, palierImpo);
        if (palierImpo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierImpo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierImpoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PalierImpo> result = palierImpoService.partialUpdate(palierImpo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, palierImpo.getId().toString())
        );
    }

    /**
     * {@code GET  /palier-impos} : get all the palierImpos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of palierImpos in body.
     */
    @GetMapping("/palier-impos")
    public ResponseEntity<List<PalierImpo>> getAllPalierImpos(Pageable pageable) {
        log.debug("REST request to get a page of PalierImpos");
        Page<PalierImpo> page = palierImpoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /palier-impos/:id} : get the "id" palierImpo.
     *
     * @param id the id of the palierImpo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the palierImpo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/palier-impos/{id}")
    public ResponseEntity<PalierImpo> getPalierImpo(@PathVariable Long id) {
        log.debug("REST request to get PalierImpo : {}", id);
        Optional<PalierImpo> palierImpo = palierImpoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(palierImpo);
    }

    /**
     * {@code DELETE  /palier-impos/:id} : delete the "id" palierImpo.
     *
     * @param id the id of the palierImpo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/palier-impos/{id}")
    public ResponseEntity<Void> deletePalierImpo(@PathVariable Long id) {
        log.debug("REST request to delete PalierImpo : {}", id);
        palierImpoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
