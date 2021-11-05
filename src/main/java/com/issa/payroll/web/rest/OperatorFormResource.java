package com.issa.payroll.web.rest;

import com.issa.payroll.domain.OperatorForm;
import com.issa.payroll.repository.OperatorFormRepository;
import com.issa.payroll.service.OperatorFormService;
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
 * REST controller for managing {@link com.issa.payroll.domain.OperatorForm}.
 */
@RestController
@RequestMapping("/api")
public class OperatorFormResource {

    private final Logger log = LoggerFactory.getLogger(OperatorFormResource.class);

    private static final String ENTITY_NAME = "operatorForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperatorFormService operatorFormService;

    private final OperatorFormRepository operatorFormRepository;

    public OperatorFormResource(OperatorFormService operatorFormService, OperatorFormRepository operatorFormRepository) {
        this.operatorFormService = operatorFormService;
        this.operatorFormRepository = operatorFormRepository;
    }

    /**
     * {@code POST  /operator-forms} : Create a new operatorForm.
     *
     * @param operatorForm the operatorForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operatorForm, or with status {@code 400 (Bad Request)} if the operatorForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operator-forms")
    public ResponseEntity<OperatorForm> createOperatorForm(@RequestBody OperatorForm operatorForm) throws URISyntaxException {
        log.debug("REST request to save OperatorForm : {}", operatorForm);
        if (operatorForm.getId() != null) {
            throw new BadRequestAlertException("A new operatorForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperatorForm result = operatorFormService.save(operatorForm);
        return ResponseEntity
            .created(new URI("/api/operator-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operator-forms/:id} : Updates an existing operatorForm.
     *
     * @param id the id of the operatorForm to save.
     * @param operatorForm the operatorForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorForm,
     * or with status {@code 400 (Bad Request)} if the operatorForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operatorForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operator-forms/{id}")
    public ResponseEntity<OperatorForm> updateOperatorForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperatorForm operatorForm
    ) throws URISyntaxException {
        log.debug("REST request to update OperatorForm : {}, {}", id, operatorForm);
        if (operatorForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperatorForm result = operatorFormService.save(operatorForm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operatorForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operator-forms/:id} : Partial updates given fields of an existing operatorForm, field will ignore if it is null
     *
     * @param id the id of the operatorForm to save.
     * @param operatorForm the operatorForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operatorForm,
     * or with status {@code 400 (Bad Request)} if the operatorForm is not valid,
     * or with status {@code 404 (Not Found)} if the operatorForm is not found,
     * or with status {@code 500 (Internal Server Error)} if the operatorForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operator-forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperatorForm> partialUpdateOperatorForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperatorForm operatorForm
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperatorForm partially : {}, {}", id, operatorForm);
        if (operatorForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operatorForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operatorFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperatorForm> result = operatorFormService.partialUpdate(operatorForm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operatorForm.getId().toString())
        );
    }

    /**
     * {@code GET  /operator-forms} : get all the operatorForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operatorForms in body.
     */
    @GetMapping("/operator-forms")
    public ResponseEntity<List<OperatorForm>> getAllOperatorForms(Pageable pageable) {
        log.debug("REST request to get a page of OperatorForms");
        Page<OperatorForm> page = operatorFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operator-forms/:id} : get the "id" operatorForm.
     *
     * @param id the id of the operatorForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operatorForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operator-forms/{id}")
    public ResponseEntity<OperatorForm> getOperatorForm(@PathVariable Long id) {
        log.debug("REST request to get OperatorForm : {}", id);
        Optional<OperatorForm> operatorForm = operatorFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operatorForm);
    }

    /**
     * {@code DELETE  /operator-forms/:id} : delete the "id" operatorForm.
     *
     * @param id the id of the operatorForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operator-forms/{id}")
    public ResponseEntity<Void> deleteOperatorForm(@PathVariable Long id) {
        log.debug("REST request to delete OperatorForm : {}", id);
        operatorFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
