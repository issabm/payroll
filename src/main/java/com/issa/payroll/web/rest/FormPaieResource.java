package com.issa.payroll.web.rest;

import com.issa.payroll.domain.FormPaie;
import com.issa.payroll.repository.FormPaieRepository;
import com.issa.payroll.service.FormPaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.FormPaie}.
 */
@RestController
@RequestMapping("/api")
public class FormPaieResource {

    private final Logger log = LoggerFactory.getLogger(FormPaieResource.class);

    private static final String ENTITY_NAME = "formPaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormPaieService formPaieService;

    private final FormPaieRepository formPaieRepository;

    public FormPaieResource(FormPaieService formPaieService, FormPaieRepository formPaieRepository) {
        this.formPaieService = formPaieService;
        this.formPaieRepository = formPaieRepository;
    }

    /**
     * {@code POST  /form-paies} : Create a new formPaie.
     *
     * @param formPaie the formPaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formPaie, or with status {@code 400 (Bad Request)} if the formPaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-paies")
    public ResponseEntity<FormPaie> createFormPaie(@RequestBody FormPaie formPaie) throws URISyntaxException {
        log.debug("REST request to save FormPaie : {}", formPaie);
        if (formPaie.getId() != null) {
            throw new BadRequestAlertException("A new formPaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormPaie result = formPaieService.save(formPaie);
        return ResponseEntity
            .created(new URI("/api/form-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-paies/:id} : Updates an existing formPaie.
     *
     * @param id the id of the formPaie to save.
     * @param formPaie the formPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaie,
     * or with status {@code 400 (Bad Request)} if the formPaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-paies/{id}")
    public ResponseEntity<FormPaie> updateFormPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaie formPaie
    ) throws URISyntaxException {
        log.debug("REST request to update FormPaie : {}, {}", id, formPaie);
        if (formPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormPaie result = formPaieService.save(formPaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /form-paies/:id} : Partial updates given fields of an existing formPaie, field will ignore if it is null
     *
     * @param id the id of the formPaie to save.
     * @param formPaie the formPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaie,
     * or with status {@code 400 (Bad Request)} if the formPaie is not valid,
     * or with status {@code 404 (Not Found)} if the formPaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the formPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormPaie> partialUpdateFormPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaie formPaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormPaie partially : {}, {}", id, formPaie);
        if (formPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormPaie> result = formPaieService.partialUpdate(formPaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaie.getId().toString())
        );
    }

    /**
     * {@code GET  /form-paies} : get all the formPaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formPaies in body.
     */
    @GetMapping("/form-paies")
    public ResponseEntity<List<FormPaie>> getAllFormPaies(Pageable pageable) {
        log.debug("REST request to get a page of FormPaies");
        Page<FormPaie> page = formPaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /form-paies/:id} : get the "id" formPaie.
     *
     * @param id the id of the formPaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formPaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-paies/{id}")
    public ResponseEntity<FormPaie> getFormPaie(@PathVariable Long id) {
        log.debug("REST request to get FormPaie : {}", id);
        Optional<FormPaie> formPaie = formPaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formPaie);
    }

    /**
     * {@code DELETE  /form-paies/:id} : delete the "id" formPaie.
     *
     * @param id the id of the formPaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-paies/{id}")
    public ResponseEntity<Void> deleteFormPaie(@PathVariable Long id) {
        log.debug("REST request to delete FormPaie : {}", id);
        formPaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
