package com.issa.payroll.web.rest;

import com.issa.payroll.domain.FormPaieLigne;
import com.issa.payroll.repository.FormPaieLigneRepository;
import com.issa.payroll.service.FormPaieLigneService;
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
 * REST controller for managing {@link com.issa.payroll.domain.FormPaieLigne}.
 */
@RestController
@RequestMapping("/api")
public class FormPaieLigneResource {

    private final Logger log = LoggerFactory.getLogger(FormPaieLigneResource.class);

    private static final String ENTITY_NAME = "formPaieLigne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormPaieLigneService formPaieLigneService;

    private final FormPaieLigneRepository formPaieLigneRepository;

    public FormPaieLigneResource(FormPaieLigneService formPaieLigneService, FormPaieLigneRepository formPaieLigneRepository) {
        this.formPaieLigneService = formPaieLigneService;
        this.formPaieLigneRepository = formPaieLigneRepository;
    }

    /**
     * {@code POST  /form-paie-lignes} : Create a new formPaieLigne.
     *
     * @param formPaieLigne the formPaieLigne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formPaieLigne, or with status {@code 400 (Bad Request)} if the formPaieLigne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-paie-lignes")
    public ResponseEntity<FormPaieLigne> createFormPaieLigne(@RequestBody FormPaieLigne formPaieLigne) throws URISyntaxException {
        log.debug("REST request to save FormPaieLigne : {}", formPaieLigne);
        if (formPaieLigne.getId() != null) {
            throw new BadRequestAlertException("A new formPaieLigne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormPaieLigne result = formPaieLigneService.save(formPaieLigne);
        return ResponseEntity
            .created(new URI("/api/form-paie-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-paie-lignes/:id} : Updates an existing formPaieLigne.
     *
     * @param id the id of the formPaieLigne to save.
     * @param formPaieLigne the formPaieLigne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaieLigne,
     * or with status {@code 400 (Bad Request)} if the formPaieLigne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formPaieLigne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-paie-lignes/{id}")
    public ResponseEntity<FormPaieLigne> updateFormPaieLigne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaieLigne formPaieLigne
    ) throws URISyntaxException {
        log.debug("REST request to update FormPaieLigne : {}, {}", id, formPaieLigne);
        if (formPaieLigne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaieLigne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieLigneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormPaieLigne result = formPaieLigneService.save(formPaieLigne);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaieLigne.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /form-paie-lignes/:id} : Partial updates given fields of an existing formPaieLigne, field will ignore if it is null
     *
     * @param id the id of the formPaieLigne to save.
     * @param formPaieLigne the formPaieLigne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaieLigne,
     * or with status {@code 400 (Bad Request)} if the formPaieLigne is not valid,
     * or with status {@code 404 (Not Found)} if the formPaieLigne is not found,
     * or with status {@code 500 (Internal Server Error)} if the formPaieLigne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-paie-lignes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormPaieLigne> partialUpdateFormPaieLigne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaieLigne formPaieLigne
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormPaieLigne partially : {}, {}", id, formPaieLigne);
        if (formPaieLigne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaieLigne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieLigneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormPaieLigne> result = formPaieLigneService.partialUpdate(formPaieLigne);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaieLigne.getId().toString())
        );
    }

    /**
     * {@code GET  /form-paie-lignes} : get all the formPaieLignes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formPaieLignes in body.
     */
    @GetMapping("/form-paie-lignes")
    public ResponseEntity<List<FormPaieLigne>> getAllFormPaieLignes(Pageable pageable) {
        log.debug("REST request to get a page of FormPaieLignes");
        Page<FormPaieLigne> page = formPaieLigneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /form-paie-lignes/:id} : get the "id" formPaieLigne.
     *
     * @param id the id of the formPaieLigne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formPaieLigne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-paie-lignes/{id}")
    public ResponseEntity<FormPaieLigne> getFormPaieLigne(@PathVariable Long id) {
        log.debug("REST request to get FormPaieLigne : {}", id);
        Optional<FormPaieLigne> formPaieLigne = formPaieLigneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formPaieLigne);
    }

    /**
     * {@code DELETE  /form-paie-lignes/:id} : delete the "id" formPaieLigne.
     *
     * @param id the id of the formPaieLigne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-paie-lignes/{id}")
    public ResponseEntity<Void> deleteFormPaieLigne(@PathVariable Long id) {
        log.debug("REST request to delete FormPaieLigne : {}", id);
        formPaieLigneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
