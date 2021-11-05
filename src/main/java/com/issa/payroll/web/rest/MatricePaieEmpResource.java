package com.issa.payroll.web.rest;

import com.issa.payroll.domain.MatricePaieEmp;
import com.issa.payroll.repository.MatricePaieEmpRepository;
import com.issa.payroll.service.MatricePaieEmpService;
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
 * REST controller for managing {@link com.issa.payroll.domain.MatricePaieEmp}.
 */
@RestController
@RequestMapping("/api")
public class MatricePaieEmpResource {

    private final Logger log = LoggerFactory.getLogger(MatricePaieEmpResource.class);

    private static final String ENTITY_NAME = "matricePaieEmp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatricePaieEmpService matricePaieEmpService;

    private final MatricePaieEmpRepository matricePaieEmpRepository;

    public MatricePaieEmpResource(MatricePaieEmpService matricePaieEmpService, MatricePaieEmpRepository matricePaieEmpRepository) {
        this.matricePaieEmpService = matricePaieEmpService;
        this.matricePaieEmpRepository = matricePaieEmpRepository;
    }

    /**
     * {@code POST  /matrice-paie-emps} : Create a new matricePaieEmp.
     *
     * @param matricePaieEmp the matricePaieEmp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matricePaieEmp, or with status {@code 400 (Bad Request)} if the matricePaieEmp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matrice-paie-emps")
    public ResponseEntity<MatricePaieEmp> createMatricePaieEmp(@RequestBody MatricePaieEmp matricePaieEmp) throws URISyntaxException {
        log.debug("REST request to save MatricePaieEmp : {}", matricePaieEmp);
        if (matricePaieEmp.getId() != null) {
            throw new BadRequestAlertException("A new matricePaieEmp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatricePaieEmp result = matricePaieEmpService.save(matricePaieEmp);
        return ResponseEntity
            .created(new URI("/api/matrice-paie-emps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matrice-paie-emps/:id} : Updates an existing matricePaieEmp.
     *
     * @param id the id of the matricePaieEmp to save.
     * @param matricePaieEmp the matricePaieEmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matricePaieEmp,
     * or with status {@code 400 (Bad Request)} if the matricePaieEmp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matricePaieEmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matrice-paie-emps/{id}")
    public ResponseEntity<MatricePaieEmp> updateMatricePaieEmp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatricePaieEmp matricePaieEmp
    ) throws URISyntaxException {
        log.debug("REST request to update MatricePaieEmp : {}, {}", id, matricePaieEmp);
        if (matricePaieEmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matricePaieEmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matricePaieEmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatricePaieEmp result = matricePaieEmpService.save(matricePaieEmp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matricePaieEmp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /matrice-paie-emps/:id} : Partial updates given fields of an existing matricePaieEmp, field will ignore if it is null
     *
     * @param id the id of the matricePaieEmp to save.
     * @param matricePaieEmp the matricePaieEmp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matricePaieEmp,
     * or with status {@code 400 (Bad Request)} if the matricePaieEmp is not valid,
     * or with status {@code 404 (Not Found)} if the matricePaieEmp is not found,
     * or with status {@code 500 (Internal Server Error)} if the matricePaieEmp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/matrice-paie-emps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatricePaieEmp> partialUpdateMatricePaieEmp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatricePaieEmp matricePaieEmp
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatricePaieEmp partially : {}, {}", id, matricePaieEmp);
        if (matricePaieEmp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matricePaieEmp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matricePaieEmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatricePaieEmp> result = matricePaieEmpService.partialUpdate(matricePaieEmp);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matricePaieEmp.getId().toString())
        );
    }

    /**
     * {@code GET  /matrice-paie-emps} : get all the matricePaieEmps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matricePaieEmps in body.
     */
    @GetMapping("/matrice-paie-emps")
    public ResponseEntity<List<MatricePaieEmp>> getAllMatricePaieEmps(Pageable pageable) {
        log.debug("REST request to get a page of MatricePaieEmps");
        Page<MatricePaieEmp> page = matricePaieEmpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matrice-paie-emps/:id} : get the "id" matricePaieEmp.
     *
     * @param id the id of the matricePaieEmp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matricePaieEmp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matrice-paie-emps/{id}")
    public ResponseEntity<MatricePaieEmp> getMatricePaieEmp(@PathVariable Long id) {
        log.debug("REST request to get MatricePaieEmp : {}", id);
        Optional<MatricePaieEmp> matricePaieEmp = matricePaieEmpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matricePaieEmp);
    }

    /**
     * {@code DELETE  /matrice-paie-emps/:id} : delete the "id" matricePaieEmp.
     *
     * @param id the id of the matricePaieEmp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matrice-paie-emps/{id}")
    public ResponseEntity<Void> deleteMatricePaieEmp(@PathVariable Long id) {
        log.debug("REST request to delete MatricePaieEmp : {}", id);
        matricePaieEmpService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
