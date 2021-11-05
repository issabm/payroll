package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Assiete;
import com.issa.payroll.repository.AssieteRepository;
import com.issa.payroll.service.AssieteService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Assiete}.
 */
@RestController
@RequestMapping("/api")
public class AssieteResource {

    private final Logger log = LoggerFactory.getLogger(AssieteResource.class);

    private static final String ENTITY_NAME = "assiete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssieteService assieteService;

    private final AssieteRepository assieteRepository;

    public AssieteResource(AssieteService assieteService, AssieteRepository assieteRepository) {
        this.assieteService = assieteService;
        this.assieteRepository = assieteRepository;
    }

    /**
     * {@code POST  /assietes} : Create a new assiete.
     *
     * @param assiete the assiete to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assiete, or with status {@code 400 (Bad Request)} if the assiete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assietes")
    public ResponseEntity<Assiete> createAssiete(@RequestBody Assiete assiete) throws URISyntaxException {
        log.debug("REST request to save Assiete : {}", assiete);
        if (assiete.getId() != null) {
            throw new BadRequestAlertException("A new assiete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assiete result = assieteService.save(assiete);
        return ResponseEntity
            .created(new URI("/api/assietes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assietes/:id} : Updates an existing assiete.
     *
     * @param id the id of the assiete to save.
     * @param assiete the assiete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assiete,
     * or with status {@code 400 (Bad Request)} if the assiete is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assiete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assietes/{id}")
    public ResponseEntity<Assiete> updateAssiete(@PathVariable(value = "id", required = false) final Long id, @RequestBody Assiete assiete)
        throws URISyntaxException {
        log.debug("REST request to update Assiete : {}, {}", id, assiete);
        if (assiete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assiete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assieteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Assiete result = assieteService.save(assiete);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assiete.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /assietes/:id} : Partial updates given fields of an existing assiete, field will ignore if it is null
     *
     * @param id the id of the assiete to save.
     * @param assiete the assiete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assiete,
     * or with status {@code 400 (Bad Request)} if the assiete is not valid,
     * or with status {@code 404 (Not Found)} if the assiete is not found,
     * or with status {@code 500 (Internal Server Error)} if the assiete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assietes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Assiete> partialUpdateAssiete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Assiete assiete
    ) throws URISyntaxException {
        log.debug("REST request to partial update Assiete partially : {}, {}", id, assiete);
        if (assiete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assiete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assieteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Assiete> result = assieteService.partialUpdate(assiete);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assiete.getId().toString())
        );
    }

    /**
     * {@code GET  /assietes} : get all the assietes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assietes in body.
     */
    @GetMapping("/assietes")
    public ResponseEntity<List<Assiete>> getAllAssietes(Pageable pageable) {
        log.debug("REST request to get a page of Assietes");
        Page<Assiete> page = assieteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assietes/:id} : get the "id" assiete.
     *
     * @param id the id of the assiete to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assiete, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assietes/{id}")
    public ResponseEntity<Assiete> getAssiete(@PathVariable Long id) {
        log.debug("REST request to get Assiete : {}", id);
        Optional<Assiete> assiete = assieteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assiete);
    }

    /**
     * {@code DELETE  /assietes/:id} : delete the "id" assiete.
     *
     * @param id the id of the assiete to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assietes/{id}")
    public ResponseEntity<Void> deleteAssiete(@PathVariable Long id) {
        log.debug("REST request to delete Assiete : {}", id);
        assieteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
