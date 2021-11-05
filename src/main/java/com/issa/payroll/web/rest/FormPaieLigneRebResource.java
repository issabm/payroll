package com.issa.payroll.web.rest;

import com.issa.payroll.domain.FormPaieLigneReb;
import com.issa.payroll.repository.FormPaieLigneRebRepository;
import com.issa.payroll.service.FormPaieLigneRebService;
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
 * REST controller for managing {@link com.issa.payroll.domain.FormPaieLigneReb}.
 */
@RestController
@RequestMapping("/api")
public class FormPaieLigneRebResource {

    private final Logger log = LoggerFactory.getLogger(FormPaieLigneRebResource.class);

    private static final String ENTITY_NAME = "formPaieLigneReb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormPaieLigneRebService formPaieLigneRebService;

    private final FormPaieLigneRebRepository formPaieLigneRebRepository;

    public FormPaieLigneRebResource(
        FormPaieLigneRebService formPaieLigneRebService,
        FormPaieLigneRebRepository formPaieLigneRebRepository
    ) {
        this.formPaieLigneRebService = formPaieLigneRebService;
        this.formPaieLigneRebRepository = formPaieLigneRebRepository;
    }

    /**
     * {@code POST  /form-paie-ligne-rebs} : Create a new formPaieLigneReb.
     *
     * @param formPaieLigneReb the formPaieLigneReb to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formPaieLigneReb, or with status {@code 400 (Bad Request)} if the formPaieLigneReb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-paie-ligne-rebs")
    public ResponseEntity<FormPaieLigneReb> createFormPaieLigneReb(@RequestBody FormPaieLigneReb formPaieLigneReb)
        throws URISyntaxException {
        log.debug("REST request to save FormPaieLigneReb : {}", formPaieLigneReb);
        if (formPaieLigneReb.getId() != null) {
            throw new BadRequestAlertException("A new formPaieLigneReb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormPaieLigneReb result = formPaieLigneRebService.save(formPaieLigneReb);
        return ResponseEntity
            .created(new URI("/api/form-paie-ligne-rebs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-paie-ligne-rebs/:id} : Updates an existing formPaieLigneReb.
     *
     * @param id the id of the formPaieLigneReb to save.
     * @param formPaieLigneReb the formPaieLigneReb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaieLigneReb,
     * or with status {@code 400 (Bad Request)} if the formPaieLigneReb is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formPaieLigneReb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-paie-ligne-rebs/{id}")
    public ResponseEntity<FormPaieLigneReb> updateFormPaieLigneReb(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaieLigneReb formPaieLigneReb
    ) throws URISyntaxException {
        log.debug("REST request to update FormPaieLigneReb : {}, {}", id, formPaieLigneReb);
        if (formPaieLigneReb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaieLigneReb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieLigneRebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormPaieLigneReb result = formPaieLigneRebService.save(formPaieLigneReb);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaieLigneReb.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /form-paie-ligne-rebs/:id} : Partial updates given fields of an existing formPaieLigneReb, field will ignore if it is null
     *
     * @param id the id of the formPaieLigneReb to save.
     * @param formPaieLigneReb the formPaieLigneReb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formPaieLigneReb,
     * or with status {@code 400 (Bad Request)} if the formPaieLigneReb is not valid,
     * or with status {@code 404 (Not Found)} if the formPaieLigneReb is not found,
     * or with status {@code 500 (Internal Server Error)} if the formPaieLigneReb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-paie-ligne-rebs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormPaieLigneReb> partialUpdateFormPaieLigneReb(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormPaieLigneReb formPaieLigneReb
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormPaieLigneReb partially : {}, {}", id, formPaieLigneReb);
        if (formPaieLigneReb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formPaieLigneReb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formPaieLigneRebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormPaieLigneReb> result = formPaieLigneRebService.partialUpdate(formPaieLigneReb);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formPaieLigneReb.getId().toString())
        );
    }

    /**
     * {@code GET  /form-paie-ligne-rebs} : get all the formPaieLigneRebs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formPaieLigneRebs in body.
     */
    @GetMapping("/form-paie-ligne-rebs")
    public ResponseEntity<List<FormPaieLigneReb>> getAllFormPaieLigneRebs(Pageable pageable) {
        log.debug("REST request to get a page of FormPaieLigneRebs");
        Page<FormPaieLigneReb> page = formPaieLigneRebService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /form-paie-ligne-rebs/:id} : get the "id" formPaieLigneReb.
     *
     * @param id the id of the formPaieLigneReb to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formPaieLigneReb, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-paie-ligne-rebs/{id}")
    public ResponseEntity<FormPaieLigneReb> getFormPaieLigneReb(@PathVariable Long id) {
        log.debug("REST request to get FormPaieLigneReb : {}", id);
        Optional<FormPaieLigneReb> formPaieLigneReb = formPaieLigneRebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formPaieLigneReb);
    }

    /**
     * {@code DELETE  /form-paie-ligne-rebs/:id} : delete the "id" formPaieLigneReb.
     *
     * @param id the id of the formPaieLigneReb to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-paie-ligne-rebs/{id}")
    public ResponseEntity<Void> deleteFormPaieLigneReb(@PathVariable Long id) {
        log.debug("REST request to delete FormPaieLigneReb : {}", id);
        formPaieLigneRebService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
